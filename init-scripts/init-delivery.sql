CREATE SCHEMA IF NOT EXISTS delivery;

CREATE TABLE IF NOT EXISTS delivery.deliveries
(
    id           UUID PRIMARY KEY,
    order_id     UUID        NOT NULL,
    state        VARCHAR(16) NOT NULL,

    from_country VARCHAR(100),
    from_city    VARCHAR(100),
    from_street  VARCHAR(255),
    from_house   VARCHAR(50),
    from_flat    VARCHAR(50),

    to_country   VARCHAR(100),
    to_city      VARCHAR(100),
    to_street    VARCHAR(255),
    to_house     VARCHAR(50),
    to_flat      VARCHAR(50)
);