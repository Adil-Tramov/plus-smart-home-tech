package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;

/**
 * Запрос на новый заказ
 *
 * @param shoppingCart Корзина товаров в онлайн магазине
 * @param deliveryAddress Представление адреса в системе
 */
public record CreateNewOrderRequest(
        @NotNull
        ShoppingCartDto shoppingCart,

        @NotNull
        AddressDto deliveryAddress
) {
}
