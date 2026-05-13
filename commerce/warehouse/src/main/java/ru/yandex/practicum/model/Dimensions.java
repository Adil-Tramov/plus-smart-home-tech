package ru.yandex.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
public class Dimensions {
    @Column(name = "width", nullable = false)
    @DecimalMin("1.0")
    private double width;

    @Column(name = "height", nullable = false)
    @DecimalMin("1.0")
    private double height;

    @Column(name = "depth", nullable = false)
    @DecimalMin("1.0")
    private double depth;
}
