CREATE SCHEMA IF NOT EXISTS orders;

CREATE TABLE IF NOT EXISTS orders.orders
(
    id UUID PRIMARY KEY,
    shopping_cart_id UUID,
    payment_id UUID,
    delivery_id UUID,
    state VARCHAR(16),
    delivery_weight DECIMAL(10, 2),
    delivery_volume DECIMAL(10, 2),
    fragile BOOLEAN,
    total_price DECIMAL(10, 2),
    delivery_price DECIMAL(10, 2),
    product_price DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS orders.order_products
(
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity BIGINT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders.orders (id) ON DELETE CASCADE
);

INSERT INTO orders.orders (id, shopping_cart_id, payment_id, delivery_id, state, delivery_weight, delivery_volume, fragile, total_price, delivery_price, product_price)
VALUES
    ('650e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440000', '750e8400-e29b-41d4-a716-446655440000', '850e8400-e29b-41d4-a716-446655440000', 'NEW', 5.00, 10.00, false, 150.00, 20.00, 130.00),
    ('650e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', '750e8400-e29b-41d4-a716-446655440001', '850e8400-e29b-41d4-a716-446655440001', 'DELIVERED', 2.50, 5.00, true, 80.00, 15.00, 65.00),
    ('650e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440000', '750e8400-e29b-41d4-a716-446655440002', '850e8400-e29b-41d4-a716-446655440002', 'COMPLETED', 3.00, 7.00, false, 120.00, 10.00, 110.00);

INSERT INTO orders.order_products (order_id, product_id, quantity)
VALUES
    ('650e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440100', 2),
    ('650e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440101', 1),
    ('650e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440101', 3),
    ('650e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440100', 1),
    ('650e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440101', 2);
