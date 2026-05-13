package ru.yandex.practicum.sensor.simulation;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.SensorConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
class ValueGenerator {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final Map<String, Integer> lastValues = new HashMap<>();

    public int generateValueWithDeviation(SensorConfig.Range range, String sensorId) {
        int lastValue = lastValues.getOrDefault(sensorId, range.getMinValue());
        int deviation = random.nextInt(-1, 2); // Отклонение от -1 до 1
        int newValue = lastValue + deviation;
        // Ограничиваем новое значение диапазоном датчика
        newValue = Math.min(range.getMaxValue(), newValue);
        newValue = Math.max(range.getMinValue(), newValue);
        lastValues.put(sensorId, newValue);
        return newValue;
    }
}