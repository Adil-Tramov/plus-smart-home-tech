package ru.yandex.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class SnapshotStorage {
    private final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    public Optional<SensorsSnapshotAvro> update(SensorEventAvro event) {
        SensorsSnapshotAvro snapshot = snapshots.computeIfAbsent(event.getHubId(), this::createNewSnapshot);
        if (!needsUpdate(snapshot, event)) {
            log.debug("Обновление снапшота не требуется для hubId={}, sensorId={}, timestamp={}",
                    event.getHubId(), event.getId(), event.getTimestamp());
            return Optional.empty();
        }
        log.debug("Обновляем снапшот для hubId={}, sensorId={}, timestamp={}",
                event.getHubId(), event.getId(), event.getTimestamp());
        updateSensorState(snapshot, event);
        snapshots.put(event.getHubId(), snapshot);
        return Optional.of(snapshot);
    }

    private SensorsSnapshotAvro createNewSnapshot(String hubId) {
        SensorsSnapshotAvro snapshot = new SensorsSnapshotAvro();
        snapshot.setHubId(hubId);
        snapshot.setSensorsState(new HashMap<>());
        return snapshot;
    }

    private boolean needsUpdate(SensorsSnapshotAvro snapshot, SensorEventAvro event) {
        SensorStateAvro oldState = snapshot.getSensorsState().get(event.getId());
        return oldState == null ||
                !oldState.getTimestamp().isAfter(event.getTimestamp()) &&
                        !oldState.getData().equals(event.getPayload());
    }

    private void updateSensorState(SensorsSnapshotAvro snapshot, SensorEventAvro event) {
        SensorStateAvro newState = new SensorStateAvro();
        newState.setTimestamp(event.getTimestamp());
        newState.setData(event.getPayload());

        snapshot.getSensorsState().put(event.getId(), newState);
        snapshot.setTimestamp(event.getTimestamp());
        log.debug("Состояние обновлено для sensorId={}, новое состояние={}",
                event.getId(), newState);
    }
}
