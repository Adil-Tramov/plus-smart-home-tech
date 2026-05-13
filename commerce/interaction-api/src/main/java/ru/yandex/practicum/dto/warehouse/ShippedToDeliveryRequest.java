package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

/**
 * Запрос на передачу товаров в доставку.
 * @param orderId Идентификатор заказа в БД
 * @param deliveryId Идентификатор доставки в БД
 */
@Builder
public record ShippedToDeliveryRequest(
        @NotNull
        UUID orderId,

        @NotNull
        UUID deliveryId
) {
}
