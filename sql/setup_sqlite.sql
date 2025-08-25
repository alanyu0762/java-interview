-- Interview Database Setup Script for SQLite
-- Run this script to set up the SQLite database for the candidate interview

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    email TEXT UNIQUE NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    order_number TEXT UNIQUE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_status TEXT DEFAULT 'PENDING' CHECK(order_status IN ('PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED')),
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create products table
CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INTEGER DEFAULT 0,
    category TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Create order_items table
CREATE TABLE IF NOT EXISTS order_items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Insert sample data
INSERT INTO users (username, email, first_name, last_name) VALUES
('john_doe', 'john.doe@email.com', 'John', 'Doe'),
('jane_smith', 'jane.smith@email.com', 'Jane', 'Smith'),
('bob_wilson', 'bob.wilson@email.com', 'Bob', 'Wilson');

INSERT INTO products (name, description, price, stock_quantity, category) VALUES
('Laptop', 'High-performance laptop', 999.99, 50, 'Electronics'),
('Mouse', 'Wireless mouse', 29.99, 100, 'Electronics'),
('Keyboard', 'Mechanical keyboard', 89.99, 75, 'Electronics'),
('Monitor', '24-inch LED monitor', 199.99, 30, 'Electronics');

INSERT INTO orders (user_id, order_number, total_amount, order_status) VALUES
(2, 'ORD-001', 1089.98, 'DELIVERED'),
(2, 'ORD-002', 289.98, 'SHIPPED'),
(3, 'ORD-003', 999.99, 'PENDING');

INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
(1, 1, 1, 999.99),
(1, 3, 1, 89.99),
(2, 4, 1, 199.99),
(2, 2, 3, 29.99),
(3, 1, 1, 999.99);
