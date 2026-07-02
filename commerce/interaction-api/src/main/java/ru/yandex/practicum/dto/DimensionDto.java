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
public class DimensionDto {
    @DecimalMin(value = "1.0", message = "Ширина должна быть больше или равна 1.0")
    private double width;
    @DecimalMin(value = "1.0", message = "Высота должна быть больше или равна 1.0")
    private double height;
    @DecimalMin(value = "1.0", message = "Глубина должна быть больше или равна 1.0")
    private double depth;
}
