CREATE TABLE IF NOT EXISTS Orders (
    order_id UUID PRIMARY KEY,
    shopping_cart_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    payment_id UUID NOT NULL,
    delivery_id UUID NOT NULL,
    state VARCHAR(50) NOT NULL,
    delivery_weight DOUBLE PRECISION NOT NULL CHECK (delivery_weight > 0.0),
    delivery_volume DOUBLE PRECISION NOT NULL CHECK (delivery_volume > 0.0),
    fragile BOOLEAN NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    delivery_price DOUBLE PRECISION NOT NULL,
    product_price DOUBLE PRECISION NOT NULL
);