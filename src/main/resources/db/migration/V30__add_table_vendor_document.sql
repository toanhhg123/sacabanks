CREATE TABLE vendor_document (
    id BINARY(16) PRIMARY KEY, 
    title VARCHAR(250),
    user_id BINARY(16),
    file VARCHAR(250),
    created_at DATETIME(6),
    updated_at DATETIME(6),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
