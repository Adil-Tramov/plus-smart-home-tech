package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @NotNull(message = "ID продукта обязателен")
    private UUID orderId;
    private UUID shoppingCartId;
    @NotNull(message = "Список продуктов обязателен")
    @Size(min = 1, message = "Корзина не может быть пустой")
    Map<UUID, Integer> products;
    private UUID paymentId;
    private UUID deliveryId;
    private StateDto state;
    private double deliveryWeight;
    private double deliveryVolume;
    private boolean fragile;
    private BigDecimal totalPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal productPrice;
}
