package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.ProductState;
import ru.yandex.practicum.dto.product.SetProductQuantityStateRequest;
import ru.yandex.practicum.exception.ProductAlreadyExistsException;
import ru.yandex.practicum.exception.ProductNotFoundException;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingStoreServiceImpl implements ShoppingStoreService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    @Override
    public ProductDto getProductById(UUID productId) {
        log.debug("Запрашиваем товар с ID: {}", productId);
        if (productId == null) {
            return null;
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        log.debug("Получили из DB товар {}", product);
        return productMapper.toProductDto(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDto> getProducts(ProductCategory category, Pageable pageable) {
        log.debug("Запрашиваем товары с категорией - {} и пагинацией - {}", category, pageable);
        List<Product> products = productRepository.findAllByCategory(category, pageable).getContent();
        log.debug("Получили из DB список товаров размером {}", products.size());
        return productMapper.toProductDto(products);
    }

    @Transactional
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        log.debug("Сохраняем новый товар в DB - {}", productDto);
        UUID id = productDto.productId();
        if (productRepository.existsById(id)) {
            throw new ProductAlreadyExistsException(id);
        }
        Product product = productMapper.toProduct(productDto);
        productRepository.save(product);
        log.debug("Сохранили товар в DB - {}", product);
        return productMapper.toProductDto(product);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        log.debug("Обновляем товар в DB - {}", productDto);
        UUID id = productDto.productId();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(productDto.productName());
        product.setDescription(productDto.description());
        product.setImageSrc(productDto.imageSrc());
        product.setQuantityState(productDto.quantityState());
        product.setCategory(productDto.productCategory());
        product.setProductState(productDto.productState());
        product.setPrice(productDto.price());
        log.debug("Обновили товар в DB - {}", product);
        return productMapper.toProductDto(product);
    }

    @Transactional
    @Override
    public boolean updateQuantityState(SetProductQuantityStateRequest request) {
        log.debug("Обновляем признак количества товара в DB - {}", request);
        UUID id = request.productId();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.setQuantityState(request.quantityState());
        log.debug("Обновили признак количества товара в DB - {}", product);
        return true;
    }

    @Transactional
    @Override
    public boolean removeProduct(UUID productId) {
        log.debug("Получили запрос на деактивацию товара с ID - {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        product.setProductState(ProductState.DEACTIVATE);
        log.debug("Деактивировали товар - {}", product);
        return true;
    }
}
