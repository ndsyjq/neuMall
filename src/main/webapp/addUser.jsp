<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加用户 - 管理后台</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <style>
        .page-container {
            max-width: 500px;
            margin: 50px auto;
        }
    </style>
</head>
<body>
    <div class="page-container">
        <h1 class="text-center mb-4">新增用户</h1>

        <!-- 错误提示 -->
        <div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="errorModalLabel">错误提示</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="errorMessage">
                    <!-- 错误信息将显示在这里 -->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
        <form action="${pageContext.request.contextPath}/userManagement" method="post" class="needs-validation" novalidate>
            <input type="hidden" name="action" value="create">

            <!-- 用户名 -->
            <div class="mb-3">
                <label for="username" class="form-label">用户名 <span class="text-danger">*</span></label>
                <input type="text" class="form-control" id="username" name="username" required>
                <div class="invalid-feedback">请填写用户名</div>
            </div>

            <!-- 密码 -->
            <div class="mb-3">
                <label for="password" class="form-label">密码 <span class="text-danger">*</span></label>
                <input type="password" class="form-control" id="password" name="password" required>
                <div class="invalid-feedback">请填写密码</div>
            </div>

            <!-- 邮箱 -->
            <div class="mb-3">
                <label for="email" class="form-label">邮箱 <span class="text-danger">*</span></label>
                <input type="email" class="form-control" id="email" name="email" required>
                <div class="invalid-feedback">请填写有效的邮箱地址</div>
            </div>

            <!-- 手机 -->
            <div class="mb-3">
                <label for="phone" class="form-label">手机 <span class="text-danger">*</span></label>
                <input type="tel" class="form-control" id="phone" name="phone" pattern="[0-9]{11}" required>
                <div class="invalid-feedback">请填写11位手机号</div>
            </div>

            <!-- 昵称 -->
            <div class="mb-3">
                <label for="nickname" class="form-label">昵称</label>
                <input type="text" class="form-control" id="nickname" name="nickname">
            </div>

            <!-- 性别 -->
            <div class="mb-3">
                <label class="form-label">性别</label>
                <select class="form-select" name="gender">
                    <option value="男">男</option>
                    <option value="女">女</option>
                </select>
            </div>

            <!-- 头像地址 -->
            <div class="mb-3">
                <label for="avatar" class="form-label">头像地址</label>
                <input type="url" class="form-control" id="avatar" name="avatar" placeholder="如：https://example.com/avatar.png">
            </div>

            <!-- 管理员权限 -->
            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" id="isAdmin" name="isAdmin" value="true">
                <label class="form-check-label" for="isAdmin">设为管理员</label>
            </div>

            <!-- 提交按钮 -->
            <button type="submit" class="btn btn-primary w-100">立即添加</button>
        </form>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
    <script>
        // 表单验证初始化
        (function () {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.from(forms).forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
        })()
    </script>
    <script>
        function showErrorModal(errorMessage) {
            var modal = new bootstrap.Modal(document.getElementById('errorModal'));
            document.getElementById('errorMessage').textContent = errorMessage;
            modal.show();
        }
        document.addEventListener('DOMContentLoaded', function() {
            <%
                String errorMsg = (String) request.getAttribute("errorMsg");
                String error = (String) request.getAttribute("error");
                if (errorMsg != null) {
                    out.println("showErrorModal('" + errorMsg.replace("'", "\\'") + "');");
                }
                if (error != null) {
                    out.println("showErrorModal('" + error.replace("'", "\\'") + "');");
                }
            %>
        });
    </script>
</body>
</html>
