package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.model.DeliveryState;
import ru.yandex.practicum.repository.DeliveryRepository;
import ru.yandex.practicum.service.DeliveryService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Loggable
    @Transactional
    public Delivery createNewDelivery(Delivery delivery) {
        delivery.setDeliveryState(DeliveryState.CREATED);
        return deliveryRepository.save(delivery);
    }

    @Loggable
    @Transactional
    public Delivery successfulDelivery(UUID id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new NoDeliveryFoundException("Deliver with ID: " + id + "does not exist"));
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        return deliveryRepository.save(delivery);
    }

    @Loggable
    @Transactional
    public Delivery pickedDelivery(UUID id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new NoDeliveryFoundException("Deliver with ID: " + id + "does not exist"));
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        return deliveryRepository.save(delivery);
    }

    @Loggable
    @Transactional
    public Delivery failedDelivery(UUID id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new NoDeliveryFoundException("Deliver with ID: " + id + "does not exist"));
        delivery.setDeliveryState(DeliveryState.FAILED);
        return deliveryRepository.save(delivery);
    }

    @Loggable
    public BigDecimal calculateDeliveryCost(OrderDto order) {
        Delivery delivery = deliveryRepository.findById(order.getDeliveryId())
                .orElseThrow(() -> new NoDeliveryFoundException("Delivery with ID: " + order.getDeliveryId() + " does not exist"));

        log.info("Order id: {}", order.getOrderId());
        BigDecimal result = new BigDecimal("5.0");
        log.info("Base cost: {}", result);

        BigDecimal addressMult = delivery.getFromAddress().getCity().equalsIgnoreCase("ADDRESS_2")
                ? new BigDecimal("2") : BigDecimal.ONE;
        result = result.multiply(addressMult).add(new BigDecimal("5.0"));
        log.info("After address (×{}): {}", addressMult, result);

        BigDecimal fragileAdd = result.multiply(new BigDecimal(order.isFragile() ? "0.2" : "0"));
        result = result.add(fragileAdd);
        log.info("After fragile (+{}): {}", fragileAdd, result);

        BigDecimal weightCost = BigDecimal.valueOf(order.getDeliveryWeight()).multiply(new BigDecimal("0.3"));
        result = result.add(weightCost);
        log.info("After weight (+{}): {}", weightCost, result);

        BigDecimal volumeCost = BigDecimal.valueOf(order.getDeliveryVolume()).multiply(new BigDecimal("0.2"));
        result = result.add(volumeCost);
        log.info("After volume (+{}): {}", volumeCost, result);

        boolean sameStreet = delivery.getFromAddress().getStreet()
                .equalsIgnoreCase(delivery.getToAddress().getStreet());
        BigDecimal streetAdd = result.multiply(new BigDecimal(sameStreet ? "0" : "0.2"));
        result = result.add(streetAdd);
        log.info("After street (+{}): {}", streetAdd, result);

        return result.setScale(2, RoundingMode.HALF_UP);
    }

    @Loggable
    public Delivery findByDeliveryId(UUID id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new NoDeliveryFoundException("Deliver with ID: " + id + "does not exist"));
    }
}
