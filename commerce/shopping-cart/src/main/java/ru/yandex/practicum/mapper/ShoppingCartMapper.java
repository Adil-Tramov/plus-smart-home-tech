package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.model.ShoppingCartProduct;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ShoppingCartMapper {
    public ShoppingCartDto toDto(ShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            return null;
        }
        Map<UUID, Long> products = shoppingCart.getProducts().stream()
                .collect(Collectors.toMap(ShoppingCartProduct::getProductId, ShoppingCartProduct::getQuantity));
        return ShoppingCartDto.builder()
                .shoppingCartId(shoppingCart.getId())
                .products(products)
                .build();
    }

    public List<ShoppingCartProduct> toShoppingCartProduct(Map<UUID, Long> products, ShoppingCart shoppingCart) {
        if (products == null || products.isEmpty() || shoppingCart == null) {
            return null;
        }
        return products.entrySet().stream()
                .map(entry -> {
                    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
                    shoppingCartProduct.setShoppingCart(shoppingCart);
                    shoppingCartProduct.setProductId(entry.getKey());
                    shoppingCartProduct.setQuantity(entry.getValue());
                    return shoppingCartProduct;
                }).collect(Collectors.toList());
    }
}
