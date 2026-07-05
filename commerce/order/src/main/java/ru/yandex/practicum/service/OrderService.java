package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.model.Order;

import java.util.UUID;

public interface OrderService {

    Page<Order> getAllUserOrders(String username, Pageable pageable);

    Order createNewOrder(CreateNewOrderRequest newOrder,
                         BookedProductsDto bookedProducts,
                         UUID orderId,
                         UUID deliveryId);

    Order updateOrder(Order order);

    Order payForOrder(UUID orderId);

    Order failedPayForOrder(UUID orderId);

    Order orderDelivered(UUID orderId);

    Order failedOrderDelivered(UUID orderId);

    Order completeOrder(UUID orderId);

    Order assemblyOrder(UUID orderId);

    Order failedAssemblyOrder(UUID orderId);

    Order getOrderById(UUID orderId);
}
