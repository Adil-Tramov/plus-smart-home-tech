package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "cart", name = "shopping_carts")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString(exclude = "products")
public class ShoppingCart {
    @Id
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank
    private String username;

    @OneToMany(mappedBy = "shoppingCart",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ShoppingCartProduct> products;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShoppingCartState state;

    public ShoppingCart() {
        id = UUID.randomUUID();
        products = new ArrayList<>();
        state = ShoppingCartState.ACTIVE;
    }

    public boolean isActive() {
        return ShoppingCartState.ACTIVE.equals(state);
    }
}
