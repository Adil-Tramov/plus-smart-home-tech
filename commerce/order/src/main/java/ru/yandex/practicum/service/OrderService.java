package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;

import java.util.UUID;

public interface OrderService {

    Page<OrderDto> getAllUserOrders(String username, Pageable pageable);

    OrderDto createNewOrder(CreateNewOrderRequest newOrder,
                            BookedProductsDto bookedProducts,
                            UUID orderId,
                            UUID deliveryId);

    OrderDto updateOrder(OrderDto orderDto);

    OrderDto payForOrder(UUID orderId);

    OrderDto failedPayForOrder(UUID orderId);

    OrderDto orderDelivered(UUID orderId);

    OrderDto failedOrderDelivered(UUID orderId);

    OrderDto completeOrder(UUID orderId);

    OrderDto assemblyOrder(UUID orderId);

    OrderDto failedAssemblyOrder(UUID orderId);

    OrderDto getOrderById(UUID orderId);
}