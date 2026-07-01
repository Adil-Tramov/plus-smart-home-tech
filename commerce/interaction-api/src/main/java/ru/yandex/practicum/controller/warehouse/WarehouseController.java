package ru.yandex.practicum.controller.warehouse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.*;

import java.util.Map;
import java.util.UUID;

public interface WarehouseController {

    @PutMapping
    void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest product);

    @PostMapping("/check")
    BookedProductsDto checkAvailableAllProductInShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCart);

    @PostMapping("/add")
    void addQuantityInProduct(@RequestBody @Valid AddProductToWarehouseRequest productQuantity);

    @GetMapping("/address")
    AddressDto getWarehouseAddress();

    @PostMapping("/shipped")
    void shippedInDelivery(@RequestBody @Valid ShippedToDeliveryRequest request);

    @PostMapping("/return")
    void returnProductsInWarehous(@RequestBody Map<@NotNull UUID, @PositiveOrZero Integer> products);

    @PostMapping("assembly")
    BookedProductsDto assemblyProductsForDelivery(@RequestBody @Valid AssemblyProductsForOrderRequest request);

}
