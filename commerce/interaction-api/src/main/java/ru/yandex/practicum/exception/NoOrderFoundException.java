package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Нет информации о заказе
 */

public class NoOrderFoundException extends ErrorResponse {
    public NoOrderFoundException(UUID orderId) {
        super(HttpStatus.BAD_REQUEST, "О заказе с ID " + orderId + " нет информации в системе.");
    }
}
