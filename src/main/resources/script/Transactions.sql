

-- Create table for storing transaction information
CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,     -- Unique identifier for the transaction
    cart_id INT NOT NULL,                  -- Associated cart ID
    user_id INT NOT NULL,                  -- User who made the purchase
    total_amount DECIMAL(10, 2) NOT NULL,   -- Total transaction amount
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp when transaction occurred
    payment_method VARCHAR(50),            -- Payment method (e.g., Credit Card, PayPal)
    transaction_status VARCHAR(20) DEFAULT 'pending', -- Status of the transaction (e.g., pending, completed, failed, reviewed)
    shipping_address TEXT,                 -- Shipping address for the transaction
    billing_address TEXT,                  -- Billing address for the transaction
    CONSTRAINT fk_cart_transaction
        FOREIGN KEY(cart_id)
        REFERENCES carts(cart_id)         -- Foreign key to the 'carts' table
        ON DELETE CASCADE,
    CONSTRAINT fk_user_transaction
        FOREIGN KEY(user_id)
        REFERENCES users(user_id)         -- Foreign key to the 'users' table
        ON DELETE CASCADE
);


CREATE INDEX idx_transaction_date ON transactions (transaction_date);
