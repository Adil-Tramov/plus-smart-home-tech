CREATE TABLE IF NOT EXISTS Payment (
    payment_id UUID PRIMARY KEY,
    total_payment DOUBLE PRECISION,
    total_delivery DOUBLE PRECISION,
    total_fee DOUBLE PRECISION,
    payment_status VARCHAR(50)
);