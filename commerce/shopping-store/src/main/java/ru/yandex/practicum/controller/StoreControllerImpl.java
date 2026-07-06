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
import ru.yandex.practicum.service.StoreService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
@Validated
public class StoreControllerImpl implements StoreController {
    private final StoreService storeService;

    @Loggable
    @GetMapping
    public Page<ProductDto> getListOfProducts(@RequestParam ProductCategoryDto category,
                                              @PageableDefault(size = 20, sort = "productName",
                                                      direction = Sort.Direction.ASC) Pageable pageable) {
        return storeService.getListOfProducts(category, pageable);
    }

    @Loggable
    @PostMapping("/getAll")
    public List<ProductDto> getAllProductsFromList(@RequestBody List<UUID> productsId) {
        return storeService.getAllProductsFromList(productsId);
    }

    @Loggable
    @PutMapping
    public ProductDto createNewProduct(@RequestBody @Valid ProductDto dto) {
        return storeService.createNewProduct(dto);
    }

    @Loggable
    @PostMapping
    public ProductDto updateProductInfo(@RequestBody @Valid ProductDto dto) {
        return storeService.updateProduct(dto);
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
        return storeService.changeQuantityState(productId, quantityState);
    }

    @Loggable
    @GetMapping("/{productId}")
    public ProductDto getProductInfo(@PathVariable @NotNull UUID productId) {
        return storeService.getProductInfo(productId);
    }
}