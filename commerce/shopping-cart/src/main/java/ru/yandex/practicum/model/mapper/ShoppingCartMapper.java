package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.model.ShoppingCart;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ShoppingCartMapper {

    public ShoppingCart toEntity(ShoppingCartDto dto, String username) {
        if (dto == null) {
            return null;
        }

        ShoppingCart.ShoppingCartBuilder shoppingCart = ShoppingCart.builder();

        shoppingCart.shoppingCartId(dto.getShoppingCartId());
        Map<UUID, Integer> map = dto.getProducts();
        if (map != null) {
            shoppingCart.products(new LinkedHashMap<UUID, Integer>(map));
        }
        shoppingCart.username(username);

        return shoppingCart.build();
    }

    public ShoppingCartDto toDto(ShoppingCart entity) {
        if (entity == null) {
            return null;
        }

        ShoppingCartDto.ShoppingCartDtoBuilder shoppingCartDto = ShoppingCartDto.builder();

        shoppingCartDto.shoppingCartId(entity.getShoppingCartId());
        Map<UUID, Integer> map = entity.getProducts();
        if (map != null) {
            shoppingCartDto.products(new LinkedHashMap<UUID, Integer>(map));
        }
        shoppingCartDto.shoppingCartId(entity.getShoppingCartId());
        return shoppingCartDto.build();
    }
}
