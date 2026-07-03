CREATE TABLE IF NOT EXISTS Address (
    address_id BIGSERIAL PRIMARY KEY,
    country VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    street VARCHAR(50) NOT NULL,
    house VARCHAR(50) NOT NULL,
    flat VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Delivery (
    delivery_id UUID PRIMARY KEY,
    from_address BIGINT NOT NULL,
    to_address BIGINT NOT NULL,
    order_id UUID,
    delivery_state VARCHAR(50) NOT NULL,
    FOREIGN KEY (from_address) REFERENCES Address(address_id) ON DELETE CASCADE,
    FOREIGN KEY (to_address) REFERENCES Address(address_id) ON DELETE CASCADE
);