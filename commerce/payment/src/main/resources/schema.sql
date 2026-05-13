CREATE SCHEMA IF NOT EXISTS payment;

CREATE TABLE IF NOT EXISTS payment.payments
(
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    total_payment DECIMAL(10, 2),
    delivery_price DECIMAL(10, 2),
    products_price DECIMAL(10, 2),
    fee_price DECIMAL(10, 2),
    status VARCHAR(7) NOT NULL
);