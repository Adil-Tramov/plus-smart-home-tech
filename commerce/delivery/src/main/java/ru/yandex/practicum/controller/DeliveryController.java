package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.DeliveryOperations;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.service.DeliveryService;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController implements DeliveryOperations {
    private final DeliveryService deliveryService;

    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        log.info("PUT /api/v1/delivery - Создание новой доставки для заказа с ID: {}", deliveryDto.orderId());
        DeliveryDto delivery = deliveryService.createDelivery(deliveryDto);
        log.info("Возвращаем созданную доставку: {}", delivery);
        return delivery;
    }

    @Override
    public void successDelivery(UUID orderId) {
        log.info("POST /api/v1/delivery/successful - Эмуляция успешной доставки заказа с ID: {}", orderId);
        deliveryService.successDelivery(orderId);
        log.info("Эмулировали успешную доставку заказа с ID: {}", orderId);
    }

    @Override
    public void pickDelivery(UUID orderId) {
        log.info("POST /api/v1/delivery/picked - Эмуляция получения доставки заказа с ID: {}", orderId);
        deliveryService.pickDelivery(orderId);
        log.info("Эмулировали получение доставки заказа с ID: {}", orderId);
    }

    @Override
    public void failedDelivery(UUID orderId) {
        log.info("POST /api/v1/delivery/failed - Эмуляция неудачного вручения доставки заказа с ID: {}", orderId);
        deliveryService.failedDelivery(orderId);
        log.info("Эмулировали неудачное вручение доставки заказа с ID: {}", orderId);
    }

    @Override
    public BigDecimal calculateCost(OrderDto orderDto) {
        log.info("POST /api/v1/delivery/cost - Расчет полной стоимости доставки заказа с ID: {}", orderDto.orderId());
        BigDecimal deliveryCost = deliveryService.calculateCost(orderDto);
        log.info("Возвращаем стоимость доставки: {}", deliveryCost);
        return deliveryCost;
    }
}
