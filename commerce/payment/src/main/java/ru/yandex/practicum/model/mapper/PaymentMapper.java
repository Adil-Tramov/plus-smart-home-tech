package ru.yandex.practicum.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.model.Payment;

@Component
public class PaymentMapper {

    @Autowired
    private PaymentStatusMapper paymentStatusMapper;

    public Payment toEntity(PaymentDto dto) {
        if (dto == null) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.paymentStatus(paymentStatusMapper.toEntity(dto.getPaymentStatusDto()));
        payment.paymentId(dto.getPaymentId());
        payment.totalPayment(dto.getTotalPayment());
        payment.deliveryTotal(dto.getDeliveryTotal());
        payment.feeTotal(dto.getFeeTotal());

        return payment.build();
    }

    public PaymentDto toDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentDto.PaymentDtoBuilder paymentDto = PaymentDto.builder();

        paymentDto.paymentStatusDto(paymentStatusMapper.toDto(payment.getPaymentStatus()));
        paymentDto.paymentId(payment.getPaymentId());
        paymentDto.totalPayment(payment.getTotalPayment());
        paymentDto.deliveryTotal(payment.getDeliveryTotal());
        paymentDto.feeTotal(payment.getFeeTotal());

        return paymentDto.build();
    }
}
