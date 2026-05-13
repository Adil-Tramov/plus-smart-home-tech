package ru.yandex.practicum.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class PaymentNotFoundException extends ErrorResponse {
    public PaymentNotFoundException(UUID paymentId) {
        super(HttpStatus.NOT_FOUND, "Платеж с ID " + paymentId + " не найден.");
    }
}
