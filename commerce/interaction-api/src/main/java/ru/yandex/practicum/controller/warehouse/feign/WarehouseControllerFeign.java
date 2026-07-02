package ru.yandex.practicum.controller.warehouse.feign;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.controller.warehouse.WarehouseController;
import ru.yandex.practicum.fallback.warehouse.WarehouseControllerFallback;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse", fallback = WarehouseControllerFallback.class)
public interface WarehouseControllerFeign extends WarehouseController {
}
