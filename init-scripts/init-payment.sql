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

INSERT INTO payment.payments (id, order_id, total_payment, delivery_price, products_price, fee_price, status)
VALUES
    ('750e8400-e29b-41d4-a716-446655440000', '650e8400-e29b-41d4-a716-446655440000', 150.00, 20.00, 130.00, 5.00, 'PENDING'),
    ('750e8400-e29b-41d4-a716-446655440001', '650e8400-e29b-41d4-a716-446655440001', 80.00, 15.00, 65.00, 3.00, 'SUCCESS'),
    ('750e8400-e29b-41d4-a716-446655440002', '650e8400-e29b-41d4-a716-446655440002', 120.00, 10.00, 110.00, 4.50, 'FAILED');