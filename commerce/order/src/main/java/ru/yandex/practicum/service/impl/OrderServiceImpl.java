package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.model.State;
import ru.yandex.practicum.repository.OrderRepository;
import ru.yandex.practicum.service.OrderService;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Loggable
    public Page<Order> getAllUserOrders(String username, Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Loggable
    @Transactional
    public Order createNewOrder(CreateNewOrderRequest newOrder,
                                BookedProductsDto bookedProducts,
                                UUID orderId,
                                UUID deliveryId) {
        Order order = Order.builder()
                .orderId(orderId)
                .shoppingCartId(newOrder.getShoppingCartDto().getShoppingCartId())
                .products(newOrder.getShoppingCartDto().getProducts())
                .paymentId(UUID.randomUUID())
                .deliveryId(deliveryId)
                .state(State.NEW)
                .deliveryWeight(bookedProducts.getDeliveryWeight())
                .deliveryVolume(bookedProducts.getDeliveryVolume())
                .fragile(bookedProducts.isFragile())
                .totalPrice(new BigDecimal("0"))
                .deliveryPrice(new BigDecimal("0"))
                .productPrice(new BigDecimal("0"))
                .build();
        return orderRepository.save(order);
    }

    @Loggable
    @Transactional
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Loggable
    @Transactional
    public Order payForOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.PAID);
        return orderRepository.save(order);
    }

    @Loggable
    @Transactional
    public Order failedPayForOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.PAYMENT_FAILED);
        return orderRepository.save(order);
    }

    @Loggable
    @Transactional
    public Order orderDelivered(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.DELIVERED);
        return orderRepository.save(order);
    }

    @Loggable
    @Transactional
    public Order failedOrderDelivered(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.DELIVERY_FAILED);
        return orderRepository.save(order);
    }

    @Loggable
    @Transactional
    public Order completeOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.COMPLETED);
        return orderRepository.save(order);
    }

    public Order assemblyOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.ASSEMBLED);
        return orderRepository.save(order);
    }

    public Order failedAssemblyOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.ASSEMBLY_FAILED);
        return orderRepository.save(order);
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
    }
}
