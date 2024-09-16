CREATE TABLE product_document (
    id BINARY(16) PRIMARY KEY, 
    title VARCHAR(250),
    product_id BINARY(16),
    file VARCHAR(250),
    created_at DATETIME(6),
    updated_at DATETIME(6),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
