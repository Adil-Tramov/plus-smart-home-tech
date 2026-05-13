package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.warehouse.AddressDto;

@Component
public class AddressMapper {
    public AddressDto toDto(String address) {
        return AddressDto.builder()
                .country(address)
                .city(address)
                .street(address)
                .house(address)
                .flat(address)
                .build();
    }
}