package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.model.Payment;

@Component
public class PaymentMapper {
    public PaymentDto toDto(Payment payment) {
        if (payment == null) {
            return null;
        }
        return PaymentDto.builder()
                .paymentId(payment.getId())
                .totalPayment(payment.getTotalPayment())
                .deliveryTotal(payment.getDeliveryPrice())
                .feeTotal(payment.getFeePrice())
                .build();
    }
}
