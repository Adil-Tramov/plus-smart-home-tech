package ru.yandex.practicum.dto.product;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Запрос на изменение статуса остатка товара
 *
 * @param productId         идентификатор товара
 * @param quantityState     статус, перечисляющий состояние остатка как свойства товара
 */
public record SetProductQuantityStateRequest(
        @NotNull
        UUID productId,

        @NotNull
        QuantityState quantityState
) {
}
