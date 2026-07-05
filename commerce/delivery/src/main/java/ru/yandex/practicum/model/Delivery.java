package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "Delivery")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_id", nullable = false)
    private UUID deliveryId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "from_address", nullable = false)
    private Address fromAddress;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "to_address", nullable = false)
    private Address toAddress;
    @Column(name = "order_id", nullable = false)
    private UUID orderId;
    @Column(name = "delivery_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryState deliveryState;
}
