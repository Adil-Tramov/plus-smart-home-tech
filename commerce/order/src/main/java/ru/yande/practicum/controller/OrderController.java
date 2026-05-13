package ru.yande.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yande.practicum.service.OrderService;
import ru.yandex.practicum.api.OrderOperations;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController implements OrderOperations {
    private final OrderService orderService;

    @Override
    public List<OrderDto> getOrders(String username) {
        log.info("GET /api/v1/order - Получение заказов пользователя: {}", username);
        List<OrderDto> orders = orderService.getOrders(username);
        log.info("Возвращаем заказы с ID: {}", orders.stream()
                .map(order -> order.orderId().toString())
                .collect(Collectors.joining(", ")));
        return orders;
    }

    @Override
    public OrderDto addOrder(String username, CreateNewOrderRequest request) {
        log.info("PUT /api/v1/order - Создание заказа: {}", request);
        OrderDto order = orderService.addOrder(username, request);
        log.info("Возвращаем созданный заказ c ID: {}", order.orderId());
        return order;
    }

    @Override
    public OrderDto returnOrder(ProductReturnRequest request) {
        log.info("POST /api/v1/order/return - Заявка на возврат заказа с ID: {}", request.orderId());
        OrderDto order = orderService.returnOrder(request);
        log.info("Возвращаем заказ c ID: {}", order.orderId());
        return order;
    }

    @Override
    public OrderDto orderPayment(UUID orderId) {
        log.info("POST /api/v1/order/payment - Оплата заказа с ID: {}", orderId);
        OrderDto order = orderService.orderPayment(orderId);
        log.info("Возвращаем оплаченный заказ c ID: {}", order.orderId());
        return order;
    }

    @Override
    public OrderDto orderPaymentFailed(UUID orderId) {
        log.info("POST /api/v1/order/payment/failed - Оплата заказа с ID: {} произошла с ошибкой", orderId);
        OrderDto order = orderService.orderPaymentFailed(orderId);
        log.info("Возвращаем оплаченный с ошибкой заказ. ID: {}", order.orderId());
        return order;
    }

    @Override
    public OrderDto orderDelivered(UUID orderId) {
        log.info("POST /api/v1/order/delivery - Доставка заказа с ID: {}", orderId);
        OrderDto order = orderService.orderDelivered(orderId);
        log.info("Возвращаем доставленный заказ c ID: {}", order.orderId());
        return order;
    }

    @Override
    public OrderDto orderDeliveryFailed(UUID orderId) {
        log.info("POST /api/v1/order/delivery/failed - Доставка заказа с ID: {} произошла с ошибкой", orderId);
        OrderDto order = orderService.orderDeliveryFailed(orderId);
        log.info("Возвращаем заказ с ошибкой доставки, ID: {}", order.orderId());
        return order;
    }

    @Override
    public OrderDto orderCompleted(UUID orderId) {
        log.info("POST /api/v1/order/completed - Завершение заказа с ID: {}", orderId);
        OrderDto order = orderService.orderCompleted(orderId);
        log.info("Возвращаем завершенный заказ с ID: {}", order.orderId());
        return order;
    }

    @Override
    public OrderDto calculateTotalPrice(UUID orderId) {
        log.info("POST /api/v1/order/calculate/total - Расчёт стоимости заказа с ID: {}", orderId);
        OrderDto order = orderService.calculateTotalPrice(orderId);
        log.info("Возвращаем заказ с рассчитанной стоимостью, ID: {}", order.orderId());
        return order;
    }

    @Override
    public OrderDto calculateDeliveryPrice(UUID orderId) {
        log.info("POST /api/v1/order/calculate/delivery - Расчёт стоимости доставки заказа с ID: {}", orderId);
        OrderDto order = orderService.calculateDeliveryPrice(orderId);
        log.info("Возвращаем заказ с рассчитанной стоимостью доставки, ID: {}", order.orderId());
        return order;
    }

    @Override
    public OrderDto orderAssemblyCompleted(UUID orderId) {
        log.info("POST /api/v1/order/assembly - Сборка заказа с ID: {} завершена", orderId);
        OrderDto order = orderService.orderAssemblyCompleted(orderId);
        log.info("Возвращаем заказ после сборки, ID: {}", order.orderId());
        return order;
    }

    @Override
    public OrderDto orderAssemblyFailed(UUID orderId) {
        log.info("POST /api/v1/order/assembly/failed - Сборка заказа с ID: {} произошла с ошибкой", orderId);
        OrderDto order = orderService.orderAssemblyFailed(orderId);
        log.info("Возвращаем заказ после после ошибки сборки, ID: {}", order.orderId());
        return order;
    }
}
