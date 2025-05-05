CREATE TABLE products (
    product_id UUID DEFAULT gen_random_uuid(),
    product_name VARCHAR(255),
    product_value NUMERIC,
    product_created_at TIMESTAMP,
    PRIMARY KEY(product_id)
)