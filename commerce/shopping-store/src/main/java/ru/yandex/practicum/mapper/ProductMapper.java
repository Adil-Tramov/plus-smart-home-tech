package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public Product toProduct(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(dto.productId());
        product.setName(dto.productName());
        product.setDescription(dto.description());
        product.setImageSrc(dto.imageSrc());
        product.setQuantityState(dto.quantityState());
        product.setCategory(dto.productCategory());
        product.setProductState(dto.productState());
        product.setPrice(dto.price());
        return product;
    }

    public ProductDto toProductDto(Product product) {
        if (product == null) {
            return null;
        }
        return ProductDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .imageSrc(product.getImageSrc())
                .quantityState(product.getQuantityState())
                .productCategory(product.getCategory())
                .productState(product.getProductState())
                .price(product.getPrice())
                .build();
    }

    public List<ProductDto> toProductDto(List<Product> products) {
        if (products == null) {
            return null;
        }
        return products.stream().map(this::toProductDto).collect(Collectors.toList());
    }
}
