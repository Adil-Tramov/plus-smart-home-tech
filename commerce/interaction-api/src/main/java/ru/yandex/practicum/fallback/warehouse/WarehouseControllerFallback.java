package ru.yandex.practicum.fallback.warehouse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.controller.warehouse.feign.WarehouseControllerFeign;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;

import java.util.Map;
import java.util.UUID;

@Component
public class WarehouseControllerFallback implements WarehouseControllerFeign {

    @Override
    public void newProductInWarehouse(@RequestBody NewProductInWarehouseRequest product) {
        throw new SpecifiedProductAlreadyInWarehouseException("Error saving product");
    }

    @Override
    public BookedProductsDto checkAvailableAllProductInShoppingCart(@RequestBody ShoppingCartDto shoppingCart) {
        throw new ProductInShoppingCartLowQuantityInWarehouse("Check error");
    }

    @Override
    public void addQuantityInProduct(@RequestBody AddProductToWarehouseRequest productQuantity) {
        throw new NoSpecifiedProductInWarehouseException("Add error");
    }

    @Override
    public AddressDto getWarehouseAddress() {
        return AddressDto.builder()
                .country("Error")
                .city("Error")
                .street("Error")
                .house("Error")
                .flat("Error")
                .build();
    }

    @Override
    public void shippedInDelivery(@RequestBody @Valid ShippedToDeliveryRequest request) {
        throw new ProductInShoppingCartLowQuantityInWarehouse("Check error");
    }

    @Override
    public void returnProductsInWarehous(@RequestBody Map<@NotNull UUID, @PositiveOrZero Integer> products) {
        throw new ProductInShoppingCartLowQuantityInWarehouse("Check error");
    }

    @Override
    public BookedProductsDto assemblyProductsForDelivery(@RequestBody @Valid AssemblyProductsForOrderRequest request) {
        return null;
    }

}
