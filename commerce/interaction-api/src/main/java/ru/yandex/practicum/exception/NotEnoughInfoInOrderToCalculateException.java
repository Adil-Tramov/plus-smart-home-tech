package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Не достаточно информации в заказе для расчета стоимости
 */

public class NotEnoughInfoInOrderToCalculateException extends ErrorResponse {
    public NotEnoughInfoInOrderToCalculateException(UUID orderId) {
        super(HttpStatus.BAD_REQUEST, "В заказе с ID " + orderId +
                " не достаточно информации для расчета стоимости.");
    }
}
