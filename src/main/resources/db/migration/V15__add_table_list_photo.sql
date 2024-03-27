DROP TABLE IF EXISTS list_photo;

-- Recreate the list_photo table with updated schema
CREATE TABLE list_photo (
    id BINARY(16) PRIMARY KEY,
    product_id BINARY(16),
    photo_url VARCHAR(255),
    created_at DATETIME(6),
    updated_at DATETIME(6),
    FOREIGN KEY (product_id) REFERENCES product(id)
);