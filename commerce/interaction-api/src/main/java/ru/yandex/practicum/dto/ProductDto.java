package ru.yandex.practicum.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotNull(message = "ID продукта обязателен")
    private UUID productId;
    @NotNull(message = "Имя продукта не может быть пустым")
    private String productName;
    @NotNull(message = "Описание продукта не может быть пустым")
    private String description;
    private String imageSrc;
    @NotNull(message = "Количество продукта не может быть пустым")
    private QuantityStateDto quantityState;
    @NotNull(message = "Статус продукта не может быть пустым")
    private ProductStateDto productState;
    private ProductCategoryDto productCategory;
    @DecimalMin(value = "1.0", message = "Цена продукта должна быть больше или равна 1.0")
    private double price;
}
