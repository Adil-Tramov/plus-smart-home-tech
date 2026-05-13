package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductState;
import ru.yandex.practicum.dto.product.QuantityState;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(schema = "store", name = "products")
@EqualsAndHashCode(of = "id")
@ToString(exclude = "description")
@Getter
@Setter
public class Product {
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 3, max = 1000)
    private String description;

    @Column(name = "image_src")
    @Size(min = 3, max = 500)
    private String imageSrc;

    @Column(name = "quantity_state", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private QuantityState quantityState;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(name = "price", nullable = false)
    @NotNull
    @DecimalMin(value = "1.0")
    private BigDecimal price;

    @Column(name = "product_state", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductState productState = ProductState.ACTIVE;

    public Product() {
        id = UUID.randomUUID();
    }
}
