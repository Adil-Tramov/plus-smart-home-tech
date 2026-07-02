package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.controller.warehouse.WarehouseController;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.BookedProducts;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.model.mapper.AddressMapper;
import ru.yandex.practicum.model.mapper.BookedProductsMapper;
import ru.yandex.practicum.model.mapper.ProductMapper;
import ru.yandex.practicum.service.WarehouseService;

@Slf4j
@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
@Validated
public class WarehouseControllerImpl implements WarehouseController {
    private final WarehouseService warehouseService;
    private final AddressMapper addressMapper;
    private final ProductMapper productMapper;
    private final BookedProductsMapper bookedProductsMapper;

    @Loggable
    @PutMapping
    public void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest dto) {
        Product product = productMapper.toEntity(dto);
        warehouseService.saveProductInWarehouse(product);
    }

    @Loggable
    @PostMapping("/check")
    public BookedProductsDto checkAvailableAllProductInShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCart) {
        BookedProducts bookedProducts = warehouseService.checkShoppingCart(shoppingCart.getProducts());
        return bookedProductsMapper.toDto(bookedProducts);
    }

    @Loggable
    @PostMapping("/add")
    public void addQuantityInProduct(@RequestBody @Valid AddProductToWarehouseRequest productQuantity) {
        warehouseService.addQuantityInProduct(productQuantity.getProductId(), productQuantity.getQuantity());
    }

    @Loggable
    @GetMapping("/address")
    public AddressDto getWarehouseAddress() {
        Address address = warehouseService.getWarehouseAddress();
        return addressMapper.toDto(address);
    }
}