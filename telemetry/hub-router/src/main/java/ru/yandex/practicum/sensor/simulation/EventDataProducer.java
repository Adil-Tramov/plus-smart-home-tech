package ru.yandex.practicum.sensor.simulation;

import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.SensorConfig;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class EventDataProducer {
    private final SensorConfig sensorConfig;
    private final ValueGenerator valueGenerator;
    private final CollectorControllerGrpc.CollectorControllerBlockingStub collectorStub;

    @Autowired
    public EventDataProducer(SensorConfig sensorConfig,
                             ValueGenerator valueGenerator,
                             @GrpcClient("collector") CollectorControllerGrpc.CollectorControllerBlockingStub collectorStub) {
        this.sensorConfig = sensorConfig;
        this.valueGenerator = valueGenerator;
        this.collectorStub = collectorStub;
    }

    @Scheduled(fixedRate = 3000)
    public void generateAndSendRandomEvent() {
        SensorType sensorType = SensorType.getRandomType();
        log.debug("Выбран датчик типа: {}", sensorType);

        switch (sensorType) {
            case CLIMATE -> getRandomSensor(sensorConfig.getClimateSensors())
                    .ifPresent(sensor -> sendEvent(createClimateSensorEvent(sensor)));
            case LIGHT -> getRandomSensor(sensorConfig.getLightSensors())
                    .ifPresent(sensor -> sendEvent(createLightSensorEvent(sensor)));
            case MOTION -> getRandomSensor(sensorConfig.getMotionSensors())
                    .ifPresent(sensor -> sendEvent(createMotionSensorEvent(sensor)));
            case SWITCH -> getRandomSensor(sensorConfig.getSwitchSensors())
                    .ifPresent(sensor -> sendEvent(createSwitchSensorEvent(sensor)));
            case TEMPERATURE -> getRandomSensor(sensorConfig.getTemperatureSensors())
                    .ifPresent(sensor -> sendEvent(createTemperatureSensorEvent(sensor)));
        }
    }

    private void sendEvent(SensorEventProto event) {
        log.info("Отправляем данные датчика c id: {}", event.getId());
        log.debug("Отправляем данные датчика: {}", event.getAllFields());
        collectorStub.collectSensorEvent(event);
    }

    private <T> Optional<T> getRandomSensor(List<T> sensors) {
        return sensors.isEmpty() ?
                Optional.empty() :
                Optional.of(sensors.get(ThreadLocalRandom.current().nextInt(sensors.size())));
    }

    private SensorEventProto createTemperatureSensorEvent(SensorConfig.TemperatureSensor sensor) {
        int temperatureCelsius = valueGenerator.generateValueWithDeviation(sensor.getTemperature(), sensor.getId());
        int temperatureFahrenheit = (int) (temperatureCelsius * 1.8 + 32);
        Instant ts = Instant.now();

        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setHubId("TestHubId")
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setTemperatureSensor(
                        TemperatureSensorEvent.newBuilder()
                                .setTemperatureC(temperatureCelsius)
                                .setTemperatureF(temperatureFahrenheit)
                                .build()
                )
                .build();
    }

    private SensorEventProto createLightSensorEvent(SensorConfig.LightSensor sensor) {
        int luminosity = valueGenerator.generateValueWithDeviation(sensor.getLuminosity(), sensor.getId());
        Instant ts = Instant.now();

        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setHubId("TestHubId")
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setLightSensor(
                        LightSensorEvent.newBuilder()
                                .setLuminosity(luminosity)
                                .build()
                )
                .build();
    }

    private SensorEventProto createMotionSensorEvent(SensorConfig.MotionSensor sensor) {
        boolean isMotion = ThreadLocalRandom.current().nextBoolean();
        int linkQuality = valueGenerator.generateValueWithDeviation(sensor.getLinkQuality(), sensor.getId() + "-linkQuality");
        int voltage = valueGenerator.generateValueWithDeviation(sensor.getVoltage(), sensor.getId() + "-voltage");
        Instant ts = Instant.now();

        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setHubId("TestHubId")
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setMotionSensor(
                        MotionSensorEvent.newBuilder()
                                .setMotion(isMotion)
                                .setLinkQuality(linkQuality)
                                .setVoltage(voltage)
                                .build()
                )
                .build();
    }

    private SensorEventProto createSwitchSensorEvent(SensorConfig.SwitchSensor sensor) {
        boolean state = ThreadLocalRandom.current().nextBoolean();
        Instant ts = Instant.now();

        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setHubId("TestHubId")
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setSwitchSensor(
                        SwitchSensorEvent.newBuilder()
                                .setState(state)
                                .build()
                )
                .build();
    }

    private SensorEventProto createClimateSensorEvent(SensorConfig.ClimateSensor sensor) {
        int temperature = valueGenerator.generateValueWithDeviation(sensor.getTemperature(), sensor.getId() + "-temperature");
        int humidity = valueGenerator.generateValueWithDeviation(sensor.getHumidity(), sensor.getId() + "-humidity");
        int co2Level = valueGenerator.generateValueWithDeviation(sensor.getCo2Level(), sensor.getId() + "-co2");
        Instant ts = Instant.now();

        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setHubId("TestHubId")
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setClimateSensor(
                        ClimateSensorEvent.newBuilder()
                                .setTemperatureC(temperature)
                                .setHumidity(humidity)
                                .setCo2Level(co2Level)
                                .build()
                )
                .build();
    }
}
