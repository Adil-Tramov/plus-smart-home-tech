package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

/**
 * Запрос на добавление нового товара на склад
 *
 * @param productId Идентификатор товара в БД
 * @param fragile Признак хрупкости
 * @param dimensions Размеры товара
 * @param weight Вес товара
 */

@Builder
public record NewProductInWarehouseRequest(
        @NotNull
        UUID productId,

        Boolean fragile,

        @NotNull
        DimensionsDto dimensions,

        @NotNull
        @DecimalMin("1.0")
        Double weight
) {
}
