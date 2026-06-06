package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "Shopping_cart")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cart_id", nullable = false, unique = true)
    private UUID shoppingCartId;
    @Column(name = "username")
    private String username;
    @ElementCollection
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<UUID, Integer> products;
    @Enumerated(EnumType.STRING)
    @Column(name = "shopping_cart_status")
    private ShoppingCartStatus shoppingCartStatus;

    @PrePersist
    public void generateShoppingCartId() {
        if (shoppingCartId == null) {
            shoppingCartId = UUID.randomUUID();
        }
    }
}
