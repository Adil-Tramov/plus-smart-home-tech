package ru.yandex.practicum.controller.shopping.store.feign;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.controller.shopping.store.StoreController;
import ru.yandex.practicum.fallback.shopping.store.StoreControllerFallback;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store", fallback = StoreControllerFallback.class)
public interface StoreControllerFeign extends StoreController {
}
