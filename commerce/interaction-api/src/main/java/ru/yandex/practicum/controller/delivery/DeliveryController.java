package ru.yandex.practicum.controller.delivery;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface DeliveryController {

    @PutMapping
    DeliveryDto createNewDelivery(@RequestBody DeliveryDto deliveryDto);

    @PostMapping("/successful")
    DeliveryDto successfulDelivery(@RequestBody UUID deliveryId);

    @PostMapping("/picked")
    DeliveryDto pickedDelivery(@RequestBody UUID deliveryId);

    @PostMapping("/failed")
    DeliveryDto failedDelivery(@RequestBody UUID deliveryId);

    @PostMapping("/cost")
    BigDecimal deliveryCostCalculation(@RequestBody OrderDto order);
}
