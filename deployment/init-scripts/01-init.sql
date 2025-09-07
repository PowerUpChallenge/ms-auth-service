-- Insertar roles
INSERT INTO role_auth (id_role, name, description) VALUES
(1, 'ROLE_ADMIN', 'Administrador del sistema con todos los privilegios'),
(2, 'ROLE_ASESOR', 'Asesor con permisos para gestionar clientes y operaciones'),
(3, 'ROLE_CLIENTE', 'Usuario cliente del sistema');

-- usuario administrador
INSERT INTO user_auth (
    user_id, id_number, id_type, name, lastname, birth_date,
    address, phone, email, base_salary, id_role, password_hash, enabled
) VALUES (
    1,
    '1234567890',
    1,
    'Admin',
    'Sistema',
    '1990-01-01',
    'Calle Principal 123',
    '+1234567890',
    'admin@example.com',
    5000.00,
    1,
    '$2a$10$TFsSP7yxrt82FZLhPCohmOym4y1QpSo.hzmwMXnKJSMZwggcx4AOq',
    true
);

-- usuario asesor
INSERT INTO user_auth (
    user_id, id_number, id_type, name, lastname, birth_date,
    address, phone, email, base_salary, id_role, password_hash, enabled
) VALUES (
    2,
    '0987654321',
    1,
    'Juan',
    'Pérez',
    '1985-05-15',
    'Avenida Central 456',
    '+0987654321',
    'juan.perez@example.com',
    3000.00,
    2,
    '$2a$10$TFsSP7yxrt82FZLhPCohmOym4y1QpSo.hzmwMXnKJSMZwggcx4AOq',
    true
);