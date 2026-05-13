package ru.yande.practicum.service;

import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDto> getOrders(String username);

    OrderDto addOrder(String username, CreateNewOrderRequest request);

    OrderDto returnOrder(ProductReturnRequest request);

    OrderDto orderPayment(UUID orderId);

    OrderDto orderPaymentFailed(UUID orderId);

    OrderDto orderDelivered(UUID orderId);

    OrderDto orderDeliveryFailed(UUID orderId);

    OrderDto orderCompleted(UUID orderId);

    OrderDto calculateTotalPrice(UUID orderId);

    OrderDto calculateDeliveryPrice(UUID orderId);

    OrderDto orderAssemblyCompleted(UUID orderId);

    OrderDto orderAssemblyFailed(UUID orderId);
}
