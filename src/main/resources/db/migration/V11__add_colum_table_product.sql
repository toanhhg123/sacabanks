ALTER TABLE product ADD COLUMN category_id binary(16);

ALTER TABLE product ADD CONSTRAINT fk_product_category
FOREIGN KEY (category_id) REFERENCES category(id);
