package ru.yandex.practicum.service.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Instant;

@Slf4j
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    protected final KafkaProducer<String, SpecificRecordBase> producer;

    @Value("${kafka.topic.sensor}")
    protected String topic;

    @Autowired
    protected BaseSensorEventHandler(KafkaProducer<String, SpecificRecordBase> producer) {
        this.producer = producer;
    }

    protected abstract T mapToAvro(SensorEventProto event);

    @Override
    public void handle(SensorEventProto event) {
        if (!event.getPayloadCase().equals(getMessageType())) {
            throw new IllegalArgumentException("Неизвестный тип события: " + event.getPayloadCase());
        }

        T payload = mapToAvro(event);

        SensorEventAvro sensorEventAvro = SensorEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setId(event.getId())
                .setTimestamp(Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(),
                        event.getTimestamp().getNanos()))
                .setPayload(payload)
                .build();

        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(
                topic,
                null,
                sensorEventAvro.getTimestamp().toEpochMilli(),
                sensorEventAvro.getHubId(),
                sensorEventAvro);
        logProducerRecord(record);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                log.error("Ошибка при отправке сообщения в Kafka: {}", exception.getMessage(), exception);
            } else {
                logMessageSent(record, metadata);
            }
        });
    }

    private void logProducerRecord(ProducerRecord<String, SpecificRecordBase> producerRecord) {
        log.info("Отправляем ProducerRecord: Topic={}, Key={}, Partition={}, Timestamp={}",
                producerRecord.topic(),
                producerRecord.key(),
                producerRecord.partition() != null ? producerRecord.partition() : "Автоматическое назначение",
                producerRecord.timestamp() != null ? producerRecord.timestamp() : "Не задан");

        if (log.isDebugEnabled()) {
            log.debug("Полное сообщение ProducerRecord: {}", producerRecord);
        }
    }

    private void logMessageSent(ProducerRecord<String, SpecificRecordBase> producerRecord, RecordMetadata metadata) {
        log.info("Сообщение отправлено в Kafka: Topic={}, Offset={}", metadata.topic(), metadata.offset());

        if (log.isDebugEnabled()) {
            log.debug("Полное сообщение ProducerRecord: {}", producerRecord);
        }
    }
}
