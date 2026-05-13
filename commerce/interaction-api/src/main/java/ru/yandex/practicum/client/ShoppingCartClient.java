package ru.yandex.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.api.ShoppingCartOperations;

@FeignClient(name = "shopping-cart")
public interface ShoppingCartClient extends ShoppingCartOperations {
}
