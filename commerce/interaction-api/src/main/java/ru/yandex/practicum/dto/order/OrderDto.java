package ru.yandex.practicum.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Представление заказа в системе.
 *
 * @param orderId Идентификатор заказа
 * @param shoppingCartId Идентификатор корзины
 * @param products Отображение идентификатора товара на отобранное количество
 * @param paymentId Идентификатор оплаты
 * @param deliveryId Идентификатор доставки
 * @param state Статус заказа
 * @param deliveryWeight Общий вес доставки
 * @param deliveryVolume Общий объём доставки
 * @param fragile Признак хрупкости заказа
 * @param totalPrice Общая стоимость
 * @param deliveryPrice Стоимость доставки
 * @param productPrice Стоимость товаров в заказе
 */
@Builder
public record OrderDto(
        @NotNull
        UUID orderId,

        UUID shoppingCartId,

        @NotNull
        @Valid
        Map<@NotNull UUID, @Positive Long> products,

        UUID paymentId,

        UUID deliveryId,

        OrderState state,

        Double deliveryWeight,

        Double deliveryVolume,

        Boolean fragile,

        BigDecimal totalPrice,

        BigDecimal deliveryPrice,

        BigDecimal productPrice
) {
}
