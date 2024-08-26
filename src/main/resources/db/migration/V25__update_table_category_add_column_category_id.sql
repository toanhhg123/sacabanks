ALTER TABLE category
ADD COLUMN category_id BINARY(16) NULL, 
ADD CONSTRAINT fk_category_parent  
FOREIGN KEY (category_id)          
REFERENCES category(id)           
ON DELETE SET NULL;      
