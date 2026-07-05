package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.model.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PaymentService {


    Payment createPayment(Payment payment);

    BigDecimal calculateTotalCost(BigDecimal deliveryPrice, BigDecimal productsPrice);

    void successPayment(UUID orderId);

    double calculateProductCost(List<ProductDto> products, Map<UUID, Integer> productsQuantity);

    void failedPayment(UUID orderId);
}
