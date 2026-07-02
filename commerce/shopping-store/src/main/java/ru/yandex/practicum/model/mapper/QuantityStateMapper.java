package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.QuantityStateDto;
import ru.yandex.practicum.model.QuantityState;

@Component
public class QuantityStateMapper {

    public QuantityState toEntity(QuantityStateDto dto) {
        if (dto == null) {
            return null;
        }

        return switch (dto) {
            case ENDED -> QuantityState.ENDED;
            case ENOUGH -> QuantityState.ENOUGH;
            case FEW -> QuantityState.FEW;
            case MANY -> QuantityState.MANY;
            default -> throw new IllegalArgumentException("Unexpected enum constant: " + dto);
        };
    }

    public QuantityStateDto toDto(QuantityState product) {
        if (product == null) {
            return null;
        }

        return switch (product) {
            case ENDED -> QuantityStateDto.ENDED;
            case FEW -> QuantityStateDto.FEW;
            case ENOUGH -> QuantityStateDto.ENOUGH;
            case MANY -> QuantityStateDto.MANY;
            default -> throw new IllegalArgumentException("Unexpected enum constant: " + product);
        };
    }
}
