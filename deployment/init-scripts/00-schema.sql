CREATE DATABASE IF NOT EXISTS `ms-auth` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ms-auth`;

CREATE TABLE IF NOT EXISTS role_auth (
    id_role BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_auth (
    user_id BIGINT PRIMARY KEY,
    id_number VARCHAR(20) NOT NULL UNIQUE,
    id_type INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    birth_date DATE,
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    base_salary DECIMAL(10, 2),
    id_role BIGINT NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT true,
    FOREIGN KEY (id_role) REFERENCES role_auth(id_role)
);

CREATE INDEX idx_user_auth_email ON user_auth(email);
CREATE INDEX idx_user_auth_idnumber ON user_auth(id_number);
CREATE INDEX idx_user_auth_role ON user_auth(id_role);