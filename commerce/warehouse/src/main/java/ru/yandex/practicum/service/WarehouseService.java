package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.BookedProducts;
import ru.yandex.practicum.model.Product;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {

    Product saveProductInWarehouse(Product product);

    BookedProducts checkShoppingCart(Map<UUID, Integer> products);

    void addQuantityInProduct(UUID id, int quantity);

    Address getWarehouseAddress();
}
