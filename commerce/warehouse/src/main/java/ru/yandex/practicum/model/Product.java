package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "Warehouse")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    private UUID productId;
    @Column(name = "fragile", nullable = false)
    private boolean fragile;
    @Embedded
    private Dimension dimension;
    @Column(name = "weight", nullable = false)
    private double weight;
    @Column(name = "quantity")
    private int quantity;
}
