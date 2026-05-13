package ru.yandex.practicum.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class ProductNotFoundException extends ErrorResponse {
    public ProductNotFoundException(UUID productId) {
        super(HttpStatus.NOT_FOUND, "Товар с ID " + productId + " не найден.");
    }
}
