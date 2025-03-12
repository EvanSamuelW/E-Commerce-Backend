-- Create table for storing cart items
CREATE TABLE carts (
    cart_id SERIAL PRIMARY KEY,         -- Unique identifier for the cart
    user_id INT NOT NULL,               -- User who owns the cart (Foreign Key to Users table)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp when the cart was created
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for when the cart was last updated
    status VARCHAR(20) DEFAULT 'active', -- The status of the cart (e.g., active, abandoned)
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
        REFERENCES users(user_id)      -- Assuming there is a 'users' table with 'user_id'
        ON DELETE CASCADE
);

CREATE TABLE cart_items (
    cart_item_id SERIAL PRIMARY KEY,     -- Unique identifier for each cart item
    cart_id INT NOT NULL,                -- Associated cart ID
    product_id INT NOT NULL,             -- ID of the product added to the cart
    quantity INT NOT NULL CHECK (quantity > 0), -- Quantity of the product in the cart
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp when the item was added
    chosen_color VARCHAR(20),
    chosen_size VARCHAR(20),
    CONSTRAINT fk_cart
        FOREIGN KEY(cart_id)
        REFERENCES carts(cart_id)       -- Foreign key to the 'carts' table
        ON DELETE CASCADE,
    CONSTRAINT fk_product
        FOREIGN KEY(product_id)
        REFERENCES products(id) -- Assuming there is a 'products' table with 'product_id'
        ON DELETE CASCADE
);