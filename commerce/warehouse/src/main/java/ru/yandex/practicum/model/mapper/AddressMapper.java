package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.model.Address;

@Component
public class AddressMapper {

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
