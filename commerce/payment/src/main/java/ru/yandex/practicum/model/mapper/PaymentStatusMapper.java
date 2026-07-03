package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.PaymentStatusDto;
import ru.yandex.practicum.model.PaymentStatus;

@Component
public class PaymentStatusMapper {

    public PaymentStatus toEntity(PaymentStatusDto dto) {
        if (dto == null) {
            return null;
        }

        return switch (dto) {
            case PENDING -> PaymentStatus.PENDING;
            case SUCCESS -> PaymentStatus.SUCCESS;
            case FAILED -> PaymentStatus.FAILED;
            default -> throw new IllegalArgumentException("Unexpected enum constant: " + dto);
        };
    }

    public PaymentStatusDto toDto(PaymentStatus paymentStatus) {
        if (paymentStatus == null) {
            return null;
        }

        return switch (paymentStatus) {
            case PENDING -> PaymentStatusDto.PENDING;
            case SUCCESS -> PaymentStatusDto.SUCCESS;
            case FAILED -> PaymentStatusDto.FAILED;
            default -> throw new IllegalArgumentException("Unexpected enum constant: " + paymentStatus);
        };
    }
}
