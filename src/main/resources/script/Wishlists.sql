
-- Create table for storing wishlist items (products in a wishlist)
CREATE TABLE wishlist_items (
    wishlist_item_id SERIAL PRIMARY KEY,    -- Unique identifier for each wishlist item
    user_id INT NOT NULL,               -- Associated wishlist ID
    product_id INT NOT NULL,                -- ID of the product added to the wishlist
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp when the item was added
    CONSTRAINT fk_user
           FOREIGN KEY(user_id)
           REFERENCES users(user_id)      -- Assuming there is a 'users' table with 'user_id'
           ON DELETE CASCADE,
    CONSTRAINT fk_product_wishlist
        FOREIGN KEY(product_id)
        REFERENCES products(product_id)    -- Assuming there is a 'products' table with 'product_id'
        ON DELETE CASCADE
);