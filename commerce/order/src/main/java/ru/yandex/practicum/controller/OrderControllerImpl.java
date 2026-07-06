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
    private final PaymentControllerFeign paymentControllerFeign;
    private final WarehouseControllerFeign warehouseControllerFeign;
    private final DeliveryControllerFeign deliveryControllerFeign;

    @Loggable
    @GetMapping
    public Page<OrderDto> getAllUserOrders(@RequestParam String username,
                                           @PageableDefault(size = 20, sort = "state",
                                                   direction = Sort.Direction.ASC) Pageable pageable) {
        return orderService.getAllUserOrders(username, pageable);
    }

    @Loggable
    @PutMapping
    public OrderDto createNewOrder(@RequestBody CreateNewOrderRequest newOrder) {
        BookedProductsDto bookedProductsDto =
                warehouseControllerFeign.checkAvailableAllProductInShoppingCart(newOrder.getShoppingCartDto());
        AddressDto warehouseAddress = warehouseControllerFeign.getWarehouseAddress();

        DeliveryDto deliveryDto = DeliveryDto.builder()
                .toAddress(newOrder.getAddressDto())
                .fromAddress(warehouseAddress)
                .orderId(UUID.randomUUID())
                .build();
        deliveryDto = deliveryControllerFeign.createNewDelivery(deliveryDto);

        OrderDto orderDto = orderService.createNewOrder(newOrder,
                bookedProductsDto,
                deliveryDto.getOrderId(),
                deliveryDto.getDeliveryId());

        paymentControllerFeign.createPayment(orderDto);

        return orderDto;
    }

    @Loggable
    @PostMapping("/return")
    public OrderDto returnProducts(@RequestBody ProductReturnRequest request) {
        OrderDto orderDto = orderService.getOrderById(request.getOrderId());
        warehouseControllerFeign.returnProductsInWarehouse(request.getProducts()); 
        orderDto.setState(StateDto.CANCELED);
        return orderService.updateOrder(orderDto);
    }

    @Loggable
    @PostMapping("/calculate/total")
    public OrderDto calculateTotalCost(@RequestBody UUID orderId) {
        OrderDto orderDto = orderService.getOrderById(orderId);

        BigDecimal totalPrice = paymentControllerFeign.calculateTotalCost(orderDto);
        orderDto.setTotalPrice(totalPrice);
        return orderService.updateOrder(orderDto);
    }

    @Loggable
    @PostMapping("/calculate/delivery")
    public OrderDto calculateDeliveryCost(@RequestBody UUID orderId) {
        OrderDto orderDto = orderService.getOrderById(orderId);
        BigDecimal deliveryCost = deliveryControllerFeign.deliveryCostCalculation(orderDto);
        orderDto.setDeliveryPrice(deliveryCost);
        return orderService.updateOrder(orderDto);
    }

    @Loggable
    @PostMapping("/payment")
    public OrderDto payForOrder(@RequestBody UUID orderId) {
        return orderService.payForOrder(orderId);
    }

    @Loggable
    @PostMapping("/payment/failed")
    public OrderDto failedPayForOrder(@RequestBody UUID orderId) {
        return orderService.failedPayForOrder(orderId);
    }

    @Loggable
    @PostMapping("/delivery")
    public OrderDto orderDelivered(@RequestBody UUID orderId) {
        return orderService.orderDelivered(orderId);
    }

    @Loggable
    @PostMapping("/delivery/failed")
    public OrderDto failedOrderDelivered(@RequestBody UUID orderId) {
        return orderService.failedOrderDelivered(orderId);
    }

    @Loggable
    @PostMapping("/completed")
    public OrderDto completeOrder(@RequestBody UUID orderId) {
        return orderService.completeOrder(orderId);
    }

    @Loggable
    @PostMapping("/assembly")
    public OrderDto assemblyOrder(@RequestBody UUID orderId) {
        return orderService.assemblyOrder(orderId);
    }

    @Loggable
    @PostMapping("/assembly/failed")
    public OrderDto failedAssemblyOrder(@RequestBody UUID orderId) {
        return orderService.failedAssemblyOrder(orderId);
    }
}