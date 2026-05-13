package ru.yandex.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.api.ShoppingStoreOperations;
import ru.yandex.practicum.dto.product.ProductDto;

import java.util.UUID;

@FeignClient(name = "shopping-store")
public interface ShoppingStoreClient extends ShoppingStoreOperations {
    @Override
    @GetMapping("/api/v1/shopping-store/{productId}")
    ProductDto getProductById(UUID productId);
}
