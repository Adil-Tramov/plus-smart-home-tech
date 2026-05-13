package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.UUID;

/**
 * Ошибка, товар из корзины не находится в требуемом количестве на складе
 */

public class ProductInShoppingCartLowQuantityInWarehouse extends ErrorResponse {
    public ProductInShoppingCartLowQuantityInWarehouse(Set<UUID> productIds) {
        super(HttpStatus.BAD_REQUEST,
                "Товары из корзины с ID " + productIds + " не находится в требуемом количестве на складе.");
    }
}
