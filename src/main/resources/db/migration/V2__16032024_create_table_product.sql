CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    title VARCHAR(255) NOT NULL, 
    slug VARCHAR(255) NOT NULL UNIQUE, 
    itemNumber VARCHAR(255), 
    material VARCHAR(255), 
    finishing VARCHAR(255), 
    dimensionL INT, 
    dimensionW INT, 
    dimensionH INT, 
    netWeight FLOAT, 
    price DECIMAL(10, 2), 
    quantity INT, 
    mainPhoto VARCHAR(255)
);