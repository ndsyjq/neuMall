-- V4__Add_role_table.sql
CREATE TABLE roles (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       role_name VARCHAR(50) UNIQUE NOT NULL, -- 角色名称（如ROLE_ADMIN, ROLE_SELLER）
                       description VARCHAR(200) -- 角色描述
);

-- 初始化默认角色
INSERT INTO roles (role_name, description) VALUES
                                               ('ROLE_ADMIN', '商城管理员，拥有所有用户管理权限'),
                                               ('ROLE_SELLER', '普通商户，仅能编辑自己的数据');