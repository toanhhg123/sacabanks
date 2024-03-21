CREATE TABLE orders (
    id binary(16) PRIMARY KEY, user_id binary(16), created_at DATETIME(6), updated_at DATETIME(6), CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES user (id)
)