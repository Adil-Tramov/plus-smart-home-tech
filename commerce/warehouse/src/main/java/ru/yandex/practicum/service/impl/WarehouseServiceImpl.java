package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequest;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.BookedProducts;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.model.ShippedDelivery;
import ru.yandex.practicum.model.mapper.AddressMapper;
import ru.yandex.practicum.model.mapper.BookedProductsMapper;
import ru.yandex.practicum.model.mapper.ProductMapper;
import ru.yandex.practicum.repository.ShippedRepository;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.service.WarehouseService;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final ShippedRepository shippedRepository;
    private final ProductMapper productMapper;
    private final AddressMapper addressMapper;
    private final BookedProductsMapper bookedProductsMapper;

    private static final Random RANDOM = new Random();
    private static final Address[] ADDRESSES = new Address[]{
            Address.builder()
                    .country("ADDRESS_1")
                    .city("ADDRESS_1")
                    .street("ADDRESS_1")
                    .house("ADDRESS_1")
                    .flat("ADDRESS_1")
                    .build(),
            Address.builder()
                    .country("ADDRESS_2")
                    .city("ADDRESS_2")
                    .street("ADDRESS_2")
                    .house("ADDRESS_2")
                    .flat("ADDRESS_2")
                    .build()
    };

    @Loggable
    @Transactional
    public void saveProductInWarehouse(NewProductInWarehouseRequest productDto) {
        Product product = productMapper.toEntity(productDto);
        UUID id = product.getProductId();
        if (warehouseRepository.existsById(id)) {
            throw new SpecifiedProductAlreadyInWarehouseException("Product with ID: " + id + " already exists!");
        }
        warehouseRepository.save(product);
    }

    @Loggable
    public BookedProductsDto checkShoppingCart(Map<UUID, Integer> productsFromCart) {
        List<Product> productListFromWarehouse = checkAvailableAndGetListOfProducts(productsFromCart.keySet());
        BookedProducts bookedProducts = createBookedProductsFromListOfProducts(productListFromWarehouse, productsFromCart);
        return bookedProductsMapper.toDto(bookedProducts);
    }

    @Loggable
    @Transactional
    public void addQuantityInProduct(UUID id, int quantity) {
        Product product = warehouseRepository.findById(id)
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("Not found product with ID: " + id));
        product.setQuantity(quantity);
        warehouseRepository.save(product);
    }

    @Loggable
    public AddressDto getWarehouseAddress() {
        Address address = ADDRESSES[RANDOM.nextInt(ADDRESSES.length)];
        return addressMapper.toDto(address);
    }

    @Loggable
    @Transactional
    public void returnProductsInWarehouse(Map<UUID, Integer> products) {
        List<Product> productListFromWarehouse = checkAvailableAndGetListOfProducts(products.keySet());
        productListFromWarehouse.forEach(product -> {
            product.setQuantity(product.getQuantity() + products.get(product.getProductId()));
        });
        warehouseRepository.saveAll(productListFromWarehouse);
    }

    @Loggable
    @Transactional
    public BookedProductsDto assemblyProductsForDelivery(Map<UUID, Integer> products) {
        List<Product> productListFromWarehouse = checkAvailableAndGetListOfProducts(products.keySet());
        BookedProducts bookedProducts = createBookedProductsFromListOfProducts(productListFromWarehouse, products);
        productListFromWarehouse.forEach(product -> {
            product.setQuantity(product.getQuantity() - products.get(product.getProductId()));
        });
        warehouseRepository.saveAll(productListFromWarehouse);
        return bookedProductsMapper.toDto(bookedProducts);
    }

    @Loggable
    @Transactional
    public void shippedInDelivery(UUID orderId, UUID deliveryId) {
        ShippedDelivery shippedDelivery = ShippedDelivery.builder()
                .orderId(orderId)
                .deliveryId(deliveryId)
                .build();
        shippedRepository.save(shippedDelivery);
    }

    private List<Product> checkAvailableAndGetListOfProducts(Set<UUID> productId) {
        List<Product> productListFromWarehouse = warehouseRepository.findAllById(productId);
        if (productListFromWarehouse.size() != productId.size()) {
            throw new ProductInShoppingCartLowQuantityInWarehouse("Not enough product positions in warehouse");
        }
        return productListFromWarehouse;
    }

    private BookedProducts createBookedProductsFromListOfProducts(List<Product> productListFromWarehouse,
                                                                  Map<UUID, Integer> productsFromCart) {
        BookedProducts bookedProducts = BookedProducts.builder()
                .fragile(false)
                .deliveryVolume(0.0)
                .deliveryWeight(0.0)
                .build();
        for (Product product : productListFromWarehouse) {
            if (product.getQuantity() < productsFromCart.get(product.getProductId())) {
                throw new ProductInShoppingCartLowQuantityInWarehouse("Not enough products in warehouse");
            }
            bookedProducts.setDeliveryWeight(bookedProducts.getDeliveryWeight() +
                    product.getWeight() * productsFromCart.get(product.getProductId()));
            bookedProducts.setDeliveryVolume(bookedProducts.getDeliveryVolume() +
                    product.getDimension().calculateVolume() * productsFromCart.get(product.getProductId()));
            if (!bookedProducts.isFragile() && product.isFragile()) {
                bookedProducts.setFragile(true);
            }
        }
        return bookedProducts;
    }
}