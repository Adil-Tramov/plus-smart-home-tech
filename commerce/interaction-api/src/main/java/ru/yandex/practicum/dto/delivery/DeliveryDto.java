package ru.yandex.practicum.dto.delivery;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.yandex.practicum.dto.warehouse.AddressDto;

import java.util.UUID;

/**
 * Представление доставки в системе
 *
 * @param deliveryId Идентификатор доставки
 * @param fromAddress Адрес отправителя
 * @param toAddress Адрес получателя
 * @param orderId Идентификатор заказа
 * @param deliveryState Статус доставки
 */
@Builder
public record DeliveryDto(
        @NotNull
        UUID deliveryId,

        @NotNull
        AddressDto fromAddress,

        @NotNull
        AddressDto toAddress,

        @NotNull
        UUID orderId,

        @NotNull
        DeliveryState deliveryState
) {
}
