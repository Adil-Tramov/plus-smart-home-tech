package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Delivery;

@Component
public class DeliveryMapper {
    public Delivery toDelivery(DeliveryDto dto) {
        if (dto == null) {
            return null;
        }

        Address from = toAddress(dto.fromAddress());
        Address to = toAddress(dto.toAddress());

        Delivery delivery = new Delivery();
        delivery.setFromAddress(from);
        delivery.setToAddress(to);
        delivery.setOrderId(dto.orderId());
        return delivery;
    }

    public DeliveryDto toDeliveryDto(Delivery delivery) {
        if (delivery == null) {
            return null;
        }

        AddressDto from = toAddressDto(delivery.getFromAddress());
        AddressDto to = toAddressDto(delivery.getToAddress());

        return DeliveryDto.builder()
                .deliveryId(delivery.getId())
                .fromAddress(from)
                .toAddress(to)
                .orderId(delivery.getOrderId())
                .deliveryState(delivery.getState())
                .build();
    }

    public Address toAddress(AddressDto dto) {
        if (dto == null) {
            return null;
        }

        Address address = new Address();
        address.setCountry(dto.country());
        address.setCity(dto.city());
        address.setStreet(dto.street());
        address.setHouse(dto.house());
        address.setFlat(dto.flat());
        return address;
    }

    public AddressDto toAddressDto(Address address) {
        if (address == null) {
            return null;
        }

        return AddressDto.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .flat(address.getFlat())
                .build();
    }
}
