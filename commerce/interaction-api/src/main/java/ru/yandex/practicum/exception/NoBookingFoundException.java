package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Нет информации о забронированных товаров к заказу
 */

public class NoBookingFoundException extends ErrorResponse {
    public NoBookingFoundException(UUID orderId) {
        super(HttpStatus.BAD_REQUEST, "Для заказа с ID " + orderId + " нет забронированных товаров на складе.");
    }
}
