package ru.yandex.practicum.controller.warehouse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.*;

public interface WarehouseController {

    @PutMapping
    void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest product);

    @PostMapping("/check")
    BookedProductsDto checkAvailableAllProductInShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCart);

    @PostMapping("/add")
    void addQuantityInProduct(@RequestBody @Valid AddProductToWarehouseRequest productQuantity);

    @GetMapping("/address")
    AddressDto getWarehouseAddress();
}
