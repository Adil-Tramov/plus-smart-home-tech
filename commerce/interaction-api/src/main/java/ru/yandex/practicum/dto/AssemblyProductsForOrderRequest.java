package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class AssemblyProductsForOrderRequest {
    private Map<@NotNull UUID, @Positive Integer> products;
    @NotNull(message = "ID заказа обязательно")
    private UUID orderId;
}
