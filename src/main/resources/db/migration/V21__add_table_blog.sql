CREATE TABLE blog (
    id BINARY(16) PRIMARY KEY, 
    content TEXT,
    title TEXT,
    focus_keywords JSON,
    slug VARCHAR(500),
    created_at DATETIME(6),
    updated_at DATETIME(6)
);
