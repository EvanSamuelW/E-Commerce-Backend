CREATE TABLE products (
    id SERIAL PRIMARY KEY,  -- Auto-increment primary key
    name VARCHAR(1024) NOT NULL,
    description TEXT,
    image VARCHAR(1024),
    price FLOAT NOT NULL,
   category VARCHAR(255)
);

-- Create a table for availableSizes
CREATE TABLE product_sizes (
    product_id BIGINT REFERENCES products(id) ON DELETE CASCADE,
    size VARCHAR(50) NOT NULL,
    PRIMARY KEY (product_id, size)
);

-- Create a table for availableColors
CREATE TABLE product_colors (
    product_id BIGINT REFERENCES products(id) ON DELETE CASCADE,
    color VARCHAR(50) NOT NULL,
    PRIMARY KEY (product_id, color)
);
