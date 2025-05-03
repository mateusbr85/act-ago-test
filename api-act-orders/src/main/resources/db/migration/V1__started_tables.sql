CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE orders (
    order_id UUID DEFAULT gen_random_uuid(),
    order_fk_product_id UUID,
    order_value NUMERIC,
    order_created_at TIMESTAMP,
    PRIMARY KEY(order_id)
);

CREATE TABLE status_type (
    status_type_id SERIAL PRIMARY KEY,
    status_type_name VARCHAR(255),
    status_type_created_at TIMESTAMP
);

CREATE TABLE order_status (
    order_status_id SERIAL PRIMARY KEY,
    order_status_fk_order_id UUID,
    order_status_fk_status_type INTEGER
);

ALTER TABLE order_status
    ADD CONSTRAINT fk_order_status_order FOREIGN KEY(order_status_fk_order_id)
    REFERENCES orders(order_id)
    ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE order_status
    ADD CONSTRAINT fk_order_status_status FOREIGN KEY(order_status_fk_status_type)
    REFERENCES status_type(status_type_id)
    ON UPDATE CASCADE ON DELETE NO ACTION;
