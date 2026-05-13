package ru.yande.practicum.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.dto.order.OrderState;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(schema = "orders", name = "orders")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Order {
    @Id
    private UUID id;

    @Column(name = "shopping_cart_id")
    private UUID shoppingCartId;

    @Column(name = "username", nullable = false)
    private String username;

    @ElementCollection
    @CollectionTable(schema = "order",
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity", nullable = false)
    private Map<UUID, Long> products;

    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderState state = OrderState.NEW;

    @Column(name = "delivery_weight")
    private Double deliveryWeight;

    @Column(name = "delivery_volume")
    private Double deliveryVolume;

    @Column(name = "fragile")
    private Boolean fragile;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    public Order() {
        id = UUID.randomUUID();
    }
}
