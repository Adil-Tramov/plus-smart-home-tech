package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Нет информации о товаре на складе
 */

public class NoSpecifiedProductInWarehouseException extends ErrorResponse {
    public NoSpecifiedProductInWarehouseException(UUID productId) {
        super(HttpStatus.BAD_REQUEST, "О товаре с ID " + productId + " нет информации на складе.");
    }
}
