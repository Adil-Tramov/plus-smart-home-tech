CREATE TABLE IF NOT EXISTS Shopping_cart (
    id BIGINT NOT NULL PRIMARY KEY,
    cart_id UUID,
    username VARCHAR(50) NOT NULL,
    product_id UUID,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    shopping_cart_status VARCHAR(50) NOT NULL
);