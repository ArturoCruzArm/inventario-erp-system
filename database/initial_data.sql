-- Initial data for ERP System
USE erp_db;

-- Insert Roles
INSERT INTO roles (name, description, created_by) VALUES
('ROLE_ADMIN', 'System Administrator with full access', 'system'),
('ROLE_MANAGER', 'Manager with administrative privileges', 'system'),
('ROLE_SALES', 'Sales personnel', 'system'),
('ROLE_PURCHASE', 'Purchase personnel', 'system'),
('ROLE_INVENTORY', 'Inventory management personnel', 'system'),
('ROLE_FINANCE', 'Finance and accounting personnel', 'system'),
('ROLE_USER', 'Basic user with limited access', 'system');

-- Insert Permissions
INSERT INTO permissions (name, description, module, created_by) VALUES
-- User Management
('USER_CREATE', 'Create new users', 'USER_MANAGEMENT', 'system'),
('USER_READ', 'View user information', 'USER_MANAGEMENT', 'system'),
('USER_UPDATE', 'Update user information', 'USER_MANAGEMENT', 'system'),
('USER_DELETE', 'Delete users', 'USER_MANAGEMENT', 'system'),

-- Customer Management
('CUSTOMER_CREATE', 'Create new customers', 'CUSTOMER_MANAGEMENT', 'system'),
('CUSTOMER_READ', 'View customer information', 'CUSTOMER_MANAGEMENT', 'system'),
('CUSTOMER_UPDATE', 'Update customer information', 'CUSTOMER_MANAGEMENT', 'system'),
('CUSTOMER_DELETE', 'Delete customers', 'CUSTOMER_MANAGEMENT', 'system'),

-- Supplier Management
('SUPPLIER_CREATE', 'Create new suppliers', 'SUPPLIER_MANAGEMENT', 'system'),
('SUPPLIER_READ', 'View supplier information', 'SUPPLIER_MANAGEMENT', 'system'),
('SUPPLIER_UPDATE', 'Update supplier information', 'SUPPLIER_MANAGEMENT', 'system'),
('SUPPLIER_DELETE', 'Delete suppliers', 'SUPPLIER_MANAGEMENT', 'system'),

-- Product Management
('PRODUCT_CREATE', 'Create new products', 'PRODUCT_MANAGEMENT', 'system'),
('PRODUCT_READ', 'View product information', 'PRODUCT_MANAGEMENT', 'system'),
('PRODUCT_UPDATE', 'Update product information', 'PRODUCT_MANAGEMENT', 'system'),
('PRODUCT_DELETE', 'Delete products', 'PRODUCT_MANAGEMENT', 'system'),

-- Inventory Management
('INVENTORY_READ', 'View inventory information', 'INVENTORY_MANAGEMENT', 'system'),
('INVENTORY_UPDATE', 'Update inventory levels', 'INVENTORY_MANAGEMENT', 'system'),
('INVENTORY_ADJUST', 'Adjust inventory levels', 'INVENTORY_MANAGEMENT', 'system'),

-- Sales Management
('SALES_CREATE', 'Create sales orders', 'SALES_MANAGEMENT', 'system'),
('SALES_READ', 'View sales information', 'SALES_MANAGEMENT', 'system'),
('SALES_UPDATE', 'Update sales orders', 'SALES_MANAGEMENT', 'system'),
('SALES_DELETE', 'Delete sales orders', 'SALES_MANAGEMENT', 'system'),

-- Purchase Management
('PURCHASE_CREATE', 'Create purchase orders', 'PURCHASE_MANAGEMENT', 'system'),
('PURCHASE_READ', 'View purchase information', 'PURCHASE_MANAGEMENT', 'system'),
('PURCHASE_UPDATE', 'Update purchase orders', 'PURCHASE_MANAGEMENT', 'system'),
('PURCHASE_DELETE', 'Delete purchase orders', 'PURCHASE_MANAGEMENT', 'system'),

-- Finance Management
('FINANCE_READ', 'View financial information', 'FINANCE_MANAGEMENT', 'system'),
('INVOICE_CREATE', 'Create invoices', 'FINANCE_MANAGEMENT', 'system'),
('INVOICE_UPDATE', 'Update invoices', 'FINANCE_MANAGEMENT', 'system'),
('PAYMENT_PROCESS', 'Process payments', 'FINANCE_MANAGEMENT', 'system'),

-- Reports
('REPORTS_VIEW', 'View all reports', 'REPORTS', 'system');

-- Insert default admin user (password: admin123)
INSERT INTO users (username, email, password, first_name, last_name, created_by) VALUES
('admin', 'admin@erpsystem.com', '$2a$10$8EblcKoHONzjLlCJJgb2fOiYnKNJN7cFq5CJG2VVt7LCBDjQUOOFC', 'Admin', 'User', 'system');

-- Insert demo users (password: password123 for all)
INSERT INTO users (username, email, password, first_name, last_name, created_by) VALUES
('manager', 'manager@erpsystem.com', '$2a$10$rOe6BsOlKhIgr7uL5Jnc6u5dV4X9ZOkpJl8r7/4o6RzPqZ8zIRHDK', 'Manager', 'User', 'system'),
('sales', 'sales@erpsystem.com', '$2a$10$rOe6BsOlKhIgr7uL5Jnc6u5dV4X9ZOkpJl8r7/4o6RzPqZ8zIRHDK', 'Sales', 'User', 'system'),
('purchase', 'purchase@erpsystem.com', '$2a$10$rOe6BsOlKhIgr7uL5Jnc6u5dV4X9ZOkpJl8r7/4o6RzPqZ8zIRHDK', 'Purchase', 'User', 'system'),
('inventory', 'inventory@erpsystem.com', '$2a$10$rOe6BsOlKhIgr7uL5Jnc6u5dV4X9ZOkpJl8r7/4o6RzPqZ8zIRHDK', 'Inventory', 'User', 'system'),
('finance', 'finance@erpsystem.com', '$2a$10$rOe6BsOlKhIgr7uL5Jnc6u5dV4X9ZOkpJl8r7/4o6RzPqZ8zIRHDK', 'Finance', 'User', 'system');

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- admin -> ROLE_ADMIN
(2, 2), -- manager -> ROLE_MANAGER
(3, 3), -- sales -> ROLE_SALES
(4, 4), -- purchase -> ROLE_PURCHASE
(5, 5), -- inventory -> ROLE_INVENTORY
(6, 6); -- finance -> ROLE_FINANCE

-- Assign permissions to roles (Admin gets all permissions)
INSERT INTO role_permissions (role_id, permission_id)
SELECT 1, id FROM permissions; -- Admin gets all permissions

-- Manager gets most permissions except user management
INSERT INTO role_permissions (role_id, permission_id)
SELECT 2, id FROM permissions WHERE module != 'USER_MANAGEMENT';

-- Sales role permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, id FROM permissions WHERE module IN ('CUSTOMER_MANAGEMENT', 'PRODUCT_MANAGEMENT', 'SALES_MANAGEMENT', 'INVENTORY_MANAGEMENT') AND name LIKE '%READ%';

INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, id FROM permissions WHERE module = 'SALES_MANAGEMENT';

INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, id FROM permissions WHERE module = 'CUSTOMER_MANAGEMENT';

-- Purchase role permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 4, id FROM permissions WHERE module IN ('SUPPLIER_MANAGEMENT', 'PRODUCT_MANAGEMENT', 'PURCHASE_MANAGEMENT', 'INVENTORY_MANAGEMENT') AND name LIKE '%READ%';

INSERT INTO role_permissions (role_id, permission_id)
SELECT 4, id FROM permissions WHERE module = 'PURCHASE_MANAGEMENT';

INSERT INTO role_permissions (role_id, permission_id)
SELECT 4, id FROM permissions WHERE module = 'SUPPLIER_MANAGEMENT';

-- Inventory role permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 5, id FROM permissions WHERE module IN ('PRODUCT_MANAGEMENT', 'INVENTORY_MANAGEMENT');

-- Finance role permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 6, id FROM permissions WHERE module = 'FINANCE_MANAGEMENT';

INSERT INTO role_permissions (role_id, permission_id)
SELECT 6, id FROM permissions WHERE module IN ('CUSTOMER_MANAGEMENT', 'SALES_MANAGEMENT') AND name LIKE '%READ%';

-- Insert sample product categories
INSERT INTO product_categories (name, description, created_by) VALUES
('Electronics', 'Electronic products and accessories', 'system'),
('Office Supplies', 'Office stationery and supplies', 'system'),
('Furniture', 'Office and home furniture', 'system'),
('Software', 'Software products and licenses', 'system'),
('Hardware', 'Computer hardware and components', 'system');

-- Insert sample customers
INSERT INTO customers (customer_code, company_name, contact_person, email, phone, customer_type, credit_limit, created_by) VALUES
('CUST000001', 'ABC Corporation', 'John Smith', 'john@abccorp.com', '+1-555-0101', 'WHOLESALE', 50000.00, 'system'),
('CUST000002', 'XYZ Ltd', 'Jane Doe', 'jane@xyzltd.com', '+1-555-0102', 'REGULAR', 25000.00, 'system'),
('CUST000003', 'Tech Solutions Inc', 'Mike Johnson', 'mike@techsolutions.com', '+1-555-0103', 'VIP', 100000.00, 'system'),
('CUST000004', 'Retail Store Co', 'Sarah Wilson', 'sarah@retailstore.com', '+1-555-0104', 'RETAIL', 15000.00, 'system');

-- Insert sample suppliers
INSERT INTO suppliers (supplier_code, company_name, contact_person, email, phone, payment_terms, created_by) VALUES
('SUPP000001', 'Global Electronics Supply', 'David Brown', 'david@globalsupply.com', '+1-555-0201', 30, 'system'),
('SUPP000002', 'Office Depot Wholesale', 'Lisa Garcia', 'lisa@officedepot.com', '+1-555-0202', 45, 'system'),
('SUPP000003', 'Furniture Direct', 'Robert Lee', 'robert@furnituredirect.com', '+1-555-0203', 60, 'system'),
('SUPP000004', 'Software Licensing Corp', 'Maria Rodriguez', 'maria@softlicense.com', '+1-555-0204', 15, 'system');

-- Insert sample products
INSERT INTO products (product_code, name, description, category_id, unit, purchase_price, selling_price, minimum_stock, maximum_stock, reorder_level, created_by) VALUES
('PROD000001', 'Laptop Computer', 'High-performance business laptop', 1, 'PC', 800.00, 1200.00, 5, 50, 10, 'system'),
('PROD000002', 'Office Chair', 'Ergonomic office chair with lumbar support', 3, 'PC', 150.00, 250.00, 10, 100, 20, 'system'),
('PROD000003', 'Printer Paper', 'A4 size white printer paper - 500 sheets', 2, 'REAM', 5.00, 8.00, 50, 500, 100, 'system'),
('PROD000004', 'Wireless Mouse', 'Bluetooth wireless optical mouse', 1, 'PC', 25.00, 45.00, 20, 200, 40, 'system'),
('PROD000005', 'Office Desk', 'Modern office desk with drawers', 3, 'PC', 200.00, 350.00, 5, 30, 10, 'system');

-- Insert initial inventory (stock in)
INSERT INTO inventory_movements (product_id, movement_type, quantity, unit_cost, total_cost, reference_number, reference_type, notes, created_by) VALUES
(1, 'IN', 25, 800.00, 20000.00, 'INIT001', 'INITIAL_STOCK', 'Initial stock entry', 'system'),
(2, 'IN', 50, 150.00, 7500.00, 'INIT001', 'INITIAL_STOCK', 'Initial stock entry', 'system'),
(3, 'IN', 200, 5.00, 1000.00, 'INIT001', 'INITIAL_STOCK', 'Initial stock entry', 'system'),
(4, 'IN', 100, 25.00, 2500.00, 'INIT001', 'INITIAL_STOCK', 'Initial stock entry', 'system'),
(5, 'IN', 15, 200.00, 3000.00, 'INIT001', 'INITIAL_STOCK', 'Initial stock entry', 'system');