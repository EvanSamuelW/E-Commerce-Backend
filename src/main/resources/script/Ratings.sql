-- Create a table for availableColors
CREATE TABLE ratings (
     id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    transaction_id BIGINT REFERENCES transactions(transaction_id) ON DELETE CASCADE,
    cart_item_id INT NOT NULL,
    rating FLOAT NOT NULL,
    review TEXT,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     CONSTRAINT fk_product
            FOREIGN KEY(cart_item_id)
            REFERENCES cart_items(cart_item_id) -- Assuming there is a 'products' table with 'product_id'
            ON DELETE CASCADE
);