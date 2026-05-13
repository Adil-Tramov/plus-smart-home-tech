CREATE SCHEMA IF NOT EXISTS warehouse;

CREATE TABLE IF NOT EXISTS warehouse.products
(
    id       UUID PRIMARY KEY,
    fragile  BOOLEAN,
    width    DECIMAL(10, 2) NOT NULL,
    height   DECIMAL(10, 2) NOT NULL,
    depth    DECIMAL(10, 2) NOT NULL,
    weight   DECIMAL(10, 2) NOT NULL,
    quantity BIGINT         NOT NULL
);

CREATE TABLE IF NOT EXISTS warehouse.bookings
(
    id          UUID PRIMARY KEY,
    order_id    UUID,
    delivery_id UUID
);

CREATE TABLE IF NOT EXISTS warehouse.booking_products
(
    booking_id UUID,
    product_id UUID,
    quantity   BIGINT NOT NULL,
    PRIMARY KEY (booking_id, product_id),
    FOREIGN KEY (booking_id) REFERENCES warehouse.bookings (id)
);

INSERT INTO warehouse.products (id, fragile, width, height, depth, weight, quantity)
VALUES ('550e8400-e29b-41d4-a716-446655440100', TRUE, 10.5, 20.0, 15.3, 2.5, 50),
       ('550e8400-e29b-41d4-a716-446655440101', FALSE, 5.0, 10.0, 7.5, 1.0, 200),
       ('550e8400-e29b-41d4-a716-446655440102', TRUE, 30.0, 40.5, 20.0, 5.5, 10);