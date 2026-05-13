package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class NoProductsInShoppingCartException extends ErrorResponse {
    public NoProductsInShoppingCartException(UUID productId) {
        super(HttpStatus.BAD_REQUEST, "Товара с ID: " + productId + " нет в корзине.");
    }
}
