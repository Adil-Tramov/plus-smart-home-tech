package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.model.Address;

@Component
public class AddressMapper {

    public Address toEntity(AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.country(addressDto.getCountry());
        address.city(addressDto.getCity());
        address.street(addressDto.getStreet());
        address.house(addressDto.getHouse());
        address.flat(addressDto.getFlat());

        return address.build();
    }

    public AddressDto toDto(Address address) {
        if (address == null) {
            return null;
        }

        AddressDto.AddressDtoBuilder addressDto = AddressDto.builder();

        addressDto.country(address.getCountry());
        addressDto.city(address.getCity());
        addressDto.street(address.getStreet());
        addressDto.house(address.getHouse());
        addressDto.flat(address.getFlat());

        return addressDto.build();
    }
}
