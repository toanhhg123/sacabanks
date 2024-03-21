ALTER TABLE product MODIFY COLUMN id binary(16);

ALTER TABLE product ADD COLUMN created_at DATETIME(6);

ALTER TABLE product ADD COLUMN updated_at DATETIME(6);

ALTER TABLE product ADD COLUMN user_id binary(16);

ALTER TABLE product
ADD CONSTRAINT fk_product_user FOREIGN KEY (user_id) REFERENCES user (id);