CREATE SCHEMA IF NOT EXISTS store;

CREATE TABLE IF NOT EXISTS store.products
(
    id             UUID PRIMARY KEY,
    name           VARCHAR(100)   NOT NULL,
    description    VARCHAR(1000)  NOT NULL,
    image_src      VARCHAR(500),
    quantity_state VARCHAR(6)     NOT NULL,
    category       VARCHAR(8),
    price          DECIMAL(10, 2) NOT NULL,
    product_state  VARCHAR(10)    NOT NULL
);

INSERT INTO store.products (id, name, description, image_src, quantity_state, category, price, product_state)
VALUES ('550e8400-e29b-41d4-a716-446655440100',
        'Лампа',
        'Умная лампа',
        'https://example.com/lamp.jpg',
        'FEW',
        'LIGHTING',
        1200.99,
        'ACTIVE'),
       ('550e8400-e29b-41d4-a716-446655440101',
        'Хаб',
        'Хаб умного дома',
        'https://example.com/hub.jpg',
        'ENOUGH',
        'CONTROL',
        899.50,
        'ACTIVE'),
       ('550e8400-e29b-41d4-a716-446655440102',
        'Датчик',
        'Датчик движения',
        'https://example.com/sensor.jpg',
        'MANY',
        'SENSORS',
        29.99,
        'ACTIVE');