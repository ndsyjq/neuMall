-- V2__Add_captcha_audit_table.sql
CREATE TABLE captcha_audit (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               request_ip VARCHAR(45) NOT NULL,
                               captcha_key VARCHAR(50) NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               success BOOLEAN DEFAULT FALSE
);

CREATE INDEX idx_captcha_created ON captcha_audit(created_at);