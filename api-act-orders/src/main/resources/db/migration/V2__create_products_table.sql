CREATE TABLE products (
    product_id UUID DEFAULT gen_random_uuid(),
    product_name VARCHAR(255),
    product_value NUMERIC,
    product_created_at TIMESTAMP,
    PRIMARY KEY(product_id)
);


ALTER TABLE orders
    ADD FOREIGN KEY(order_fk_product_id)
    REFERENCES products(product_id)
    ON UPDATE NO ACTION ON DELETE NO ACTION;