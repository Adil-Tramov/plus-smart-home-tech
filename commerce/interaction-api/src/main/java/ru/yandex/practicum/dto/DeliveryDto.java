package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {
    @NotNull(message = "ID продукта обязателен")
    private UUID deliveryId;
    private AddressDto fromAddress;
    private AddressDto toAddress;
    @NotNull(message = "ID заказа обязателен")
    private UUID orderId;
    private DeliveryStateDto deliveryState;
}
