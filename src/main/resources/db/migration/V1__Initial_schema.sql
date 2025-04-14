-- V1__Initial_schema.sql
CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       phone VARCHAR(20),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       locked BOOLEAN DEFAULT FALSE,
                       login_attempts INT DEFAULT 0
);

CREATE INDEX idx_users_username ON users(username);