package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

/**
 * Запрос на увеличение единиц товара по его идентификатору
 *
 * @param productId Идентификатор товара в БД
 * @param quantity Количество единиц товара для добавления на склад
 */

@Builder
public record AddProductToWarehouseRequest(
        @NotNull
        UUID productId,

        @NotNull
        @Min(1)
        Long quantity
) {
}
