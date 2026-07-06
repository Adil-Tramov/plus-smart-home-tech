package ru.yandex.practicum.controller.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.controller.order.OrderController;

@FeignClient(name = "order", path = "/api/v1/order")
public interface OrderControllerFeign extends OrderController {
}
