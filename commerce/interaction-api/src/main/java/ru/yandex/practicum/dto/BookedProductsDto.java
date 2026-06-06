package ru.yandex.practicum.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookedProductsDto {
    @DecimalMin(value = "1.0", message = "Общий вес доставки должен быть больше или равен 1.0")
    private double deliveryWeight;
    @DecimalMin(value = "1.0", message = "Общий объём доставки должен быть больше или равен 1.0")
    private double deliveryVolume;
    private boolean fragile;
}
