package ru.yandex.practicum.controller.shopping.cart.feign;


import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.controller.shopping.cart.CartController;
import ru.yandex.practicum.fallback.shopping.cart.CartControllerFallback;

@FeignClient(name = "shopping-cart", path = "/api/v1/shopping-cart", fallback = CartControllerFallback.class)
public interface CartControllerFeign extends CartController {
}
