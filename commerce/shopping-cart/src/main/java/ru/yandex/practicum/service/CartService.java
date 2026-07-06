package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartService {

    ShoppingCartDto getUserProductCart(String username);

    ShoppingCartDto putProductInCart(String username, Map<UUID, Integer> products);

    void deleteShoppingCart(String username);

    ShoppingCartDto removeProductFromCart(String username, List<UUID> productId);

    ShoppingCartDto changeQuantityInShoppingCart(String username, UUID productId, int newQuantity);
}