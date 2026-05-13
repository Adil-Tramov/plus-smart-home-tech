package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.Map;
import java.util.UUID;

/**
 * Запрос на сбор заказа из товаров
 *
 * @param products Отображение идентификатора товара на отобранное количество
 * @param orderId Идентификатор заказа в БД
 */
@Builder
public record AssemblyProductsForOrderRequest(
        @NotNull
        @Valid
        Map<@NotNull UUID, @Positive Long> products,

        @NotNull
        UUID orderId
) {
}
