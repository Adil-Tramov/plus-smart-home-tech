package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.controller.order.feign.OrderControllerFeign;
import ru.yandex.practicum.controller.warehouse.WarehouseController;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.logging.Loggable;
import ru.yandex.practicum.service.WarehouseService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
@Validated
public class WarehouseControllerImpl implements WarehouseController {
    private final WarehouseService warehouseService;
    private final OrderControllerFeign orderControllerFeign;

    @Loggable
    @PutMapping
    public void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest dto) {
        warehouseService.saveProductInWarehouse(dto);
    }

    @Loggable
    @PostMapping("/check")
    public BookedProductsDto checkAvailableAllProductInShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCart) {
        return warehouseService.checkShoppingCart(shoppingCart.getProducts());
    }

    @Loggable
    @PostMapping("/add")
    public void addQuantityInProduct(@RequestBody @Valid AddProductToWarehouseRequest productQuantity) {
        warehouseService.addQuantityInProduct(productQuantity.getProductId(), productQuantity.getQuantity());
    }

    @Loggable
    @GetMapping("/address")
    public AddressDto getWarehouseAddress() {
        return warehouseService.getWarehouseAddress();
    }

    @PostMapping("/shipped")
    public void shippedInDelivery(@RequestBody @Valid ShippedToDeliveryRequest request) {
        warehouseService.shippedInDelivery(request.getOrderId(), request.getDeliveryId());
    }

    @PostMapping("/return")
    public void returnProductsInWarehouse(@RequestBody Map<@NotNull UUID, @PositiveOrZero Integer> products) {
        warehouseService.returnProductsInWarehouse(products);
    }

    @PostMapping("/assembly")
    public BookedProductsDto assemblyProductsForDelivery(@RequestBody @Valid AssemblyProductsForOrderRequest request) {
        return warehouseService.assemblyProductsForDelivery(request.getProducts());
    }
}