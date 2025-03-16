
CREATE TABLE payment_methods (
    id SERIAL PRIMARY KEY,             -- Unique identifier for the paymentMethod
    name VARCHAR(255) NOT NULL,        -- Name of the payment_method
    image VARCHAR(1024),                -- logo
    description TEXT,                  -- Optional description of the category
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- When the category was created
);