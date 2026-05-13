package ru.yandex.practicum.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.api.PaymentOperations;
import ru.yandex.practicum.dto.order.OrderDto;

import java.math.BigDecimal;

@FeignClient(name = "payment")
public interface PaymentClient extends PaymentOperations {
    @Override
    @PostMapping("/api/v1/payment/totalCost")
    BigDecimal calculateTotalCost(@RequestBody @Valid OrderDto orderDto);
}
