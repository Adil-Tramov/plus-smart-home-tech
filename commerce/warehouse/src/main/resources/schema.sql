CREATE TABLE IF NOT EXISTS Warehouse (
    id UUID PRIMARY KEY,
    fragile BOOLEAN NOT NULL,
    width DOUBLE PRECISION NOT NULL CHECK (width >= 1.0),
    height DOUBLE PRECISION NOT NULL CHECK (height >= 1.0),
    depth DOUBLE PRECISION NOT NULL CHECK (depth >= 1.0),
    weight DOUBLE PRECISION NOT NULL CHECK (weight >= 1.0),
    quantity INTEGER NOT NULL CHECK (quantity >= 0.0)
);

CREATE TABLE IF NOT EXISTS Shipped (
    order_id UUID PRIMARY KEY,
    delivery_id UUID NOT NULL
);