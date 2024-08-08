ALTER TABLE blog
DROP COLUMN focus_keywords;

CREATE TABLE blog_focus_keywords (
    blog_id BINARY(16) NOT NULL,
    keyword VARCHAR(255) NOT NULL,
    PRIMARY KEY (blog_id, keyword),
    FOREIGN KEY (blog_id) REFERENCES blog(id) ON DELETE CASCADE
);
