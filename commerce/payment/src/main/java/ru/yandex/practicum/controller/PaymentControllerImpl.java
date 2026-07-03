package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.controller.payment.PaymentController;
import ru.yandex.practicum.controller.shopping.store.feign.StoreControllerFeign;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.model.PaymentStatus;
import ru.yandex.practicum.model.mapper.PaymentMapper;
import ru.yandex.practicum.service.PaymentService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Validated
public class PaymentControllerImpl implements PaymentController {
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;
    private final StoreControllerFeign storeControllerFeign;

    @Loggable
    @PostMapping
    public PaymentDto createPayment(@RequestBody OrderDto order) {
        Payment payment = Payment.builder()
                .paymentId(order.getPaymentId())
                .totalPayment(order.getTotalPrice())
                .deliveryTotal(order.getDeliveryPrice())
                .feeTotal(order.getTotalPrice().subtract(order.getDeliveryPrice()).subtract(order.getProductPrice()))
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        payment = paymentService.createPayment(payment);
        return paymentMapper.toDto(payment);
    }

    @Loggable
    @PostMapping("/totalCost")
    public BigDecimal calculateTotalCost(@RequestBody OrderDto order) {
        return paymentService.calculateTotalCost(order.getDeliveryPrice(), order.getProductPrice());
    }

    @Loggable
    @PostMapping("/productCost")
    public double calculateProductCost(@RequestBody OrderDto order) {
        List<UUID> orderList = order.getProducts().keySet().stream()
                .toList();
        List<ProductDto> products = storeControllerFeign.getAllProductsFromList(orderList);
        return paymentService.calculateProductCost(products, order.getProducts());
    }

    @Loggable
    @PostMapping("/refund")
    public void successPayment(@RequestBody UUID orderId) {
        paymentService.successPayment(orderId);
    }

    @Loggable
    @PostMapping("/failed")
    public void failedPayment(@RequestBody UUID orderId) {
        paymentService.failedPayment(orderId);
    }
}
