CREATE TABLE banner (
    id BINARY(16) PRIMARY KEY, 
    image VARCHAR(250),
    title VARCHAR(250),
    description TEXT,
    isActive BOOLEAN,
    created_at DATETIME(6),
    updated_at DATETIME(6)
);
