package ru.yandex.practicum.service;


import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.model.Delivery;

import java.math.BigDecimal;
import java.util.UUID;

public interface DeliveryService {

    Delivery createNewDelivery(Delivery dto);

    Delivery successfulDelivery(UUID id);

    Delivery pickedDelivery(UUID id);

    Delivery failedDelivery(UUID id);

    BigDecimal calculateDeliveryCost(OrderDto orderDto);

    Delivery findByDeliveryId(UUID id);
}
