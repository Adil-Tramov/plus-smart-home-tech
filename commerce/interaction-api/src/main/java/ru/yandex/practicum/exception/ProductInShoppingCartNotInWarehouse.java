package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.UUID;

public class ProductInShoppingCartNotInWarehouse extends ErrorResponse {
    public ProductInShoppingCartNotInWarehouse(Set<UUID> productIds) {
        super(HttpStatus.CONFLICT, "Товаров с ID " + productIds + " нет на складе.");
    }
}
