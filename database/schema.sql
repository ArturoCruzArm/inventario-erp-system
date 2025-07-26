-- ERP System Database Schema
-- MySQL Database Schema for ERP System

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS erp_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE erp_db;

-- Drop existing tables (for clean setup)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS role_permissions;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS sales_order_items;
DROP TABLE IF EXISTS sales_orders;
DROP TABLE IF EXISTS purchase_order_items;
DROP TABLE IF EXISTS purchase_orders;
DROP TABLE IF EXISTS inventory_movements;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_categories;
DROP TABLE IF EXISTS supplier_addresses;
DROP TABLE IF EXISTS suppliers;
DROP TABLE IF EXISTS customer_addresses;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS permissions;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS = 1;

-- Users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    enabled BOOLEAN DEFAULT TRUE,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    INDEX idx_username (username),
    INDEX idx_email (email)
);

-- Roles table
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name ENUM('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALES', 'ROLE_PURCHASE', 'ROLE_INVENTORY', 'ROLE_FINANCE', 'ROLE_USER') UNIQUE NOT NULL,
    description VARCHAR(200),
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100)
);

-- Permissions table
CREATE TABLE permissions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(200),
    module VARCHAR(100),
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100)
);

-- User-Role relationship
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Role-Permission relationship
CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- Customers table
CREATE TABLE customers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_code VARCHAR(100) UNIQUE NOT NULL,
    company_name VARCHAR(200) NOT NULL,
    contact_person VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    mobile VARCHAR(20),
    fax VARCHAR(20),
    website VARCHAR(100),
    tax_number VARCHAR(50),
    customer_type ENUM('REGULAR', 'VIP', 'WHOLESALE', 'RETAIL') DEFAULT 'REGULAR',
    credit_limit DECIMAL(12,2) DEFAULT 0.00,
    credit_days INT DEFAULT 30,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    INDEX idx_customer_code (customer_code),
    INDEX idx_company_name (company_name)
);

-- Customer addresses table
CREATE TABLE customer_addresses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    address_type ENUM('BILLING', 'SHIPPING', 'BOTH') NOT NULL,
    address_line1 VARCHAR(200),
    address_line2 VARCHAR(200),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    is_default BOOLEAN DEFAULT FALSE,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- Suppliers table
CREATE TABLE suppliers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    supplier_code VARCHAR(100) UNIQUE NOT NULL,
    company_name VARCHAR(200) NOT NULL,
    contact_person VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    mobile VARCHAR(20),
    fax VARCHAR(20),
    website VARCHAR(100),
    tax_number VARCHAR(50),
    payment_terms INT DEFAULT 30,
    notes TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    INDEX idx_supplier_code (supplier_code),
    INDEX idx_company_name (company_name)
);

-- Supplier addresses table
CREATE TABLE supplier_addresses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    supplier_id BIGINT NOT NULL,
    address_type ENUM('BILLING', 'SHIPPING', 'BOTH') NOT NULL,
    address_line1 VARCHAR(200),
    address_line2 VARCHAR(200),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    is_default BOOLEAN DEFAULT FALSE,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE
);

-- Product categories table
CREATE TABLE product_categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    parent_id BIGINT,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (parent_id) REFERENCES product_categories(id) ON DELETE SET NULL,
    INDEX idx_name (name),
    INDEX idx_parent (parent_id)
);

-- Products table
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_code VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    category_id BIGINT,
    unit VARCHAR(50),
    purchase_price DECIMAL(10,2) DEFAULT 0.00,
    selling_price DECIMAL(10,2) DEFAULT 0.00,
    minimum_stock INT DEFAULT 0,
    maximum_stock INT DEFAULT 0,
    reorder_level INT DEFAULT 0,
    image_url VARCHAR(200),
    barcode VARCHAR(100),
    taxable BOOLEAN DEFAULT TRUE,
    tax_rate DECIMAL(5,2) DEFAULT 0.00,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (category_id) REFERENCES product_categories(id) ON DELETE SET NULL,
    INDEX idx_product_code (product_code),
    INDEX idx_name (name),
    INDEX idx_barcode (barcode)
);

-- Inventory movements table
CREATE TABLE inventory_movements (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    movement_type ENUM('IN', 'OUT', 'ADJUSTMENT', 'TRANSFER', 'RETURN') NOT NULL,
    quantity INT NOT NULL,
    unit_cost DECIMAL(10,2),
    total_cost DECIMAL(10,2),
    reference_number VARCHAR(100),
    reference_type VARCHAR(50),
    notes TEXT,
    movement_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    INDEX idx_product (product_id),
    INDEX idx_movement_date (movement_date),
    INDEX idx_reference (reference_number, reference_type)
);

-- Purchase orders table
CREATE TABLE purchase_orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(100) UNIQUE NOT NULL,
    supplier_id BIGINT NOT NULL,
    order_date DATE NOT NULL,
    expected_date DATE,
    status ENUM('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
    subtotal DECIMAL(12,2) DEFAULT 0.00,
    tax_amount DECIMAL(10,2) DEFAULT 0.00,
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    total_amount DECIMAL(12,2) DEFAULT 0.00,
    notes TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE RESTRICT,
    INDEX idx_order_number (order_number),
    INDEX idx_order_date (order_date),
    INDEX idx_status (status)
);

-- Purchase order items table
CREATE TABLE purchase_order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    purchase_order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    discount_percentage DECIMAL(5,2) DEFAULT 0.00,
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    tax_rate DECIMAL(5,2) DEFAULT 0.00,
    tax_amount DECIMAL(10,2) DEFAULT 0.00,
    total_amount DECIMAL(10,2) DEFAULT 0.00,
    received_quantity INT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT
);

-- Sales orders table
CREATE TABLE sales_orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(100) UNIQUE NOT NULL,
    customer_id BIGINT NOT NULL,
    order_date DATE NOT NULL,
    delivery_date DATE,
    status ENUM('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
    subtotal DECIMAL(12,2) DEFAULT 0.00,
    tax_amount DECIMAL(10,2) DEFAULT 0.00,
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    total_amount DECIMAL(12,2) DEFAULT 0.00,
    notes TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE RESTRICT,
    INDEX idx_order_number (order_number),
    INDEX idx_order_date (order_date),
    INDEX idx_status (status)
);

-- Sales order items table
CREATE TABLE sales_order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sales_order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    discount_percentage DECIMAL(5,2) DEFAULT 0.00,
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    tax_rate DECIMAL(5,2) DEFAULT 0.00,
    tax_amount DECIMAL(10,2) DEFAULT 0.00,
    total_amount DECIMAL(10,2) DEFAULT 0.00,
    shipped_quantity INT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (sales_order_id) REFERENCES sales_orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT
);

-- Invoices table
CREATE TABLE invoices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_number VARCHAR(100) UNIQUE NOT NULL,
    customer_id BIGINT NOT NULL,
    sales_order_id BIGINT,
    invoice_date DATE NOT NULL,
    due_date DATE,
    status ENUM('PENDING', 'SENT', 'PAID', 'OVERDUE', 'CANCELLED') DEFAULT 'PENDING',
    subtotal DECIMAL(12,2) DEFAULT 0.00,
    tax_amount DECIMAL(10,2) DEFAULT 0.00,
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    total_amount DECIMAL(12,2) DEFAULT 0.00,
    paid_amount DECIMAL(12,2) DEFAULT 0.00,
    notes TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE RESTRICT,
    FOREIGN KEY (sales_order_id) REFERENCES sales_orders(id) ON DELETE SET NULL,
    INDEX idx_invoice_number (invoice_number),
    INDEX idx_invoice_date (invoice_date),
    INDEX idx_status (status)
);

-- Payments table
CREATE TABLE payments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_number VARCHAR(100) UNIQUE,
    invoice_id BIGINT,
    customer_id BIGINT NOT NULL,
    payment_date DATE NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    payment_method ENUM('CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'BANK_TRANSFER', 'CHECK', 'ONLINE_PAYMENT'),
    reference_number VARCHAR(100),
    notes TEXT,
    status ENUM('PENDING', 'COMPLETED', 'FAILED', 'CANCELLED', 'REFUNDED') DEFAULT 'COMPLETED',
    active BOOLEAN DEFAULT TRUE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100),
    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE SET NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE RESTRICT,
    INDEX idx_payment_number (payment_number),
    INDEX idx_payment_date (payment_date),
    INDEX idx_reference_number (reference_number)
);