package ru.yande.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yande.practicum.model.Order;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.warehouse.AssemblyProductsForOrderRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class OrderMapper {
    public OrderDto toOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        return OrderDto.builder()
                .orderId(order.getId())
                .shoppingCartId(order.getShoppingCartId())
                .products(order.getProducts())
                .paymentId(order.getPaymentId())
                .deliveryId(order.getDeliveryId())
                .state(order.getState())
                .deliveryWeight(order.getDeliveryWeight())
                .deliveryVolume(order.getDeliveryVolume())
                .fragile(order.getFragile())
                .totalPrice(order.getTotalPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .productPrice(order.getProductPrice())
                .build();
    }

    public List<OrderDto> toOrderDto(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return Collections.emptyList();
        }
        return orders.stream().map(this::toOrderDto).toList();
    }

    public Order toOrder(String username, CreateNewOrderRequest request) {
        Order order = new Order();
        order.setShoppingCartId(request.shoppingCart().shoppingCartId());
        order.setUsername(username);
        order.setProducts(request.shoppingCart().products());
        return order;
    }

    public AssemblyProductsForOrderRequest toAssemblyRequest(UUID orderId, Map<UUID, Long> products) {
        return AssemblyProductsForOrderRequest.builder()
                .orderId(orderId)
                .products(products)
                .build();
    }
}
