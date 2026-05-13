package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.cart.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.exception.*;
import ru.yandex.practicum.mapper.AddressMapper;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Booking;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.BookingRepository;
import ru.yandex.practicum.repository.ProductRepository;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {
    private static final String[] ADDRESSES = new String[] {"ADDRESS_1", "ADDRESS_2"};
    private final ProductMapper productMapper;
    private final AddressMapper addressMapper;
    private final ProductRepository productRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    @Override
    public void addProduct(NewProductInWarehouseRequest request) {
        log.debug("Добавляем товар на склад: {}", request);
        if (productRepository.existsById(request.productId())) {
            throw new SpecifiedProductAlreadyInWarehouseException(request.productId());
        }
        Product product = productMapper.toProduct(request);
        Product savedProduct = productRepository.save(product);
        log.debug("Добавили товар на склад: {}", savedProduct);
    }

    @Transactional(readOnly = true)
    @Override
    public BookedProductsDto check(ShoppingCartDto shoppingCartDto) {
        log.debug("Проверяем доступность товаров из корзины: {}", shoppingCartDto);
        Map<UUID, Long> requiredProducts = shoppingCartDto.products();
        Set<Product> storedProducts = productRepository.findAllByIdIn(requiredProducts.keySet());
        checkAllProductsExists(storedProducts, requiredProducts);
        checkProductsQuantity(storedProducts, requiredProducts);
        BookedProductsDto bookedProductsDto = productMapper.toDto(requiredProducts, storedProducts);
        log.debug("Сформировали доставку: {}", bookedProductsDto);
        return bookedProductsDto;
    }

    @Transactional
    @Override
    public void increaseQuantity(AddProductToWarehouseRequest request) {
        log.debug("Увеличиваем количество товара с ID: {} на {}", request.productId(), request.quantity());
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException(request.productId()));
        product.setQuantity(product.getQuantity() + request.quantity());
        log.debug("Увеличили количество товара: {}", product);
    }

    @Override
    public AddressDto getAddress() {
        log.debug("Определяем адрес склада.");
        String address = ADDRESSES[Random.from(new SecureRandom()).nextInt(ADDRESSES.length)];
        AddressDto addressDto = addressMapper.toDto(address);
        log.debug("Адрес склада: {}", addressDto);
        return addressDto;
    }

    @Transactional
    @Override
    public void shipToDelivery(ShippedToDeliveryRequest request) {
        log.debug("Обрабатываем прием заказа с ID: {} в доставку c ID: {}",
                request.orderId(), request.deliveryId());
        Booking booking = bookingRepository.findByOrderId(request.orderId())
                .orElseThrow(() -> new NoBookingFoundException(request.orderId()));
        booking.setDeliveryId(request.deliveryId());
        log.debug("Товары по брони с ID: {} переданы в доставку. ID заказа: {}. ID доставки: {}",
                booking.getId(), request.orderId(), request.deliveryId());
    }

    @Transactional
    @Override
    public void returnProductsToWarehouse(Map<UUID, Long> products) {
        log.debug("Возвращаем товары на склад: {}", products);
        Set<Product> storedProducts = productRepository.findAllByIdIn(products.keySet());
        checkAllProductsExists(storedProducts, products);
        storedProducts.forEach(product -> {
            UUID id = product.getId();
            product.setQuantity(product.getQuantity() + products.get(id));
            log.trace("Увеличили на складе количество товара ID: {}, новое количество: {}", id, product.getQuantity());
        });
    }

    @Override
    public BookedProductsDto assemblyProducts(AssemblyProductsForOrderRequest request) {
        log.debug("Бронируем и собираем товары к заказу: {}. Количество позиций: {}",
                request.orderId(), request.products().size());
        Map<UUID, Long> requiredProducts = request.products();
        Set<Product> storedProducts = productRepository.findAllByIdIn(requiredProducts.keySet());
        checkAllProductsExists(storedProducts, requiredProducts);
        checkProductsQuantity(storedProducts, requiredProducts);
        storedProducts.forEach(product -> {
            UUID id = product.getId();
            product.setQuantity(product.getQuantity() - requiredProducts.get(id));
        });
        Booking booking = new Booking();
        booking.setOrderId(request.orderId());
        booking.setProducts(requiredProducts);
        Booking savedBooking = bookingRepository.save(booking);
        BookedProductsDto bookedProductsDto = productMapper.toDto(requiredProducts, storedProducts);
        log.debug("Забронировали товары к заказу с ID: {}. Параметры собранного заказа: {}. ID брони: {}",
                request.orderId(), bookedProductsDto, savedBooking.getId());
        return bookedProductsDto;
    }

    private void checkAllProductsExists(Set<Product> storedProducts, Map<UUID, Long> requiredProducts) {
        Set<UUID> storedProductIds = storedProducts.stream().map(Product::getId).collect(Collectors.toSet());
        Set<UUID> notFoundProducts = new HashSet<>(requiredProducts.keySet());
        notFoundProducts.removeAll(storedProductIds);
        if (!notFoundProducts.isEmpty()) {
            throw new ProductInShoppingCartNotInWarehouse(notFoundProducts);
        }
    }

    private void checkProductsQuantity(Set<Product> storedProducts, Map<UUID, Long> requiredProducts) {
        Set<UUID> deficitProducts = storedProducts.stream()
                .filter(product -> product.getQuantity() < requiredProducts.get(product.getId()))
                .map(Product::getId)
                .collect(Collectors.toSet());
        if (!deficitProducts.isEmpty()) {
            throw new ProductInShoppingCartLowQuantityInWarehouse(deficitProducts);
        }
    }
}
