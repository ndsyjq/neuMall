<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商城用户管理界面</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>商城用户管理界面</h1>
    <h2>搜索用户</h2>
    <form action="searchUser" method="get">
        <label for="account">账号:</label>
        <input type="text" id="account" name="account">
        <label for="nickname">昵称:</label>
        <input type="text" id="nickname" name="nickname">
        <input type="submit" value="搜索">
    </form>
    <h2>创建新用户</h2>
    <form action="createUser" method="post">
        <label for="newAccount">账号:</label>
        <input type="text" id="newAccount" name="account" required><br>
        <label for="newNickname">昵称:</label>
        <input type="text" id="newNickname" name="nickname" required><br>
        <label for="newPassword">密码:</label>
        <input type="password" id="newPassword" name="password" required><br>
        <label for="newGender">性别:</label>
        <input type="text" id="newGender" name="gender"><br>
        <label for="newAvatar">头像:</label>
        <input type="text" id="newAvatar" name="avatar"><br>
        <label for="newPhone">电话号码:</label>
        <input type="text" id="newPhone" name="phone"><br>
        <label for="newEmail">电子邮件:</label>
        <input type="email" id="newEmail" name="email" required><br>
        <label for="isAdmin">是否超级管理员:</label>
        <input type="checkbox" id="isAdmin" name="isAdmin"><br>
        <input type="submit" value="创建">
    </form>
    <h2>用户列表</h2>
    <table>
        <thead>
            <tr>
                <th>昵称</th>
                <th>注册时间</th>
                <th>账号</th>
                <th>电子邮件地址</th>
                <th>电话号码</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <%
                // 假设这里通过 request.getAttribute 获取用户列表
                java.util.List<DAO.entity.User> userList = (java.util.List<DAO.entity.User>) request.getAttribute("userList");
                if (userList != null) {
                    for (DAO.entity.User user : userList) {
            %>
            <tr>
                <td><%= user.getUsername() %></td>
                <td><%= user.getCreatedAt() %></td>
                <td><%= user.getAccount() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.getPhone() %></td>
                <td>
                    <a href="editUser?id=<%= user.getId() %>">编辑</a>
                    <a href="deleteUser?id=<%= user.getId() %>">删除</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>