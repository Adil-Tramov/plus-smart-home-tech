package ru.yandex.practicum.controller.shopping.store;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ProductCategoryDto;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.QuantityStateDto;

import java.util.List;
import java.util.UUID;

public interface StoreController {

    @GetMapping
    Page<ProductDto> getListOfProducts(@RequestParam ProductCategoryDto category,
                                       @PageableDefault(size = 20, sort = "productName",
                                               direction = Sort.Direction.ASC) Pageable pageable);

    @PostMapping("/getAll")
    List<ProductDto> getAllProductsFromList(@RequestBody List<UUID> productsId);

    @PutMapping
    ProductDto createNewProduct(@RequestBody @Valid ProductDto product);

    @PostMapping
    ProductDto updateProductInfo(@RequestBody @Valid ProductDto product);

    @PostMapping("/removeProductFromStore")
    boolean removeProductFromStore(@RequestBody @NotNull UUID productId);

    @PostMapping("/quantityState")
    ProductDto changeQuantityState(@RequestParam @NotNull UUID productId,
                                   @RequestParam QuantityStateDto quantityState);

    @GetMapping("/{productId}")
    ProductDto getProductInfo(@PathVariable @NotNull UUID id);
}
