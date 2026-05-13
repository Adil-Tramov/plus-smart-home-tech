CREATE SCHEMA IF NOT EXISTS cart;

CREATE TABLE IF NOT EXISTS cart.shopping_carts
(
    id UUID PRIMARY KEY NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    state VARCHAR(8) NOT NULL
);

CREATE TABLE IF NOT EXISTS cart.shopping_cart_products
(
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    quantity BIGINT NOT NULL,
    shopping_cart_id UUID NOT NULL,
    FOREIGN KEY (shopping_cart_id) REFERENCES cart.shopping_carts(id) ON DELETE CASCADE
);

INSERT INTO cart.shopping_carts (id, username, state)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'first_user', 'ACTIVE'),
    ('550e8400-e29b-41d4-a716-446655440001', 'second_user', 'ACTIVE');

INSERT INTO cart.shopping_cart_products (id, product_id, quantity, shopping_cart_id)
VALUES
    ('550e8400-e29b-41d4-a716-446655440010', '550e8400-e29b-41d4-a716-446655440100', 2, '550e8400-e29b-41d4-a716-446655440000'),
    ('550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440101', 1, '550e8400-e29b-41d4-a716-446655440001');