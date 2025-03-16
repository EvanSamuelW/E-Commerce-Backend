-- Create a table for product categories
CREATE TABLE product_categories (
    id SERIAL PRIMARY KEY,             -- Unique identifier for the category
    name VARCHAR(255) NOT NULL,        -- Name of the category (e.g., Electronics, Clothing)
    description TEXT,                  -- Optional description of the category
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- When the category was created
);