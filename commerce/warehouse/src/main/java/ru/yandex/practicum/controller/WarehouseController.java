package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.WarehouseOperations;
import ru.yandex.practicum.dto.cart.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.service.WarehouseService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController implements WarehouseOperations {
    private final WarehouseService warehouseService;

    @Override
    public void addProduct(@Valid NewProductInWarehouseRequest request) {
        log.info("POST /api/v1/warehouse - Добавление нового товара на склад: {}", request);
        warehouseService.addProduct(request);
        log.info("Успешно добавили продукт на склад.");
    }

    @Override
    public BookedProductsDto bookProducts(@Valid ShoppingCartDto shoppingCartDto) {
        log.info("POST /api/v1/warehouse/check - Проверка, что товаров на складе достаточно для корзины с ID: {}",
                shoppingCartDto.shoppingCartId());
        log.info("Корзина: {}", shoppingCartDto);
        BookedProductsDto response = warehouseService.check(shoppingCartDto);
        log.info("Сведения о забронированных товарах: {}", response);
        return response;
    }

    @Override
    public void increaseQuantity(@Valid AddProductToWarehouseRequest request) {
        log.info("POST /api/v1/warehouse/add - Добавление единиц товара на склад: {}", request);
        warehouseService.increaseQuantity(request);
        log.info("Успешно увеличили количество товара на складе.");
    }

    @Override
    public AddressDto getAddress() {
        log.info("GET /api/v1/warehouse/address - Предоставить адрес склада.");
        AddressDto response = warehouseService.getAddress();
        log.info("Возвращаем адрес: {}", response);
        return response;
    }

    @Override
    public void shipToDelivery(ShippedToDeliveryRequest request) {
        log.info("POST /api/v1/warehouse/shipped - Передать товары в доставку: {}", request);
        warehouseService.shipToDelivery(request);
        log.info("Передали товары в доставку: {}", request);
    }

    @Override
    public void returnProductsToWarehouse(Map<UUID, Long> products) {
        log.info("POST /api/v1/warehouse/return - Принять возврат товаров на склад: {}", products);
        warehouseService.returnProductsToWarehouse(products);
        log.info("Приняли товары на склад: {}", products);
    }

    @Override
    public BookedProductsDto assemblyProducts(AssemblyProductsForOrderRequest request) {
        log.info("POST /api/v1/warehouse/assembly - Собрать товары к заказу ID: {}", request.orderId());
        BookedProductsDto response = warehouseService.assemblyProducts(request);
        log.info("Собрали товары к доставке: {}", response);
        return response;
    }
}
