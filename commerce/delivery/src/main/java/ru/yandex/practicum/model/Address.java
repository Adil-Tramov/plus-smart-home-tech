package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Address")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long addressId;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "house", nullable = false)
    private String house;
    @Column(name = "flat", nullable = false)
    private String flat;
}
