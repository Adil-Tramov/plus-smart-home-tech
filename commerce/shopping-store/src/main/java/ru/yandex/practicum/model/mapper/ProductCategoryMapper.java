package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.ProductCategoryDto;
import ru.yandex.practicum.model.ProductCategory;

@Component
public class ProductCategoryMapper {

    public ProductCategory toEntity(ProductCategoryDto dto) {
        if (dto == null) {
            return null;
        }

        return switch (dto) {
            case CONTROL -> ProductCategory.CONTROL;
            case LIGHTING -> ProductCategory.LIGHTING;
            case SENSORS -> ProductCategory.SENSORS;
            default -> throw new IllegalArgumentException("Unexpected enum constant: " + dto);
        };
    }

    public ProductCategoryDto toDto(ProductCategory product) {
        if (product == null) {
            return null;
        }

        return switch (product) {
            case LIGHTING -> ProductCategoryDto.LIGHTING;
            case CONTROL -> ProductCategoryDto.CONTROL;
            case SENSORS -> ProductCategoryDto.SENSORS;
            default -> throw new IllegalArgumentException("Unexpected enum constant: " + product);
        };
    }
}
