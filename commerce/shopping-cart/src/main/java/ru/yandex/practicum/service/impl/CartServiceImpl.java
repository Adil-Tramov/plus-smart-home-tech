package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.model.ShoppingCartStatus;
import ru.yandex.practicum.model.mapper.ShoppingCartMapper;
import ru.yandex.practicum.repository.CartRepository;
import ru.yandex.practicum.service.CartService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Loggable
    public ShoppingCartDto getUserProductCart(String username) {
        if (!cartRepository.existsByUsername(username)) {
            throw new NoProductsInShoppingCartException("Shopping cart with this product not exist");
        }
        ShoppingCart shoppingCart = cartRepository.findByUsername(username);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Loggable
    @Transactional
    public ShoppingCartDto putProductInCart(String username, Map<UUID, Integer> products) {
        ShoppingCart shoppingCart = cartRepository
                .findByUsernameAndShoppingCartStatus(username, ShoppingCartStatus.ACTIVATE)
                .orElseGet(() -> ShoppingCart.builder()
                        .username(username)
                        .shoppingCartStatus(ShoppingCartStatus.ACTIVATE)
                        .products(new HashMap<>())
                        .build());
        shoppingCart.getProducts().putAll(products);
        ShoppingCart savedCart = cartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(savedCart);
    }

    @Loggable
    @Transactional
    public void deleteShoppingCart(String username) {
        ShoppingCart shoppingCart = cartRepository.findByUsernameAndShoppingCartStatus(username, ShoppingCartStatus.ACTIVATE)
                .orElseThrow(() -> new NoProductsInShoppingCartException("Shopping cart not exist"));
        shoppingCart.setShoppingCartStatus(ShoppingCartStatus.DEACTIVATE);
        cartRepository.save(shoppingCart);
    }

    @Loggable
    @Transactional
    public ShoppingCartDto removeProductFromCart(String username, List<UUID> products) {
        if (!cartRepository.existsByUsername(username)) {
            throw new NoProductsInShoppingCartException("Shopping cart with this product not exist");
        }
        ShoppingCart shoppingCart = cartRepository.findByUsername(username);
        Map<UUID, Integer> productMap = shoppingCart.getProducts();
        products.forEach(key -> {
            if (productMap.containsKey(key)) {
                productMap.remove(key);
            } else {
                throw new NoProductsInShoppingCartException("Shopping cart with this product not exist");
            }
        });
        shoppingCart.setProducts(productMap);
        ShoppingCart savedCart = cartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(savedCart);
    }

    @Loggable
    @Transactional
    public ShoppingCartDto changeQuantityInShoppingCart(String username, UUID productId, int newQuantity) {
        if (!cartRepository.existsByUsername(username)) {
            throw new NoProductsInShoppingCartException("Shopping cart with this product not exist");
        }
        ShoppingCart shoppingCart = cartRepository.findByUsername(username);
        Map<UUID, Integer> products = shoppingCart.getProducts();
        if (products.containsKey(productId)) {
            products.put(productId, newQuantity);
        } else {
            throw new NoProductsInShoppingCartException("Shopping cart with this product not exist");
        }
        shoppingCart.setProducts(products);
        ShoppingCart savedCart = cartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(savedCart);
    }
}