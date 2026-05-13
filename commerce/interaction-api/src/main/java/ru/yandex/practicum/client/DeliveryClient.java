package ru.yandex.practicum.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.api.DeliveryOperations;
import ru.yandex.practicum.dto.order.OrderDto;

import java.math.BigDecimal;

@FeignClient(name = "delivery")
public interface DeliveryClient extends DeliveryOperations {
    @Override
    @PostMapping("/api/v1/delivery/cost")
    BigDecimal calculateCost(@RequestBody @Valid OrderDto orderDto);
}
