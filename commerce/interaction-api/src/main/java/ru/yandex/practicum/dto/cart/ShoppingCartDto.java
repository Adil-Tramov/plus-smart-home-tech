package ru.yandex.practicum.dto.cart;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.Map;
import java.util.UUID;

/**
 * Корзина товаров в онлайн магазине.
 *
 * @param shoppingCartId Идентификатор корзины в БД
 * @param products       Отображение идентификатора товара на отобранное количество.
 */

@Builder
public record ShoppingCartDto(
        @NotNull
        UUID shoppingCartId,

        @NotNull
        @Valid
        Map<@NotNull UUID, @Positive Long> products
) {
}
