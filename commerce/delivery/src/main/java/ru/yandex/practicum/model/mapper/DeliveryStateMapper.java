package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.DeliveryStateDto;
import ru.yandex.practicum.model.DeliveryState;

@Component
public class DeliveryStateMapper {

    public DeliveryState toEntity(DeliveryStateDto deliveryStateDto) {
        if (deliveryStateDto == null) {
            return null;
        }

        DeliveryState deliveryState;

        switch (deliveryStateDto) {
            case CREATED:
                deliveryState = DeliveryState.CREATED;
                break;
            case IN_PROGRESS:
                deliveryState = DeliveryState.IN_PROGRESS;
                break;
            case DELIVERED:
                deliveryState = DeliveryState.DELIVERED;
                break;
            case FAILED:
                deliveryState = DeliveryState.FAILED;
                break;
            case CANCELLED:
                deliveryState = DeliveryState.CANCELLED;
                break;
            default:
                throw new IllegalArgumentException("Unexpected enum constant: " + deliveryStateDto);
        }

        return deliveryState;
    }

    public DeliveryStateDto toDto(DeliveryState deliveryState) {
        if (deliveryState == null) {
            return null;
        }

        DeliveryStateDto deliveryStateDto;

        switch (deliveryState) {
            case CREATED:
                deliveryStateDto = DeliveryStateDto.CREATED;
                break;
            case IN_PROGRESS:
                deliveryStateDto = DeliveryStateDto.IN_PROGRESS;
                break;
            case DELIVERED:
                deliveryStateDto = DeliveryStateDto.DELIVERED;
                break;
            case FAILED:
                deliveryStateDto = DeliveryStateDto.FAILED;
                break;
            case CANCELLED:
                deliveryStateDto = DeliveryStateDto.CANCELLED;
                break;
            default:
                throw new IllegalArgumentException("Unexpected enum constant: " + deliveryState);
        }

        return deliveryStateDto;
    }
}
