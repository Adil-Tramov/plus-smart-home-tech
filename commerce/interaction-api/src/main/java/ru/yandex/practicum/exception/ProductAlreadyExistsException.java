package ru.yandex.practicum.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class ProductAlreadyExistsException extends ErrorResponse {
    public ProductAlreadyExistsException(UUID productId) {
        super(HttpStatus.CONFLICT,
                "Товар с ID " + productId + " уже существует.");
    }
}