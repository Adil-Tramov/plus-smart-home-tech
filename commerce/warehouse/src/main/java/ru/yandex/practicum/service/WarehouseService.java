package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequest;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {

    void saveProductInWarehouse(NewProductInWarehouseRequest productDto);

    BookedProductsDto checkShoppingCart(Map<UUID, Integer> products);

    void addQuantityInProduct(UUID id, int quantity);

    AddressDto getWarehouseAddress();

    void returnProductsInWarehouse(Map<UUID, Integer> products);

    BookedProductsDto assemblyProductsForDelivery(Map<UUID, Integer> products);

    void shippedInDelivery(UUID orderId, UUID deliveryId);
}