-- Script para corregir las contraseñas en la base de datos
USE erp_db;

-- Nuevos hashes generados (estos SÍ funcionan)
-- admin123: $2a$10$HVdZoLPCB.EQ8blbqjMiwuvaeFblhY8EUiUM3ffg8pWk2b4i/6jTu
-- password123: $2a$10$Udo4/8OOYLQ1DsxjeovI9.6tupPX0wMUbHwQzjGT.vd0MvQWktO32

-- Actualizar contraseña del admin
UPDATE users SET password = '$2a$10$HVdZoLPCB.EQ8blbqjMiwuvaeFblhY8EUiUM3ffg8pWk2b4i/6jTu' WHERE username = 'admin';

-- Actualizar contraseñas de otros usuarios
UPDATE users SET password = '$2a$10$Udo4/8OOYLQ1DsxjeovI9.6tupPX0wMUbHwQzjGT.vd0MvQWktO32' WHERE username IN ('manager', 'sales', 'purchase', 'inventory', 'finance');

-- Verificar que todos los usuarios tengan enabled y active en TRUE
UPDATE users SET enabled = 1, active = 1 WHERE enabled IS NULL OR active IS NULL OR enabled = 0 OR active = 0;

-- Verificar los cambios
SELECT username, 
       SUBSTRING(password, 1, 20) as password_hash, 
       enabled, 
       active 
FROM users 
ORDER BY id;