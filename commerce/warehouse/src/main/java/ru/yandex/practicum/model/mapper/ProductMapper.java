package ru.yandex.practicum.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.DimensionDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequest;
import ru.yandex.practicum.model.Dimension;
import ru.yandex.practicum.model.Product;

@Component
public class ProductMapper {

    public Product toEntity(NewProductInWarehouseRequest dto) {
        if (dto == null) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.productId(dto.getProductId());
        product.fragile(dto.isFragile());
        product.dimension(dimensionDtoToDimension(dto.getDimension()));
        product.weight(dto.getWeight());

        product.quantity(0);

        return product.build();
    }

    protected Dimension dimensionDtoToDimension(DimensionDto dimensionDto) {
        if (dimensionDto == null) {
            return null;
        }

        Dimension.DimensionBuilder dimension = Dimension.builder();

        dimension.width(dimensionDto.getWidth());
        dimension.height(dimensionDto.getHeight());
        dimension.depth(dimensionDto.getDepth());

        return dimension.build();
    }
}
