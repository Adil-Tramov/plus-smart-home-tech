package ru.yandex.practicum.fallback.shopping.cart;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.controller.shopping.cart.feign.CartControllerFeign;
import ru.yandex.practicum.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class CartControllerFallback implements CartControllerFeign {

    @Override
    public ShoppingCartDto getShoppingCart(@RequestParam String username) {
        return null;
    }

    @Override
    public ShoppingCartDto putProductInCart(@RequestParam String username,
                                            @RequestBody Map<UUID, Integer> productCart) {
        return null;
    }

    @Override
    public void deleteShoppingCart(@RequestParam String username) {

    }

    @Override
    public ShoppingCartDto removeProductFromCart(@RequestParam String username,
                                                 @RequestBody List<UUID> productId) {
        return null;
    }

    @Override
    public ShoppingCartDto changeProductQuantityInCart(@RequestParam String username,
                                                       @RequestBody ChangeProductQuantityRequest request) {
        return null;
    }
}
