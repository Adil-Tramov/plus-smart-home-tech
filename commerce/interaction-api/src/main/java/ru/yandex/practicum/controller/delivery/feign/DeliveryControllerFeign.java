package ru.yandex.practicum.controller.delivery.feign;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.controller.delivery.DeliveryController;

@FeignClient(name = "delivery", path = "/api/v1/delivery")
public interface DeliveryControllerFeign extends DeliveryController {
}
