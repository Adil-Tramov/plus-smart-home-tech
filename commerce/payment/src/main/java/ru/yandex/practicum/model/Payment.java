package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(schema = "payment", name = "payments")
@EqualsAndHashCode(of = "id")
@ToString
@Getter
@Setter
public class Payment {
    @Id
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "total_payment")
    private BigDecimal totalPayment;

    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;

    @Column(name = "products_price")
    private BigDecimal productsPrice;

    @Column(name = "fee_price")
    private BigDecimal feePrice;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    public Payment() {
        id = UUID.randomUUID();
    }
}
