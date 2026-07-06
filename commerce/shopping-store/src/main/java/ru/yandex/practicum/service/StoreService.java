package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.ProductCategoryDto;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.QuantityStateDto;

import java.util.List;
import java.util.UUID;

public interface StoreService {

    ProductDto createNewProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    boolean deleteProduct(UUID productId);

    ProductDto getProductInfo(UUID id);

    ProductDto changeQuantityState(UUID productId, QuantityStateDto quantityState);

    Page<ProductDto> getListOfProducts(ProductCategoryDto category, Pageable pageable);

    List<ProductDto> getAllProductsFromList(List<UUID> productsId);
}