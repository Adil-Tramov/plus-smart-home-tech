package ru.yandex.practicum.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.model.Product;

@Component
public class ProductMapper {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductStateMapper productStateMapper;
    @Autowired
    private QuantityStateMapper quantityStateMapper;

    public Product toEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.quantityState(quantityStateMapper.toEntity(dto.getQuantityState()));
        product.productState(productStateMapper.toEntity(dto.getProductState()));
        product.productCategory(productCategoryMapper.toEntity(dto.getProductCategory()));
        product.description(dto.getDescription());
        product.imageSrc(dto.getImageSrc());
        product.price(dto.getPrice());
        product.productId(dto.getProductId());
        product.productName(dto.getProductName());

        return product.build();
    }

    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDto.ProductDtoBuilder productDto = ProductDto.builder();

        productDto.quantityState(quantityStateMapper.toDto(product.getQuantityState()));
        productDto.productState(productStateMapper.toDto(product.getProductState()));
        productDto.productCategory(productCategoryMapper.toDto(product.getProductCategory()));
        productDto.productId(product.getProductId());
        productDto.productName(product.getProductName());
        productDto.description(product.getDescription());
        productDto.imageSrc(product.getImageSrc());
        productDto.price(product.getPrice());

        return productDto.build();
    }
}
