package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class DeliveryForOrderAlreadyExistsException extends ErrorResponse {
    public DeliveryForOrderAlreadyExistsException(UUID orderId) {
        super(HttpStatus.BAD_REQUEST, "Доставка для заказа с ID " + orderId + " уже создана.");
    }
}
