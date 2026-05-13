package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class SpecifiedProductAlreadyInWarehouseException extends ErrorResponse {
    public SpecifiedProductAlreadyInWarehouseException(UUID productId) {
        super(HttpStatus.BAD_REQUEST, "Товар с ID " + productId + " уже есть на складе.");
    }
}
