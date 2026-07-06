package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "shopping_cart_id")
    private UUID shoppingCartId;

    @ElementCollection
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    Map<UUID, Integer> products;

    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "delivery_weight")
    private double deliveryWeight;

    @Column(name = "delivery_volume")
    private double deliveryVolume;

    @Column(name = "fragile")
    private boolean fragile;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;

    @Column(name = "product_price")
    private BigDecimal productPrice;
}
