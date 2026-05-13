CREATE SCHEMA IF NOT EXISTS orders;

CREATE TABLE IF NOT EXISTS orders.orders
(
    id               UUID PRIMARY KEY,
    shopping_cart_id UUID,
    username         VARCHAR(100) NOT NULL,
    payment_id       UUID,
    delivery_id      UUID,
    state            VARCHAR(16),
    delivery_weight  DECIMAL(10, 2),
    delivery_volume  DECIMAL(10, 2),
    fragile          BOOLEAN,
    total_price      DECIMAL(10, 2),
    delivery_price   DECIMAL(10, 2),
    product_price    DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS orders.order_products
(
    order_id   UUID   NOT NULL,
    product_id UUID   NOT NULL,
    quantity   BIGINT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders.orders (id)
);