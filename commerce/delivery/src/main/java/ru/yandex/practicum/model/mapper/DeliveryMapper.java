package ru.yandex.practicum.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.model.Delivery;


@Component
public class DeliveryMapper {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private DeliveryStateMapper deliveryStateMapper;

    public Delivery toEntity(DeliveryDto deliveryDto) {
        if (deliveryDto == null) {
            return null;
        }

        Delivery.DeliveryBuilder delivery = Delivery.builder();

        delivery.fromAddress(addressMapper.toEntity(deliveryDto.getFromAddress()));
        delivery.toAddress(addressMapper.toEntity(deliveryDto.getToAddress()));
        delivery.deliveryState(deliveryStateMapper.toEntity(deliveryDto.getDeliveryState()));
        delivery.deliveryId(deliveryDto.getDeliveryId());
        delivery.orderId(deliveryDto.getOrderId());

        return delivery.build();
    }

    public DeliveryDto toDto(Delivery delivery) {
        if (delivery == null) {
            return null;
        }

        DeliveryDto.DeliveryDtoBuilder deliveryDto = DeliveryDto.builder();

        deliveryDto.fromAddress(addressMapper.toDto(delivery.getFromAddress()));
        deliveryDto.toAddress(addressMapper.toDto(delivery.getToAddress()));
        deliveryDto.deliveryState(deliveryStateMapper.toDto(delivery.getDeliveryState()));
        deliveryDto.deliveryId(delivery.getDeliveryId());
        deliveryDto.orderId(delivery.getOrderId());

        return deliveryDto.build();
    }
}
