package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(schema = "cart", name = "shopping_cart_products")
@EqualsAndHashCode(of = "id")
@ToString(exclude = "shoppingCart")
@Getter
@Setter
public class ShoppingCartProduct {
    @Id
    private UUID id;

    @Column(name = "product_id", nullable = false)
    @NotNull
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    @NotNull
    @Positive
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    private ShoppingCart shoppingCart;

    public ShoppingCartProduct() {
        id = UUID.randomUUID();
    }
}
