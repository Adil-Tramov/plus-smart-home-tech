package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {
    private final KafkaConsumer<String, SensorEventAvro> consumer;
    private final KafkaProducer<String, SensorsSnapshotAvro> producer;
    private final SnapshotStorage snapshotStorage;
    private final String inputTopic;
    private final String outputTopic;

    public void start() {
        try {
            consumer.subscribe(List.of(inputTopic));
            log.info("Подписка на топик {}", inputTopic);
            while (true) {
                processRecords(consumer.poll(Duration.ofMillis(1000)));
            }
        } catch (WakeupException ignored) {
            // игнорируем - закрываем консьюмер и продюсер в блоке finally
        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {
            closeResources();
        }
    }

    private void processRecords(ConsumerRecords<String, SensorEventAvro> records) {
        for (ConsumerRecord<String, SensorEventAvro> record : records) {
            try {
                logReceivedRecord(record);
                processRecord(record);
            } catch (Exception e) {
                log.error("Ошибка при обработке записи: ключ={}, значение={}", record.key(), record.value(), e);
            }
        }
        consumer.commitSync();
    }

    private void processRecord(ConsumerRecord<String, SensorEventAvro> record) {
        Optional<SensorsSnapshotAvro> snapshotOpt = snapshotStorage.update(record.value());
        snapshotOpt.ifPresent(this::sendSnapshot);
    }

    private void sendSnapshot(SensorsSnapshotAvro snapshot) {
        ProducerRecord<String, SensorsSnapshotAvro> producerRecord =
                new ProducerRecord<>(outputTopic,
                        null,
                        snapshot.getTimestamp().toEpochMilli(),
                        snapshot.getHubId(),
                        snapshot);
        logProducerRecord(producerRecord);
        producer.send(producerRecord, (metadata, exception) -> {
            if (exception != null) {
                log.error("Ошибка при отправке сообщения в Kafka: {}", exception.getMessage(), exception);
            } else {
                logMessageSent(producerRecord, metadata);
            }
        });
    }

    private void logReceivedRecord(ConsumerRecord<String, SensorEventAvro> record) {
        log.info("Получено сообщение от Kafka: Topic={}, Partition={}, Offset={}, Key={}, Timestamp={}",
                record.topic(),
                record.partition(),
                record.offset(),
                record.key(),
                record.timestamp());

        if (log.isDebugEnabled()) {
            log.debug("Полное сообщение: {}", record.value());
        }
    }

    private void logProducerRecord(ProducerRecord<String, SensorsSnapshotAvro> producerRecord) {
        log.info("Отправляем ProducerRecord: Topic={}, Key={}, Partition={}, Timestamp={}",
                producerRecord.topic(),
                producerRecord.key(),
                producerRecord.partition() != null ? producerRecord.partition() : "Автоматическое назначение",
                producerRecord.timestamp() != null ? producerRecord.timestamp() : "Не задан");

        if (log.isDebugEnabled()) {
            log.debug("Полное сообщение ProducerRecord: {}", producerRecord);
        }
    }

    private void logMessageSent(ProducerRecord<String, SensorsSnapshotAvro> producerRecord, RecordMetadata metadata) {
        log.info("Сообщение отправлено в Kafka: Topic={}, Offset={}", metadata.topic(), metadata.offset());

        if (log.isDebugEnabled()) {
            log.debug("Полное сообщение ProducerRecord: {}", producerRecord);
        }
    }

    private void closeResources() {
        try {
            producer.flush();
            log.info("Все данные отправлены в Kafka");
            consumer.commitSync();
            log.info("Все смещения зафиксированы");
        } finally {
            log.info("Закрываем консьюмер");
            consumer.close();
            log.info("Закрываем продюсер");
            producer.close();
        }
    }
}
