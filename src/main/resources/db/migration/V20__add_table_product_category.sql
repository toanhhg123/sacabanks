CREATE TABLE category_product (
    id BINARY(16) PRIMARY KEY, 
    category_id BINARY(16),
    product_id BINARY(16),
    created_at DATETIME(6),
    updated_at DATETIME(6),
    CONSTRAINT fk_category 
        FOREIGN KEY (category_id) 
        REFERENCES category(id) 
        ON DELETE SET NULL,
        
    CONSTRAINT fk_product 
        FOREIGN KEY (product_id) 
        REFERENCES product(id) 
        ON DELETE SET NULL
);
