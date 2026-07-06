package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface DeliveryService {

    DeliveryDto createNewDelivery(DeliveryDto deliveryDto);

    DeliveryDto successfulDelivery(UUID id);

    DeliveryDto pickedDelivery(UUID id);

    DeliveryDto failedDelivery(UUID id);

    DeliveryDto findByDeliveryId(UUID id);

    BigDecimal calculateDeliveryCost(OrderDto order);
}