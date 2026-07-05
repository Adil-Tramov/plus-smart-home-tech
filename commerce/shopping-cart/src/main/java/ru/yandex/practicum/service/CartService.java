package ru.yandex.practicum.service;

import ru.yandex.practicum.model.ShoppingCart;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartService {

    public ShoppingCart getUserProductCart(String username);

    public ShoppingCart putProductInCart(String username, Map<UUID, Integer> products);

    void deleteShoppingCart(String username);

    ShoppingCart removeProductFromCart(String username, List<UUID> productId);

    ShoppingCart changeQuantityInShoppingCart(String username, UUID productId, int newQuantity);
}

