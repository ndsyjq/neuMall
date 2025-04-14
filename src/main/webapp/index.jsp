<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>东软商城 - 登录/注册</title>
    <!-- Bootstrap 5 CSS -->
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- 自定义样式 -->

    <script>
        function openForgotPasswordPopup() {
            document.getElementById('overlay').style.display = 'block';
            document.getElementById('forgotPasswordPopup').style.display = 'block';
        }

        function closeForgotPasswordPopup() {
            document.getElementById('overlay').style.display = 'none';
            document.getElementById('forgotPasswordPopup').style.display = 'none';
        }
        // 验证码刷新功能
        function refreshCaptcha() {
            const captchaImg = document.getElementById('captchaImage');
            // 添加时间戳防止浏览器缓存
            captchaImg.src = '${pageContext.request.contextPath}/captcha?t=' + Date.now();
        }

        
        // 自动刷新验证码（如果有错误）

</script>
    <style>
        /* 弹窗样式 */
        .popup {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: white;
            padding: 20px;
            border: 1px solid #ccc;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            z-index: 1000;
        }

        /* 遮罩层样式 */
        .overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 999;
        }

        /* 关闭按钮样式 */
        .close {
            position: absolute;
            top: 10px;
            right: 10px;
            cursor: pointer;
        }
        .auth-container {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            min-height: 100vh;
        }
        .auth-card {
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
        }
        .brand-logo {
            width: 120px;
            margin-bottom: 2rem;
        }
        .captcha-img {
            cursor: pointer;
            border: 1px solid #dee2e6;
            border-radius: 4px;
            height: 38px;
        }
        .captcha-refresh {
            font-size: 0.8rem;
            color: #0d6efd;
            cursor: pointer;
        }

    </style>
</head>
<body>
<!-- 错误信息模态框 -->
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
<div class="container-fluid auth-container">
    <div class="row justify-content-center align-items-center">
        <div class="col-md-8 col-lg-6">
            <div class="auth-card bg-white p-5 my-5">
                <!-- 品牌LOGO和标题 -->
                <div class="text-center mb-4">
                    <img src="image/neumall logo.png" alt="东软商城" class="brand-logo">
                    <h2 class="text-primary">欢迎来到东软商城</h2>
                    <p class="text-muted">请登录或注册您的账户</p>
                </div>

                <!-- 错误提示 -->


                <!-- 导航选项卡 -->
                <ul class="nav nav-pills nav-justified mb-4" id="authTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="login-tab" data-bs-toggle="tab"
                                data-bs-target="#login" type="button">用户登录</button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="register-tab" data-bs-toggle="tab"
                                data-bs-target="#register" type="button">新用户注册</button>
                    </li>
                </ul>

                <!-- 选项卡内容 -->
                <div class="tab-content">
                    <!-- 登录表单 -->
                    <div class="tab-pane fade show active" id="login" role="tabpanel">
                        <form action="${pageContext.request.contextPath}/login" method="post">
                            <div class="mb-3">
                                <label for="loginUsername" class="form-label">用户名/邮箱</label>
                                <input type="text" class="form-control" id="loginUsername"
                                       name="username" required>
                            </div>
                            <div class="mb-3">
                                <label for="loginPassword" class="form-label">密码</label>
                                <input type="password" class="form-control" id="loginPassword"
                                       name="password" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">验证码</label>
                                <div class="row g-2">
                                    <div class="col-6">
                                        <input type="text" class="form-control"
                                               name="captcha" placeholder="输入验证码"
                                               required maxlength="4">
                                    </div>
                                    <div class="col-6">
                                        <div class="d-flex align-items-center h-100">
                                            <img id="captchaImage"
                                                 src="${pageContext.request.contextPath}/captcha"
                                                 class="captcha-img flex-grow-1"
                                                 onclick="refreshCaptcha()" alt="验证码">
                                            <span class="captcha-refresh ms-2"
                                                  onclick="refreshCaptcha()">换一张</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">立即登录</button>
                            </div>
                        </form>
                        <button type="button" class="btn btn-link mt-2" data-bs-toggle="modal"
                                data-bs-target="#forgotPasswordModal">
                            忘记密码?
                        </button>
                      <div class="modal fade" id="forgotPasswordModal" tabindex="-1" aria-labelledby="forgotPasswordModalLabel"
                           aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="forgotPasswordModalLabel">忘记密码</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <% if (request.getAttribute("resetError") != null) { %>
                        <div class="alert alert-danger" role="alert">
                            <%= request.getAttribute("resetError") %>
                        </div>
                    <% } %>
                    <form action="resetPassword" method="post">
                        <div class="mb-3">
                            <label for="account" class="form-label">账号（用户名或邮箱）</label>
                            <input type="text" class="form-control" id="account" name="account" required>
                        </div>
                        <div class="mb-3">
                            <label for="newPassword" class="form-label">新密码</label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                        </div>
                        <button type="submit" class="btn btn-primary">重置密码</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
                    <!-- 注册表单 -->
                    <div class="tab-pane fade" id="register" role="tabpanel">
                        <form action="${pageContext.request.contextPath}/register" method="post">
                        <div class="mb-3">
                            <label for="username" class="form-label">用户名</label>
                            <input type="text" class="form-control" id="username" name="username" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">邮箱</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">密码</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">确认密码</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                        </div>
                        <div class="mb-3">
                            <label for="phone" class="form-label">手机号</label>
                            <input type="tel" class="form-control" id="phone" name="phone" pattern="[0-9]{11}">
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">立即注册</button>
                        </div>
                    </form>
                    </div>
                </div>
                <!-- 引入 Bootstrap JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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
                <!-- 页脚 -->
                <div class="text-center mt-4">
                    <p class="text-muted">© 2025 奎氏集团 保留所有权利</p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 依赖脚本 -->
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

<!-- 自定义脚本 -->

</body>
</html>