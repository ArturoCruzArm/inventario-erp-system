-- Script para debuggear problemas de login
USE erp_db;

-- 1. Verificar usuarios en la base de datos
SELECT 
    id, username, email, enabled, active, 
    SUBSTRING(password, 1, 10) as password_start,
    created_date
FROM users 
ORDER BY id;

-- 2. Verificar roles
SELECT id, name, description, active FROM roles ORDER BY id;

-- 3. Verificar relación user_roles
SELECT 
    ur.user_id, 
    u.username, 
    ur.role_id, 
    r.name as role_name
FROM user_roles ur
JOIN users u ON ur.user_id = u.id
JOIN roles r ON ur.role_id = r.id
ORDER BY ur.user_id;

-- 4. Verificar usuario admin específicamente
SELECT 
    u.id, u.username, u.enabled, u.active, 
    SUBSTRING(u.password, 1, 20) as password_hash,
    GROUP_CONCAT(r.name) as roles
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
WHERE u.username = 'admin'
GROUP BY u.id;

-- 5. Verificar si hay problemas de NULL en campos críticos
SELECT 
    username,
    CASE WHEN enabled IS NULL THEN 'NULL' ELSE CAST(enabled AS CHAR) END as enabled_status,
    CASE WHEN active IS NULL THEN 'NULL' ELSE CAST(active AS CHAR) END as active_status
FROM users
WHERE enabled IS NULL OR active IS NULL OR enabled = 0 OR active = 0;