package ru.yandex.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "Shipped")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippedDelivery {
    @Id
    @Column(name = "order_id", nullable = false)
    private UUID orderId;
    @Column(name = "delivery_id", nullable = false)
    private UUID deliveryId;
}
