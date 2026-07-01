package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.controller.delivery.DeliveryController;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.model.mapper.DeliveryMapper;
import ru.yandex.practicum.service.impl.DeliveryServiceImpl;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryControllerImpl implements DeliveryController {
    private final DeliveryServiceImpl deliveryService;
    private final DeliveryMapper deliveryMapper;

    @Loggable
    @PutMapping
    public DeliveryDto createNewDelivery(@RequestBody @Valid DeliveryDto deliveryDto) {
        Delivery delivery = deliveryMapper.toEntity(deliveryDto);
        delivery = deliveryService.createNewDelivery(delivery);
        return deliveryMapper.toDto(delivery);
    }

    @Loggable
    @PostMapping("/successful")
    public DeliveryDto successfulDelivery(@RequestBody UUID deliveryId) {
        Delivery delivery = deliveryService.successfulDelivery(deliveryId);
        return deliveryMapper.toDto(delivery);
    }

    @Loggable
    @PostMapping("/picked")
    public DeliveryDto pickedDelivery(@RequestBody UUID deliveryId) {
        Delivery delivery = deliveryService.pickedDelivery(deliveryId);
        return deliveryMapper.toDto(delivery);
    }

    @Loggable
    @PostMapping("/failed")
    public DeliveryDto failedDelivery(@RequestBody UUID deliveryId) {
        Delivery delivery = deliveryService.failedDelivery(deliveryId);
        return deliveryMapper.toDto(delivery);
    }

    @Loggable
    @PostMapping("/cost")
    public BigDecimal deliveryCostCalculation(@RequestBody OrderDto order) {
        return deliveryService.calculateDeliveryCost(order);
    }
}
