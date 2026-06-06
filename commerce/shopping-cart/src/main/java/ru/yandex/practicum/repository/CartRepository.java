package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.model.ShoppingCartStatus;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<ShoppingCart, UUID> {

    ShoppingCart findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<ShoppingCart> findByUsernameAndShoppingCartStatus(String username, ShoppingCartStatus status);

    boolean existsByUsernameAndShoppingCartStatus(String username, ShoppingCartStatus status);
}
