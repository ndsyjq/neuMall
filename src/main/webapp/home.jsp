<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户管理系统</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <style>
        /* 自定义样式 */
        .bg-light-blue {
            background-color: #e3f2fd;
        }
        .table-container {
            min-height: 400px;
        }
    </style>
</head>
<body>

<!-- 导航栏 -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home.jsp">NeuMall 管理后台</a>
        <div class="navbar-nav">
            <a class="nav-link" href="${pageContext.request.contextPath}/userManagement?action=list">用户管理</a>
            <jsp:useBean id="user" scope="session" type="DAO.entity.User"/>
            <c:if test="${not empty user && user.admin}">
                <a class="nav-link" href="${pageContext.request.contextPath}/addUser.jsp">添加用户</a>
            </c:if>
            <div class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    ${user.nickname != null ? user.nickname : user.username}
                </a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">退出登录</a></li>
                </ul>
            </div>
        </div>
    </div>
</nav>

<!-- 主内容容器 -->
<div class="container mt-4 table-container">
    <!-- 搜索栏 & 添加按钮 -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div class="input-group w-50">
            <form action="${pageContext.request.contextPath}/userManagement" method="get">
                <input type="text" name="keyword" class="form-control" placeholder="搜索用户名或昵称">
                <button class="btn btn-primary" type="submit">搜索</button>
            </form>
        </div>
        <c:if test="${user.admin}">
            <a href="${pageContext.request.contextPath}/addUser.jsp" class="btn btn-success">新建用户</a>
        </c:if>
    </div>

    <!-- 用户列表 -->
    <div class="card">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">用户列表</h4>
        </div>
        <div class="card-body">
            <table class="table table-striped table-hover table-bordered">
                <thead>
                    <tr>
                        <th>用户名</th>
                        <th>邮箱</th>
                        <th>手机号</th>
                        <th>昵称</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="userItem">
                        <tr>
                            <td>${userItem.username}</td>
                            <td>${userItem.email}</td>
                            <td>${userItem.phone}</td>
                            <td>${userItem.nickname}</td>
                            <td>
                                <span class="badge ${userItem.locked ? 'bg-danger' : 'bg-success'}">
                                        ${userItem.locked ? '已锁定' : '正常'}
                                </span>
                            </td>
                            <td>
                                <c:if test="${user.admin}">
                                    <!-- 原编辑按钮可能缺少链接或参数，修改为以下形式 -->
                                        <a href="${pageContext.request.contextPath}/userManagement?action=edit&id=${userItem.id}"
                                       class="btn btn-sm btn-primary me-1">
                                        <i class="bi bi-pencil-square"></i> 编辑
                                    </a>
                                    <a href="${pageContext.request.contextPath}/userManagement?action=delete&id=${userItem.id}"
                                       class="btn btn-sm btn-danger"
                                       onclick="return confirm('确定要删除该用户吗？')">
                                        <i class="bi bi-trash3"></i> 删除
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<!-- 可选：Bootstrap 图标库 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</body>
</html>