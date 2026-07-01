package ru.yandex.practicum.controller.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentController {

    @PostMapping
    PaymentDto createPayment(@RequestBody OrderDto order);

    @PostMapping("/totalCost")
    BigDecimal calculateTotalCost(@RequestBody OrderDto order);

    @PostMapping("/refund")
    void successPayment(@RequestBody UUID orderId);

    @PostMapping("/productCost")
    double calculateProductCost(@RequestBody OrderDto order);

    @PostMapping("/failed")
    void failedPayment(@RequestBody UUID orderId);
}
