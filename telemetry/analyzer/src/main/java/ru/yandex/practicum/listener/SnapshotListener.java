package ru.yandex.practicum.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.handler.snapshot.SnapshotHandler;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class SnapshotListener {
    private final KafkaConsumer<String, SensorsSnapshotAvro> snapshotsConsumer;
    private final String snapshotsTopic;
    private final SnapshotHandler snapshotHandler;

    @Autowired
    public SnapshotListener(KafkaConsumer<String, SensorsSnapshotAvro> snapshotsConsumer,
                            String snapshotsTopic,
                            SnapshotHandler snapshotHandler) {
        this.snapshotsConsumer = snapshotsConsumer;
        this.snapshotsTopic = snapshotsTopic;
        this.snapshotHandler = snapshotHandler;
    }

    public void start() {
        try {
            snapshotsConsumer.subscribe(List.of(snapshotsTopic));
            log.info("Подписка на топик {}", snapshotsTopic);
            while (true) {
                processRecords(snapshotsConsumer.poll(Duration.ofMillis(1000)));
            }
        } catch (WakeupException ignored) {
        } catch (Exception e) {
            log.error("Ошибка во время обработки снапшота", e);
        } finally {
            shutdownConsumer();
        }
    }

    private void processRecords(ConsumerRecords<String, SensorsSnapshotAvro> records) {
        for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
            logRecordDetails(record);
            processRecord(record);
        }
        snapshotsConsumer.commitSync();
    }

    private void processRecord(ConsumerRecord<String, SensorsSnapshotAvro> record) {
        try {
            snapshotHandler.handle(record.value());
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения: {}", record.value(), e);
        }
    }

    private void logRecordDetails(ConsumerRecord<String, SensorsSnapshotAvro> record) {
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

    private void shutdownConsumer() {
        try {
            snapshotsConsumer.commitSync();
            log.info("Смещения зафиксированы");
        } finally {
            log.info("Закрываем консьюмер");
            snapshotsConsumer.close();
        }
    }
}
