package ru.yandex.practicum.sensor.simulation;

import java.util.concurrent.ThreadLocalRandom;

public enum SensorType {
    CLIMATE,
    LIGHT,
    MOTION,
    SWITCH,
    TEMPERATURE;

    public static SensorType getRandomType() {
        SensorType[] types = values();
        int randomIndex = ThreadLocalRandom.current().nextInt(types.length);
        return types[randomIndex];
    }
}