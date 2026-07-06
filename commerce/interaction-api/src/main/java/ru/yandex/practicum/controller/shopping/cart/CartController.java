package ru.yandex.practicum.controller.shopping.cart;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartController {

    @GetMapping
    ShoppingCartDto getShoppingCart(@RequestParam @NotNull String username);

    @PutMapping
    ShoppingCartDto putProductInCart(@RequestParam @NotNull String username,
                                     @RequestBody Map<@NotNull UUID, @PositiveOrZero Integer> productCart);

    @DeleteMapping
    void deleteShoppingCart(@RequestParam @NotNull String username);

    @PostMapping("/remove")
    ShoppingCartDto removeProductFromCart(@RequestParam @NotNull String username,
                                          @RequestBody List<@NotNull UUID> productId);

    @PostMapping("/change-quantity")
    ShoppingCartDto changeProductQuantityInCart(@RequestParam @NotNull String username,
                                                @RequestBody @Valid ChangeProductQuantityRequest request);

}
