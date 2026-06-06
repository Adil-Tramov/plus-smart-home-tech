package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.model.BookedProducts;

@Component
public class BookedProductsMapper {

    public BookedProducts toEntity(BookedProductsDto dto) {
        if (dto == null) {
            return null;
        }

        BookedProducts.BookedProductsBuilder bookedProducts = BookedProducts.builder();

        bookedProducts.deliveryWeight(dto.getDeliveryWeight());
        bookedProducts.deliveryVolume(dto.getDeliveryVolume());
        bookedProducts.fragile(dto.isFragile());

        return bookedProducts.build();
    }

    public BookedProductsDto toDto(BookedProducts product) {
        if (product == null) {
            return null;
        }

        BookedProductsDto.BookedProductsDtoBuilder bookedProductsDto = BookedProductsDto.builder();

        bookedProductsDto.deliveryWeight(product.getDeliveryWeight());
        bookedProductsDto.deliveryVolume(product.getDeliveryVolume());
        bookedProductsDto.fragile(product.isFragile());

        return bookedProductsDto.build();
    }
}
