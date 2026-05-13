package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(schema = "warehouse", name = "booking")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString
public class Booking {
    @Id
    private UUID id;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @ElementCollection
    @CollectionTable(schema = "warehouse",
            name = "booking_products",
            joinColumns = @JoinColumn(name = "booking_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity", nullable = false)
    private Map<UUID, Long> products;

    public Booking() {
        id = UUID.randomUUID();
    }
}
