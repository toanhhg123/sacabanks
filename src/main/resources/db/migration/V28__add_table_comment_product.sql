CREATE TABLE product_comment (
    id BINARY(16) PRIMARY KEY, 
    content TEXT,
    title VARCHAR(250),
    user_id BINARY(16),
    product_id BINARY(16),
    created_at DATETIME(6),
    updated_at DATETIME(6),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
