package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.cart.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.DimensionsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.model.Dimensions;
import ru.yandex.practicum.model.Product;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class ProductMapper {
    public Product toProduct(NewProductInWarehouseRequest dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(dto.productId());
        product.setFragile(dto.fragile());
        product.setWeight(dto.weight());
        product.setDimensions(toDimensions(dto.dimensions()));
        return product;
    }

    public Dimensions toDimensions(DimensionsDto dto) {
        if (dto == null) {
            return null;
        }
        Dimensions dimensions = new Dimensions();
        dimensions.setWidth(dto.width());
        dimensions.setHeight(dto.height());
        dimensions.setDepth(dto.depth());
        return dimensions;
    }

    public BookedProductsDto toDto(Map<UUID, Long> requiredQuantity, Set<Product> products) {
        double deliveryWeight = 0.0;
        double deliveryVolume = 0.0;
        boolean fragile = false;
        for (Product product : products) {
            long quantity = requiredQuantity.get(product.getId());
            deliveryWeight += product.getWeight() * quantity;
            Dimensions dimensions = product.getDimensions();
            deliveryVolume += (dimensions.getWidth() * dimensions.getHeight() * dimensions.getDepth()) * quantity;
            fragile = fragile || product.getFragile();
        }
        return BookedProductsDto.builder()
                .deliveryWeight(deliveryWeight)
                .deliveryVolume(deliveryVolume)
                .fragile(fragile)
                .build();
    }
}
