package ru.yandex.practicum.controller.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequest;

import java.util.UUID;

public interface OrderController {

    @GetMapping
    Page<OrderDto> getAllUserOrders(@RequestParam String username,
                                    @PageableDefault(size = 20, sort = "username",
                                            direction = Sort.Direction.ASC) Pageable pageable);

    @PutMapping
    OrderDto createNewOrder(@RequestBody CreateNewOrderRequest newOrder);

    @PostMapping("/return")
    OrderDto returnProducts(@RequestBody ProductReturnRequest request);

    @PostMapping("/payment")
    OrderDto payForOrder(@RequestBody UUID orderId);

    @PostMapping("/payment/failed")
    OrderDto failedPayForOrder(@RequestBody UUID orderId);

    @PostMapping("/delivery")
    OrderDto orderDelivered(@RequestBody UUID orderId);

    @PostMapping("/delivery/failed")
    OrderDto failedOrderDelivered(@RequestBody UUID orderId);

    @PostMapping("/completed")
    OrderDto completeOrder(@RequestBody UUID orderId);

    @PostMapping("/calculate/total")
    OrderDto calculateTotalCost(@RequestBody UUID orderId);

    @PostMapping("/calculate/delivery")
    OrderDto calculateDeliveryCost(@RequestBody UUID orderId);

    @PostMapping("/assembly")
    OrderDto assemblyOrder(@RequestBody UUID orderId);

    @PostMapping("/assembly/failed")
    OrderDto failedAssemblyOrder(@RequestBody UUID orderId);
}
