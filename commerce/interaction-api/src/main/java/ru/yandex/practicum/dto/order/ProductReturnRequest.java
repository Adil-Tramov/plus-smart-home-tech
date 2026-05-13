package ru.yandex.practicum.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Map;
import java.util.UUID;

public record ProductReturnRequest(
        UUID orderId,

        @NotNull
        @Valid
        Map<@NotNull UUID, @Positive Long> products
) {
}
