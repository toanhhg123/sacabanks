CREATE TABLE order_item (
    id binary(16) PRIMARY KEY, product_id binary(16) NOT NULL, order_id binary(16) NOT NULL, created_at DATETIME(6), updated_at DATETIME(6), CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES product (id), CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders (id)
)