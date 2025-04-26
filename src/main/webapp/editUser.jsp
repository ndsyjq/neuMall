<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>编辑用户</title>
    <!-- Bootstrap 5 样式 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            max-width: 600px;
            margin: 50px auto;
        }
        .form-label {
            font-weight: 500;
        }
        .text-danger {
            font-size: 0.95em;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="mb-4">编辑用户信息</h2>

        <!-- 错误信息展示 -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                    ${error}
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/userManagement">
            <!-- 隐藏的action参数 -->
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="id" value="${user.id}">

            <!-- 用户名 -->
            <div class="mb-3">
                <label class="form-label">用户名 <span class="text-danger">*</span></label>
                <input type="text" class="form-control ${errorMessage.username != null ? 'is-invalid' : ''}"
                       name="username" value="${user.username}" required>
                <div class="invalid-feedback">
                    <c:if test="${errorMessage.username != null}">${errorMessage.username}</c:if>
                </div>
            </div>

            <!-- 邮箱 -->
            <div class="mb-3">
                <label class="form-label">邮箱 <span class="text-danger">*</span></label>
                <input type="email" class="form-control ${errorMessage.email != null ? 'is-invalid' : ''}"
                       name="email" value="${user.email}" required>
                <div class="invalid-feedback">
                    <c:if test="${errorMessage.email != null}">${errorMessage.email}</c:if>
                </div>
            </div>

            <!-- 手机号 -->
            <div class="mb-3">
                <label class="form-label">手机号 <span class="text-danger">*</span></label>
                <input type="tel" class="form-control ${errorMessage.phone != null ? 'is-invalid' : ''}"
                       name="phone" value="${user.phone}" pattern="[1][3-9]\d{9}" required>
                <div class="invalid-feedback">
                    <c:if test="${errorMessage.phone != null}">${errorMessage.phone}</c:if>
                </div>
            </div>

            <!-- 性别 -->
            <div class="mb-3">
                <label class="form-label">性别 <span class="text-danger">*</span></label>
                <select class="form-control ${errorMessage.gender != null ? 'is-invalid' : ''}"
                        name="gender" required>
                    <option value="">请选择</option>
                    <option value="男" ${user.gender eq '男' ? 'selected' : ''}>男</option>
                    <option value="女" ${user.gender eq '女' ? 'selected' : ''}>女</option>
                    <option value="其他" ${user.gender eq '其他' ? 'selected' : ''}>其他</option>
                </select>
                <div class="invalid-feedback">
                    <c:if test="${errorMessage.gender != null}">${errorMessage.gender}</c:if>
                </div>
            </div>

            <!-- 昵称 -->
            <div class="mb-3">
                <label class="form-label">昵称</label>
                <input type="text" class="form-control" name="nickname" value="${user.nickname}">
            </div>

            <!-- 头像 -->
            <div class="mb-3">
                <label class="form-label">头像链接</label>
                <input type="text" class="form-control" name="avatar" value="${user.avatar}">
            </div>
            <c:if test="${not hasRole('ROLE_ADMIN')}">
    <!-- 隐藏锁定状态和管理员角色选项 -->
            <input type="hidden" name="locked" value="${user.locked}">
            <input type="hidden" name="isAdmin" value="false">
            </c:if>
            <!-- 管理员权限 -->
            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" name="isAdmin"
                ${user.isAdmin() ? 'checked' : ''}>
                <label class="form-check-label">设为管理员</label>
            </div>

            <!-- 锁定状态 -->
            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" name="locked"
                ${user.isLocked() ? 'checked' : ''}>
                <label class="form-check-label">锁定用户</label>
            </div>

            <!-- 密码（非必填，留空则不修改） -->
            <div class="mb-3">
                <label class="form-label">新密码</label>
                <input type="password" class="form-control ${errorMessage.password != null ? 'is-invalid' : ''}"
                       name="password" placeholder="留空表示不修改密码">
                <small class="form-text text-muted">留空则不修改密码，填写时需至少8位</small>
                <div class="invalid-feedback">
                    <c:if test="${errorMessage.password != null}">${errorMessage.password}</c:if>
                </div>
            </div>

            <!-- 提交按钮 -->
            <button type="submit" class="btn btn-primary w-100">保存修改</button>
        </form>
    </div>

    <!-- Bootstrap 5 脚本 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>