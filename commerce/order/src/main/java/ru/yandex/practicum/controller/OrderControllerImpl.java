package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.controller.delivery.feign.DeliveryControllerFeign;
import ru.yandex.practicum.controller.order.OrderController;
import ru.yandex.practicum.controller.payment.feign.PaymentControllerFeign;
import ru.yandex.practicum.controller.warehouse.feign.WarehouseControllerFeign;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.model.State;
import ru.yandex.practicum.model.mapper.OrderMapper;
import ru.yandex.practicum.service.OrderService;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Validated
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final PaymentControllerFeign paymentControllerFeign;
    private final WarehouseControllerFeign warehouseControllerFeign;
    private final DeliveryControllerFeign deliveryControllerFeign;

    @Loggable
    @GetMapping
    public Page<OrderDto> getAllUserOrders(@RequestParam String username,
                                           @PageableDefault(size = 20, sort = "state",
                                                   direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Order> orders = orderService.getAllUserOrders(username, pageable);
        return orders.map(orderMapper::toDto);
    }

    @Loggable
    @PutMapping
    public OrderDto createNewOrder(@RequestBody CreateNewOrderRequest newOrder) {
        BookedProductsDto bookedProductsDto =
                warehouseControllerFeign.checkAvailableAllProductInShoppingCart(newOrder.getShoppingCartDto());
        AddressDto warehousAddress = warehouseControllerFeign.getWarehouseAddress();

        DeliveryDto deliveryDto = DeliveryDto.builder()
                .toAddress(newOrder.getAddressDto())
                .fromAddress(warehousAddress)
                .orderId(UUID.randomUUID())
                .build();
        deliveryDto = deliveryControllerFeign.createNewDelivery(deliveryDto);

        Order order = orderService.createNewOrder(newOrder,
                bookedProductsDto,
                deliveryDto.getOrderId(),
                deliveryDto.getDeliveryId());

        OrderDto orderDto = orderMapper.toDto(order);
        paymentControllerFeign.createPayment(orderDto);

        return orderDto;
    }

    @Loggable
    @PostMapping("/return")
    public OrderDto returnProducts(@RequestBody ProductReturnRequest request) {
        Order order = orderService.getOrderById(request.getOrderId());
        warehouseControllerFeign.returnProductsInWarehous(request.getProducts());
        order.setState(State.CANCELED);
        order = orderService.updateOrder(order);
        return orderMapper.toDto(order);
    }

    @Loggable
    @PostMapping("/calculate/total")
    public OrderDto calculateTotalCost(@RequestBody UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        OrderDto orderDto = orderMapper.toDto(order);

        BigDecimal totalPrice = paymentControllerFeign.calculateTotalCost(orderDto);
        order.setTotalPrice(totalPrice);
        order = orderService.updateOrder(order);

        return orderMapper.toDto(order);
    }

    @Loggable
    @PostMapping("/calculate/delivery")
    public OrderDto calculateDeliveryCost(@RequestBody UUID orderId) {
        Order order = orderService.getOrderById(orderId);

        OrderDto orderDto = orderMapper.toDto(order);
        BigDecimal deliveryCost = deliveryControllerFeign.deliveryCostCalculation(orderDto);

        order.setDeliveryPrice(deliveryCost);
        order = orderService.updateOrder(order);
        return orderMapper.toDto(order);
    }

    @Loggable
    @PostMapping("/payment")
    public OrderDto payForOrder(@RequestBody UUID orderId) {
        Order order = orderService.payForOrder(orderId);
        return orderMapper.toDto(order);
    }

    @Loggable
    @PostMapping("/payment/failed")
    public OrderDto failedPayForOrder(@RequestBody UUID orderId) {
        Order order = orderService.failedPayForOrder(orderId);
        return orderMapper.toDto(order);
    }

    @Loggable
    @PostMapping("/delivery")
    public OrderDto orderDelivered(@RequestBody UUID orderId) {
        Order order = orderService.orderDelivered(orderId);
        return orderMapper.toDto(order);
    }

    @Loggable
    @PostMapping("/delivery/failed")
    public OrderDto failedOrderDelivered(@RequestBody UUID orderId) {
        Order order = orderService.failedOrderDelivered(orderId);
        return orderMapper.toDto(order);
    }

    @Loggable
    @PostMapping("/completed")
    public OrderDto completeOrder(@RequestBody UUID orderId) {
        Order order = orderService.completeOrder(orderId);
        return orderMapper.toDto(order);
    }

    @Loggable
    @PostMapping("/assembly")
    public OrderDto assemblyOrder(@RequestBody UUID orderId) {
        Order order = orderService.assemblyOrder(orderId);
        return orderMapper.toDto(order);
    }

    @Loggable
    @PostMapping("/assembly/failed")
    public OrderDto failedAssemblyOrder(@RequestBody UUID orderId) {
        Order order = orderService.failedAssemblyOrder(orderId);
        return orderMapper.toDto(order);
    }
}
