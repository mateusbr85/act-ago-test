CREATE TABLE payments (
    payment_id UUID DEFAULT gen_random_uuid(),
    payment_fk_order_id UUID,
    payment_status VARCHAR(55),
    payment_created_at TIMESTAMP,
    PRIMARY KEY(payment_id)
);

ALTER TABLE payments
    ADD CONSTRAINT fk_order_id
    FOREIGN KEY(payment_fk_order_id)
    REFERENCES orders(order_id)
    ON UPDATE NO ACTION ON DELETE CASCADE;