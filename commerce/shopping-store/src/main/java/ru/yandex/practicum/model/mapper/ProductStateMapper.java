package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.ProductStateDto;
import ru.yandex.practicum.model.ProductState;

@Component
public class ProductStateMapper {

    public ProductState toEntity(ProductStateDto dto) {
        if (dto == null) {
            return null;
        }

        return switch (dto) {
            case ACTIVE -> ProductState.ACTIVE;
            case DEACTIVATE -> ProductState.DEACTIVATE;
            default -> throw new IllegalArgumentException("Unexpected enum constant: " + dto);
        };
    }

    public ProductStateDto toDto(ProductState product) {
        if (product == null) {
            return null;
        }

        return switch (product) {
            case ACTIVE -> ProductStateDto.ACTIVE;
            case DEACTIVATE -> ProductStateDto.DEACTIVATE;
            default -> throw new IllegalArgumentException("Unexpected enum constant: " + product);
        };
    }
}
