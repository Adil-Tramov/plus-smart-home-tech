package ru.yandex.practicum.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.SetProductQuantityStateRequest;

import java.util.List;
import java.util.UUID;

public interface ShoppingStoreOperations {
    @GetMapping("/{productId}")
    ProductDto getProductById(@PathVariable UUID productId);

    @GetMapping
    List<ProductDto> getProducts(@RequestParam ProductCategory category, Pageable pageable);

    @PostMapping
    ProductDto addProduct(@RequestBody @Valid ProductDto productDto);

    @PutMapping
    ProductDto updateProduct(@RequestBody @Valid ProductDto productDto);

    @PutMapping("/quantityState")
    boolean updateQuantityState(@RequestBody @Valid SetProductQuantityStateRequest setProductQuantityStateRequest);

    @PutMapping("/removeProductFromStore")
    boolean removeProduct(@RequestParam @NotNull UUID productId);
}
