package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.cart.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(String username);

    ShoppingCartDto addProductsToCart(String username, Map<UUID, Long> products);

    void deactivateShoppingCart(String username);

    ShoppingCartDto replaceShoppingCartContents(String username, Map<UUID, Long> products);

    ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request);

    BookedProductsDto bookProducts(String username);
}
