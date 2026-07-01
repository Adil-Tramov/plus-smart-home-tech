package ru.yandex.practicum.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.model.Order;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class OrderMapper {

    @Autowired
    private StateMapper stateMapper;

    public Order toEntity(OrderDto dto) {
        if (dto == null) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.state(stateMapper.toEntity(dto.getState()));
        order.orderId(dto.getOrderId());
        order.shoppingCartId(dto.getShoppingCartId());
        Map<UUID, Integer> map = dto.getProducts();
        if (map != null) {
            order.products(new LinkedHashMap<UUID, Integer>(map));
        }
        order.paymentId(dto.getPaymentId());
        order.deliveryId(dto.getDeliveryId());
        order.deliveryWeight(dto.getDeliveryWeight());
        order.deliveryVolume(dto.getDeliveryVolume());
        order.fragile(dto.isFragile());
        order.totalPrice(dto.getTotalPrice());
        order.deliveryPrice(dto.getDeliveryPrice());
        order.productPrice(dto.getProductPrice());

        return order.build();
    }

    public OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderDto.OrderDtoBuilder orderDto = OrderDto.builder();

        orderDto.state(stateMapper.toDto(order.getState()));
        orderDto.orderId(order.getOrderId());
        orderDto.shoppingCartId(order.getShoppingCartId());
        Map<UUID, Integer> map = order.getProducts();
        if (map != null) {
            orderDto.products(new LinkedHashMap<UUID, Integer>(map));
        }
        orderDto.paymentId(order.getPaymentId());
        orderDto.deliveryId(order.getDeliveryId());
        orderDto.deliveryWeight(order.getDeliveryWeight());
        orderDto.deliveryVolume(order.getDeliveryVolume());
        orderDto.fragile(order.isFragile());
        orderDto.totalPrice(order.getTotalPrice());
        orderDto.deliveryPrice(order.getDeliveryPrice());
        orderDto.productPrice(order.getProductPrice());

        return orderDto.build();
    }
}
