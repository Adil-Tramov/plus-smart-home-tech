package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "Shopping_store")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "image_src")
    private String imageSrc;
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "state")
    private QuantityState quantityState;
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "state")
    private ProductState productState;
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "category")
    private ProductCategory productCategory;
    @Column(name = "price", nullable = false)
    private double price;
}
