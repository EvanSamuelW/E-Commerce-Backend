-- Create a table for availableColors
CREATE TABLE ratings (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    transaction_id BIGINT REFERENCES transactions(id) ON DELETE CASCADE,
    product_id INT NOT NULL,
    rating FLOAT NOT NULL,
    review TEXT,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, transaction_id ),
     CONSTRAINT fk_product
            FOREIGN KEY(product_id)
            REFERENCES products(id) -- Assuming there is a 'products' table with 'product_id'
            ON DELETE CASCADE
);