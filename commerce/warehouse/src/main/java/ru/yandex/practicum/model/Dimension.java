package ru.yandex.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Dimension {
    @Column(name = "width", nullable = false)
    private double width;
    @Column(name = "height", nullable = false)
    private double height;
    @Column(name = "depth", nullable = false)
    private double depth;

    public double calculateVolume() {
        return width * height * depth;
    }
}
