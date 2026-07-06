package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.StateDto;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.model.State;
import ru.yandex.practicum.model.mapper.OrderMapper;
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
    private final OrderMapper orderMapper;

    @Loggable
    public Page<OrderDto> getAllUserOrders(String username, Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(orderMapper::toDto);
    }

    @Loggable
    @Transactional
    public OrderDto createNewOrder(CreateNewOrderRequest newOrder,
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
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Loggable
    @Transactional
    public OrderDto updateOrder(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Loggable
    @Transactional
    public OrderDto payForOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.PAID);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Loggable
    @Transactional
    public OrderDto failedPayForOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.PAYMENT_FAILED);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Loggable
    @Transactional
    public OrderDto orderDelivered(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.DELIVERED);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Loggable
    @Transactional
    public OrderDto failedOrderDelivered(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.DELIVERY_FAILED);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Loggable
    @Transactional
    public OrderDto completeOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.COMPLETED);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Loggable
    @Transactional
    public OrderDto assemblyOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.ASSEMBLED);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Loggable
    @Transactional
    public OrderDto failedAssemblyOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        order.setState(State.ASSEMBLY_FAILED);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Loggable
    public OrderDto getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order with id: " + orderId + " not found"));
        return orderMapper.toDto(order);
    }
}