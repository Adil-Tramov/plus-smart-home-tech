package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.PaymentOperations;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController implements PaymentOperations {
    private final PaymentService paymentService;

    @Override
    public PaymentDto createPayment(OrderDto orderDto) {
        log.info("POST /api/v1/payment - Формирование оплаты заказа с ID: {}", orderDto.orderId());
        PaymentDto payment = paymentService.createPayment(orderDto);
        log.info("Возвращаем созданную оплату: {}", payment);
        return payment;
    }

    @Override
    public BigDecimal calculateTotalCost(OrderDto orderDto) {
        log.info("POST /api/v1/payment/totalCost - Расчет полной стоимости заказа с ID: {}", orderDto.orderId());
        BigDecimal totalCost = paymentService.calculateTotalCost(orderDto);
        log.info("Возвращаем полную стоимость: {}", totalCost);
        return totalCost;
    }

    @Override
    public void confirmPayment(UUID paymentId) {
        log.info("POST /api/v1/payment/refund - Эмуляция успешной оплаты с ID: {}", paymentId);
        paymentService.confirmPayment(paymentId);
        log.info("Эмулировали успешную оплату с ID: {}", paymentId);
    }

    @Override
    public BigDecimal calculateProductsCost(OrderDto orderDto) {
        log.info("POST /api/v1/payment/productCost - Расчет стоимости товаров в заказе с ID: {}", orderDto.orderId());
        BigDecimal productsCost = paymentService.calculateProductsCost(orderDto);
        log.info("Возвращаем стоимость товаров: {}", productsCost);
        return productsCost;
    }

    @Override
    public void failPayment(UUID paymentId) {
        log.info("POST /api/v1/payment/failed - Эмуляция отказа в оплате с ID: {}", paymentId);
        paymentService.failPayment(paymentId);
        log.info("Эмулировали отказ в оплате с ID: {}", paymentId);
    }
}
