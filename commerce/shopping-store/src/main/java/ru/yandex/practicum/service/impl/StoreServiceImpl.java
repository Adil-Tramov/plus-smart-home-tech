package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.ProductCategoryDto;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.QuantityStateDto;
import ru.yandex.practicum.exception.ProductNotFoundException;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.model.ProductCategory;
import ru.yandex.practicum.model.ProductState;
import ru.yandex.practicum.model.QuantityState;
import ru.yandex.practicum.model.mapper.ProductCategoryMapper;
import ru.yandex.practicum.model.mapper.ProductMapper;
import ru.yandex.practicum.model.mapper.QuantityStateMapper;
import ru.yandex.practicum.repository.StoreRepository;
import ru.yandex.practicum.service.StoreService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final ProductMapper productMapper;
    private final ProductCategoryMapper productCategoryMapper;
    private final QuantityStateMapper quantityStateMapper;

    @Loggable
    @Transactional
    public ProductDto createNewProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = storeRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Loggable
    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        if (storeRepository.existsById(productDto.getProductId())) {
            Product product = productMapper.toEntity(productDto);
            Product updatedProduct = storeRepository.save(product);
            return productMapper.toDto(updatedProduct);
        } else {
            throw new ProductNotFoundException("Product with ID: " + productDto.getProductId() + " don't present");
        }
    }

    @Loggable
    @Transactional
    public ProductDto changeQuantityState(UUID productId, QuantityStateDto quantityStateDto) {
        Product product = storeRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Not found product with ID: " + productId));
        QuantityState quantityState = quantityStateMapper.toEntity(quantityStateDto);
        product.setQuantityState(quantityState);
        Product updatedProduct = storeRepository.save(product);
        return productMapper.toDto(updatedProduct);
    }

    @Loggable
    @Transactional
    public boolean deleteProduct(UUID productId) {
        Optional<Product> productOptional = storeRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setProductState(ProductState.DEACTIVATE);
            storeRepository.save(product);
            return true;
        } else {
            throw new ProductNotFoundException("Product with ID: " + productId + " don't present");
        }
    }

    @Loggable
    public ProductDto getProductInfo(UUID id) {
        Product product = storeRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Not found product with ID: " + id));
        return productMapper.toDto(product);
    }

    @Loggable
    public Page<ProductDto> getListOfProducts(ProductCategoryDto categoryDto, Pageable pageable) {
        ProductCategory category = productCategoryMapper.toEntity(categoryDto);
        Page<Product> products = storeRepository.findByProductCategory(category, pageable);
        return products.map(productMapper::toDto);
    }

    @Loggable
    public List<ProductDto> getAllProductsFromList(List<UUID> productsId) {
        List<Product> products = storeRepository.findAllById(productsId);
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }
}