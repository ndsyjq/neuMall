-- 添加新字段到 users 表
ALTER TABLE users
    ADD COLUMN nickname VARCHAR(255) NULL,
    ADD COLUMN gender VARCHAR(10) NULL,
    ADD COLUMN avatar VARCHAR(255) NULL,
    ADD COLUMN is_admin BOOLEAN DEFAULT false;