package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @NotBlank(message = "Не верно указанно поле: country")
    private String country;
    @NotBlank(message = "Не верно указанно поле: city")
    private String city;
    @NotBlank(message = "Не верно указанно поле: street")
    private String street;
    @NotBlank(message = "Не верно указанно поле: house")
    private String house;
    @NotBlank(message = "Не верно указанно поле: flat")
    private String flat;
}
