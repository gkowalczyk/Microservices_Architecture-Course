CREATE TABLE customer_orders (
                                 id BIGSERIAL PRIMARY KEY,
                                 customer_id BIGINT NOT NULL,
                                 customer_email VARCHAR(255),
                                 product_name VARCHAR(255) NOT NULL,
                                 quantity INTEGER NOT NULL,
                                 status VARCHAR(50) NOT NULL
);

CREATE INDEX idx_customer_orders_customer_id ON customer_orders(customer_id);