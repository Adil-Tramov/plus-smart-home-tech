package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.client.OrderClient;
import ru.yandex.practicum.client.WarehouseClient;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.exception.DeliveryForOrderAlreadyExistsException;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.repository.DeliveryRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private static final int PRICE_SCALE = 2;
    private static final BigDecimal BASE_COST = BigDecimal.valueOf(5.0);
    private static final double FRAGILE_COEFFICIENT = 0.2;
    private static final double WEIGHT_COEFFICIENT = 0.3;
    private static final double VOLUME_COEFFICIENT = 0.2;
    private static final double DESTINATION_COEFFICIENT = 0.2;

    private final DeliveryMapper deliveryMapper;
    private final DeliveryRepository deliveryRepository;
    private final OrderClient orderClient;
    private final WarehouseClient warehouseClient;

    @Transactional
    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        log.debug("Сохраняем новую доставку для заказа с ID: {} в DB", deliveryDto.orderId());
        if (deliveryRepository.existsByOrderId(deliveryDto.orderId())) {
            throw new DeliveryForOrderAlreadyExistsException(deliveryDto.orderId());
        }
        Delivery delivery = deliveryMapper.toDelivery(deliveryDto);
        Delivery savedDelivery = deliveryRepository.save(delivery);
        log.debug("Сохранили в DB новую доставку. ID доставки: {}. ID заказа: {}",
                savedDelivery.getId(), savedDelivery.getOrderId());
        return deliveryMapper.toDeliveryDto(savedDelivery);
    }

    @Transactional
    @Override
    public void successDelivery(UUID orderId) {
        log.debug("Устанавливаем признак успешной доставки для заказа с ID: {}", orderId);
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoDeliveryFoundException(orderId));
        delivery.setState(DeliveryState.DELIVERED);
        orderClient.orderDelivered(orderId);
        log.debug("Установили признак успешной доставки. ID доставки: {}. ID заказа: {}", delivery.getId(), orderId);
    }

    @Transactional
    @Override
    public void pickDelivery(UUID orderId) {
        log.debug("Принимаем заказ с ID: {} в доставку", orderId);
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoDeliveryFoundException(orderId));
        delivery.setState(DeliveryState.IN_PROGRESS);
        orderClient.orderAssemblyCompleted(orderId);
        ShippedToDeliveryRequest shippedToDeliveryRequest = createShippedToDeliveryRequest(orderId, delivery.getId());
        warehouseClient.shipToDelivery(shippedToDeliveryRequest);
        log.debug("Приняли заказ в доставку: {}", delivery);
    }

    @Override
    public void failedDelivery(UUID orderId) {
        log.debug("Устанавливаем признак неудачной доставки для заказа с ID: {}", orderId);
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoDeliveryFoundException(orderId));
        delivery.setState(DeliveryState.FAILED);
        orderClient.orderDeliveryFailed(orderId);
        log.debug("Установили признак неудачной доставки. ID доставки: {}. ID заказа: {}", delivery.getId(), orderId);
    }

    @Override
    public BigDecimal calculateCost(OrderDto orderDto) {
        log.debug("Рассчитываем стоимость доставки для заказа с ID: {}", orderDto.orderId());
        Delivery delivery = deliveryRepository.findByOrderId(orderDto.orderId())
                .orElseThrow(() -> new NoDeliveryFoundException(orderDto.orderId()));
        BigDecimal totalCost = BASE_COST;
        totalCost = totalCost.add(calculateWarehouseCost(totalCost, delivery.getFromAddress()));
        totalCost = totalCost.add(calculateFragileCost(totalCost, orderDto.fragile()));
        totalCost = totalCost.add(calculateWeightCost(orderDto.deliveryWeight()));
        totalCost = totalCost.add(calculateVolumeCost(orderDto.deliveryVolume()));
        totalCost = totalCost.add(calculateDestinationCost(totalCost, delivery.getFromAddress(), delivery.getToAddress()));
        log.debug("Рассчитали стоимость доставки: {} для заказа с ID: {}", totalCost, orderDto.orderId());
        return totalCost;
    }

    private ShippedToDeliveryRequest createShippedToDeliveryRequest(UUID orderId, UUID deliveryId) {
        log.debug("Создаем запрос на передачу в доставку с ID: {} заказа c ID: {}", orderId, deliveryId);
        return ShippedToDeliveryRequest.builder()
                .orderId(orderId)
                .deliveryId(deliveryId)
                .build();
    }

    private BigDecimal calculateWarehouseCost(BigDecimal baseCost, Address warehouseAddress) {
        final BigDecimal warehouse1Coefficient = BigDecimal.ONE;
        final BigDecimal warehouse2Coefficient = BigDecimal.TWO;
        BigDecimal warehouseCost;
        if ("ADDRESS_1".equals(warehouseAddress.getCountry())) {
            warehouseCost = baseCost.multiply(warehouse1Coefficient).setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
        } else {
            warehouseCost = baseCost.multiply(warehouse2Coefficient).setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
        }
        log.debug("Адрес склада: {}. Стоимость доставки из этого склада: {}", warehouseAddress.getCountry(), warehouseCost);
        return warehouseCost;
    }

    private BigDecimal calculateFragileCost(BigDecimal baseCost, boolean isFragile) {
        BigDecimal fragileCost;
        if (isFragile) {
            fragileCost = baseCost.multiply(BigDecimal.valueOf(FRAGILE_COEFFICIENT))
                    .setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
        } else {
            fragileCost = BigDecimal.ZERO;
        }
        log.debug("Признак хрупкости: {}. Доплата за хрупкость: {}", isFragile, fragileCost);
        return fragileCost;
    }

    private BigDecimal calculateWeightCost(double weight) {
        BigDecimal weightCost = BigDecimal.valueOf(WEIGHT_COEFFICIENT).multiply(BigDecimal.valueOf(weight))
                .setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
        log.debug("Вес заказа: {}. Доплата за вес: {}", weight, weightCost);
        return weightCost;
    }

    private BigDecimal calculateVolumeCost(double volume) {
        BigDecimal volumeCost = BigDecimal.valueOf(VOLUME_COEFFICIENT).multiply(BigDecimal.valueOf(volume))
                .setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
        log.debug("Объем заказа: {}. Доплата за объем: {}", volume, volumeCost);
        return volumeCost;
    }

    private BigDecimal calculateDestinationCost(BigDecimal baseCost,
                                                Address warehouseAddress,
                                                Address destinationAddress) {
        BigDecimal destinationCost;
        if (warehouseAddress.getStreet().equals(destinationAddress.getStreet())) {
            destinationCost = BigDecimal.ZERO;
        } else {
            destinationCost = BigDecimal.valueOf(DESTINATION_COEFFICIENT).multiply(baseCost)
                    .setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
        }
        log.debug("Улица склада: {}. Улица получателя: {}. Доплата за местоположение пункта назначения: {}",
                warehouseAddress.getStreet(), destinationAddress.getStreet(), destinationCost);
        return destinationCost;
    }
}
