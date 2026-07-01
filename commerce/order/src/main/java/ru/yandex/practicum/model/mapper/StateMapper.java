package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.StateDto;
import ru.yandex.practicum.model.State;

@Component
public class StateMapper {

    public State toEntity(StateDto dto) {
        if (dto == null) {
            return null;
        }

        return switch (dto) {
            case NEW -> State.NEW;
            case ON_PAYMENT -> State.ON_PAYMENT;
            case ON_DELIVERY -> State.ON_DELIVERY;
            case DONE -> State.DONE;
            case DELIVERED -> State.DELIVERED;
            case ASSEMBLED -> State.ASSEMBLED;
            case PAID -> State.PAID;
            case COMPLETED -> State.COMPLETED;
            case DELIVERY_FAILED -> State.DELIVERY_FAILED;
            case ASSEMBLY_FAILED -> State.ASSEMBLY_FAILED;
            case PAYMENT_FAILED -> State.PAYMENT_FAILED;
            case PRODUCT_RETURNED -> State.PRODUCT_RETURNED;
            case CANCELED -> State.CANCELED;
            default -> throw new IllegalArgumentException("Unexpected enum constant: " + dto);
        };
    }

    public StateDto toDto(State state) {
        if (state == null) {
            return null;
        }

        return switch (state) {
            case NEW -> StateDto.NEW;
            case ON_PAYMENT -> StateDto.ON_PAYMENT;
            case ON_DELIVERY -> StateDto.ON_DELIVERY;
            case DONE -> StateDto.DONE;
            case DELIVERED -> StateDto.DELIVERED;
            case ASSEMBLED -> StateDto.ASSEMBLED;
            case PAID -> StateDto.PAID;
            case COMPLETED -> StateDto.COMPLETED;
            case DELIVERY_FAILED -> StateDto.DELIVERY_FAILED;
            case ASSEMBLY_FAILED -> StateDto.ASSEMBLY_FAILED;
            case PAYMENT_FAILED -> StateDto.PAYMENT_FAILED;
            case PRODUCT_RETURNED -> StateDto.PRODUCT_RETURNED;
            case CANCELED -> StateDto.CANCELED;
            default -> throw new IllegalArgumentException("Unexpected enum constant: " + state);
        };
    }
}
