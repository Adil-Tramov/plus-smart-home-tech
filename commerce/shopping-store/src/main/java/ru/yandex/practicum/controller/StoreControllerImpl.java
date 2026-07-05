package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.controller.shopping.store.StoreController;
import ru.yandex.practicum.dto.ProductCategoryDto;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.QuantityStateDto;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.model.ProductCategory;
import ru.yandex.practicum.model.QuantityState;
import ru.yandex.practicum.model.mapper.ProductCategoryMapper;
import ru.yandex.practicum.model.mapper.ProductMapper;
import ru.yandex.practicum.model.mapper.QuantityStateMapper;
import ru.yandex.practicum.service.StoreService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
@Validated
public class StoreControllerImpl implements StoreController {
    private final StoreService storeService;
    private final ProductMapper productMapper;
    private final ProductCategoryMapper productCategoryMapper;
    private final QuantityStateMapper quantityStateMapper;

    @Loggable
    @GetMapping
    public Page<ProductDto> getListOfProducts(@RequestParam ProductCategoryDto category,
                                              @PageableDefault(size = 20, sort = "productName",
                                                      direction = Sort.Direction.ASC) Pageable pageable) {
        ProductCategory productCategory = productCategoryMapper.toEntity(category);
        Page<Product> products = storeService.getListOfProducts(productCategory, pageable);
        return products.map(productMapper::toDto);
    }

    @Loggable
    @PutMapping
    public ProductDto createNewProduct(@RequestBody @Valid ProductDto dto) {
        Product product = productMapper.toEntity(dto);
        product = storeService.createNewProduct(product);
        return productMapper.toDto(product);
    }

    @Loggable
    @PostMapping
    public ProductDto updateProductInfo(@RequestBody @Valid ProductDto dto) {
        Product product = productMapper.toEntity(dto);
        product = storeService.updateProduct(product);
        return productMapper.toDto(product);
    }

    @Loggable
    @PostMapping("/removeProductFromStore")
    public boolean removeProductFromStore(@RequestBody @NotNull UUID productId) {
        return storeService.deleteProduct(productId);
    }

    @Loggable
    @PostMapping("/quantityState")
    public ProductDto changeQuantityState(@RequestParam @NotNull UUID productId,
                                          @RequestParam QuantityStateDto quantityState) {
        QuantityState state = quantityStateMapper.toEntity(quantityState);
        Product product = storeService.changeQuantityState(productId, state);
        return productMapper.toDto(product);
    }

    @Loggable
    @GetMapping("/{productId}")
    public ProductDto getProductInfo(@PathVariable @NotNull UUID productId) {
        Product product = storeService.getProductInfo(productId);
        return productMapper.toDto(product);
    }
}
