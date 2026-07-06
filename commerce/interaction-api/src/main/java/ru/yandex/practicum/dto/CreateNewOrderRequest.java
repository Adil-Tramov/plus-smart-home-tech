package ru.yandex.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewOrderRequest {
    private ShoppingCartDto shoppingCartDto;
    private AddressDto addressDto;
}
