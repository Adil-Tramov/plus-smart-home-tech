package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.ShippedDelivery;

import java.util.UUID;

public interface ShippedRepository extends JpaRepository<ShippedDelivery, UUID> {
}
