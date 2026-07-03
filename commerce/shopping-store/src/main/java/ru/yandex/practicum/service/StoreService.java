package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.model.ProductCategory;
import ru.yandex.practicum.model.QuantityState;

import java.util.List;
import java.util.UUID;

public interface StoreService {

    Product createNewProduct(Product product);

    Product updateProduct(Product product);

    boolean deleteProduct(UUID productId);

    Product getProductInfo(UUID id);

    Product changeQuantityState(UUID productId, QuantityState quantityState);

    Page<Product> getListOfProducts(ProductCategory category,
                                    Pageable pageable);

    List<Product> getAllProductsFromList(List<UUID> productsId);
}
