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

-- Additional orders for CANDIDATE TASK #8 (Revenue Calculation)
-- Orders with specific dates for testing date range filtering
INSERT INTO orders (user_id, order_number, total_amount, order_status, order_date) VALUES
-- Q1 2024 - Delivered orders (total: 2500.00)
(1, 'ORD-Q1-001', 500.00, 'DELIVERED', '2024-01-15 10:30:00'),
(1, 'ORD-Q1-002', 750.00, 'DELIVERED', '2024-02-20 14:45:00'),
(2, 'ORD-Q1-003', 1250.00, 'DELIVERED', '2024-03-10 09:15:00'),
-- Q1 2024 - Non-delivered orders (should NOT count in revenue)
(1, 'ORD-Q1-004', 300.00, 'CANCELLED', '2024-02-25 11:00:00'),
(3, 'ORD-Q1-005', 450.00, 'PENDING', '2024-03-28 16:30:00'),

-- Q2 2024 - Delivered orders (total: 3200.00)
(1, 'ORD-Q2-001', 800.00, 'DELIVERED', '2024-04-05 08:00:00'),
(2, 'ORD-Q2-002', 1200.00, 'DELIVERED', '2024-05-15 12:30:00'),
(3, 'ORD-Q2-003', 1200.00, 'DELIVERED', '2024-06-20 17:45:00'),
-- Q2 2024 - Non-delivered orders
(2, 'ORD-Q2-004', 600.00, 'SHIPPED', '2024-05-25 10:15:00'),
(1, 'ORD-Q2-005', 900.00, 'CONFIRMED', '2024-06-30 14:00:00'),

-- Q3 2024 - Delivered orders (total: 4500.00)
(1, 'ORD-Q3-001', 1500.00, 'DELIVERED', '2024-07-10 09:30:00'),
(2, 'ORD-Q3-002', 2000.00, 'DELIVERED', '2024-08-22 11:45:00'),
(3, 'ORD-Q3-003', 1000.00, 'DELIVERED', '2024-09-15 15:00:00'),
-- Q3 2024 - Non-delivered orders
(1, 'ORD-Q3-004', 350.00, 'CANCELLED', '2024-08-05 13:20:00'),

-- Q4 2024 - Delivered orders (total: 5750.00)
(1, 'ORD-Q4-001', 2250.00, 'DELIVERED', '2024-10-08 10:00:00'),
(2, 'ORD-Q4-002', 1750.00, 'DELIVERED', '2024-11-18 14:30:00'),
(3, 'ORD-Q4-003', 1750.00, 'DELIVERED', '2024-12-05 09:45:00'),
    -- Q4 2024 - Non-delivered orders
    (2, 'ORD-Q4-004', 500.00, 'PENDING', '2024-12-20 16:00:00'),
    (3, 'ORD-Q4-005', 800.00, 'SHIPPED', '2024-12-28 11:30:00');

-- Additional orders for CANDIDATE TASK #9 (Recent Orders by User)
-- User 1 (john_doe) has many orders for testing sorting and limiting
INSERT INTO orders (user_id, order_number, total_amount, order_status, order_date) VALUES
-- User 1 orders in 2025 (for testing recent orders)
(1, 'ORD-U1-2025-01', 150.00, 'PENDING', '2025-01-05 09:00:00'),
(1, 'ORD-U1-2025-02', 275.50, 'CONFIRMED', '2025-01-12 14:30:00'),
(1, 'ORD-U1-2025-03', 89.99, 'SHIPPED', '2025-01-20 11:15:00'),
(1, 'ORD-U1-2025-04', 1299.00, 'DELIVERED', '2025-02-01 16:45:00'),
(1, 'ORD-U1-2025-05', 45.00, 'PENDING', '2025-02-10 08:30:00'),
(1, 'ORD-U1-2025-06', 599.99, 'CONFIRMED', '2025-02-18 13:00:00'),
(1, 'ORD-U1-2025-07', 320.00, 'SHIPPED', '2025-03-01 10:20:00'),
(1, 'ORD-U1-2025-08', 175.50, 'DELIVERED', '2025-03-15 15:45:00'),
(1, 'ORD-U1-2025-09', 899.00, 'PENDING', '2025-03-25 09:30:00'),
(1, 'ORD-U1-2025-10', 50.00, 'CANCELLED', '2025-04-01 12:00:00'),
-- User 2 orders in 2025 (for comparison)
(2, 'ORD-U2-2025-01', 199.99, 'PENDING', '2025-01-08 10:00:00'),
(2, 'ORD-U2-2025-02', 450.00, 'DELIVERED', '2025-02-14 14:00:00'),
(2, 'ORD-U2-2025-03', 75.00, 'SHIPPED', '2025-03-20 11:30:00'),
-- User 3 orders in 2025
(3, 'ORD-U3-2025-01', 125.00, 'CONFIRMED', '2025-01-15 09:45:00'),
(3, 'ORD-U3-2025-02', 650.00, 'DELIVERED', '2025-02-28 16:15:00');INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
(1, 1, 1, 999.99),
(1, 3, 1, 89.99),
(2, 4, 1, 199.99),
(2, 2, 3, 29.99),
(3, 1, 1, 999.99);
