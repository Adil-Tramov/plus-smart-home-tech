package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.cart.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {
    void addProduct(NewProductInWarehouseRequest request);

    BookedProductsDto check(ShoppingCartDto shoppingCartDto);

    void increaseQuantity(AddProductToWarehouseRequest request);

    AddressDto getAddress();

    void shipToDelivery(ShippedToDeliveryRequest request);

    void returnProductsToWarehouse(Map<UUID, Long> products);

    BookedProductsDto assemblyProducts(AssemblyProductsForOrderRequest request);
}
