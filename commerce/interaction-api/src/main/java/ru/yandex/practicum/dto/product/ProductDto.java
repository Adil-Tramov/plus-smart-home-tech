package ru.yandex.practicum.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Товар, продаваемый в интернет-магазине
 *
 * @param productId       идентификатор товара в БД
 * @param productName     наименование товара
 * @param description     описание товара
 * @param imageSrc        ссылка на картинку во внешнем хранилище или SVG
 * @param quantityState   статус, перечисляющий состояние остатка как свойства товара
 * @param productState    статус товара
 * @param productCategory категория товара
 * @param price           цена товара
 */
@Builder
public record ProductDto(
        UUID productId,

        @NotBlank
        String productName,

        @NotBlank
        String description,

        String imageSrc,

        @NotNull
        QuantityState quantityState,

        @NotNull
        ProductState productState,

        ProductCategory productCategory,

        @NotNull
        @DecimalMin(value = "1.0")
        BigDecimal price
) {
}
