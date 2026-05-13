package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Нет информации о доставке
 */

public class NoDeliveryFoundException extends ErrorResponse {
    public NoDeliveryFoundException(UUID orderId) {
        super(HttpStatus.BAD_REQUEST, "О доставке для заказа с ID " + orderId + " нет информации в системе.");
    }
}
