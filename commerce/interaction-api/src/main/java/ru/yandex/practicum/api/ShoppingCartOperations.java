package ru.yandex.practicum.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.cart.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

/**
 * API для обеспечения работы корзины онлайн магазина
 */
public interface ShoppingCartOperations {
    /**
     * Получить актуальную корзину для авторизованного пользователя
     *
     * @param username Имя пользователя
     * @return Ранее созданная или новая корзина в онлайн магазине
     */
    @GetMapping
    ShoppingCartDto getShoppingCart(@RequestParam String username);

    /**
     * Добавить товар в корзину
     *
     * @param username Имя пользователя
     * @param products Отображение идентификатора товара на отобранное количество
     * @return Корзина товаров с изменениями
     */
    @PostMapping
    ShoppingCartDto addProductsToCart(@RequestParam String username,
                                      @RequestBody Map<UUID, Long> products);

    /**
     * Деактивация корзины товаров для пользователя.
     *
     * @param username Имя пользователя
     */
    @DeleteMapping
    void deactivateShoppingCart(@RequestParam String username);

    /**
     * Изменить состав товаров в корзине, т.е. удалить другие
     *
     * @param username Имя пользователя
     * @param products Отображение идентификатора товара на отобранное количество
     * @return Корзина товаров с изменениями
     */
    @PutMapping("/remove")
    ShoppingCartDto replaceShoppingCartContents(@RequestParam String username,
                                                @RequestBody Map<UUID, Long> products);

    /**
     * Изменить количество товаров в корзине
     *
     * @param username Имя пользователя
     * @param request  Запрос на изменение количества единиц товара
     * @return Корзина товаров с изменениями
     */
    @PutMapping("/change-quantity")
    ShoppingCartDto changeProductQuantity(@RequestParam String username,
                                          @RequestBody @Valid ChangeProductQuantityRequest request);

    /**
     * Зарезервировать товары на складе
     *
     * @param username Имя пользователя
     * @return Описательный объект со сведениями о бронировании
     */
    @PostMapping("/booking")
    BookedProductsDto bookProducts(@RequestParam String username);
}
