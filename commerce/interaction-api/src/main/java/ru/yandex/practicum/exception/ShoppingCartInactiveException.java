package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ShoppingCartInactiveException extends ErrorResponse {
    public ShoppingCartInactiveException(UUID shoppingCartId) {
        super(HttpStatus.NOT_FOUND, "Корзина с ID " + shoppingCartId + " деактивирована.");
    }
}
