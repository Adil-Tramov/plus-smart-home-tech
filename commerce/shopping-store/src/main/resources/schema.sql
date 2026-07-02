CREATE TABLE IF NOT EXISTS Shopping_store (
    id UUID PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image_src VARCHAR(255),
    quantity_state BIGINT NOT NULL,
    product_state BIGINT NOT NULL,
    product_category BIGINT NOT NULL,
    price DOUBLE PRECISION NOT NULL CHECK (price >= 1.0)
);