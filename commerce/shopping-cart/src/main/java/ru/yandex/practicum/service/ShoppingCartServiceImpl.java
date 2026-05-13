package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.client.WarehouseClient;
import ru.yandex.practicum.dto.cart.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.exception.ShoppingCartInactiveException;
import ru.yandex.practicum.mapper.ShoppingCartMapper;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.model.ShoppingCartProduct;
import ru.yandex.practicum.model.ShoppingCartState;
import ru.yandex.practicum.repository.ShoppingCartRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final WarehouseClient warehouseClient;

    @Transactional
    @Override
    public ShoppingCartDto getShoppingCart(String username) {
        log.debug("Запрашиваем корзину пользователя: {}", username);
        ShoppingCart shoppingCart = findOrCreateShoppingCart(username);
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        log.debug("Возвращаем корзину пользователя {}", shoppingCartDto);
        return shoppingCartDto;
    }

    @Transactional
    @Override
    public ShoppingCartDto addProductsToCart(String username, Map<UUID, Long> products) {
        log.debug("Добавляем товары: {} в корзину пользователя: {}", products, username);
        ShoppingCart shoppingCart = findOrCreateShoppingCart(username);
        if (!shoppingCart.isActive()) {
            throw new ShoppingCartInactiveException(shoppingCart.getId());
        }
        List<ShoppingCartProduct> shoppingCartProducts =
                shoppingCartMapper.toShoppingCartProduct(products, shoppingCart);
        shoppingCart.getProducts().addAll(shoppingCartProducts);
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        log.debug("Возвращаем обновленную корзину пользователя {}", shoppingCartDto);
        return shoppingCartDto;
    }

    @Transactional
    @Override
    public void deactivateShoppingCart(String username) {
        log.debug("Деактивируем корзину пользователя: {}", username);
        ShoppingCart shoppingCart = findOrCreateShoppingCart(username);
        shoppingCart.setState(ShoppingCartState.INACTIVE);
        log.debug("Деактивировали корзину: {}", shoppingCart);
    }

    @Transactional
    @Override
    public ShoppingCartDto replaceShoppingCartContents(String username, Map<UUID, Long> products) {
        log.debug("Заменяем товары: {} в корзине пользователя: {}", products, username);
        ShoppingCart shoppingCart = findOrCreateShoppingCart(username);
        if (!shoppingCart.isActive()) {
            throw new ShoppingCartInactiveException(shoppingCart.getId());
        }
        List<ShoppingCartProduct> shoppingCartProducts =
                shoppingCartMapper.toShoppingCartProduct(products, shoppingCart);
        shoppingCart.getProducts().clear();
        shoppingCart.getProducts().addAll(shoppingCartProducts);
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        log.debug("Возвращаем обновленную корзину пользователя {}", shoppingCartDto);
        return shoppingCartDto;
    }

    @Transactional
    @Override
    public ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request) {
        log.debug("Изменяем количество товаров в корзине пользователя: {} {}", username, request);
        ShoppingCart shoppingCart = findOrCreateShoppingCart(username);
        if (!shoppingCart.isActive()) {
            throw new ShoppingCartInactiveException(shoppingCart.getId());
        }
        shoppingCart.getProducts().stream()
                .filter(product -> product.getProductId().equals(request.productId()))
                .findAny()
                .orElseThrow(() -> new NoProductsInShoppingCartException(request.productId()))
                .setQuantity(request.newQuantity());
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        log.debug("Возвращаем обновленную корзину пользователя {}", shoppingCartDto);
        return shoppingCartDto;
    }

    @Transactional
    @Override
    public BookedProductsDto bookProducts(String username) {
        log.debug("Бронируем товары в корзине пользователя: {}", username);
        ShoppingCart shoppingCart = findOrCreateShoppingCart(username);
        if (!shoppingCart.isActive()) {
            throw new ShoppingCartInactiveException(shoppingCart.getId());
        }
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        BookedProductsDto bookedProductsDto = warehouseClient.bookProducts(shoppingCartDto);
        log.debug("Забронировали товары в корзине : {}", bookedProductsDto);
        return bookedProductsDto;
    }

    private ShoppingCart findOrCreateShoppingCart(String username) {
        if (username.isBlank()) {
            throw new NotAuthorizedUserException(username);
        }
        return shoppingCartRepository.findByUsername(username)
                .orElseGet(() -> {
                    log.debug("Корзина для пользователя: {} не найдена в DB. Создаем новую.", username);
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setUsername(username);
                    return shoppingCartRepository.save(shoppingCart);
                });
    }
}
