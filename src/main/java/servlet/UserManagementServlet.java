package servlet;

import DAO.UserDao;
import DAO.UserDaoImpl;
import DAO.entity.User;
import Service.UserService;
import flyway.DataSourceConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@WebServlet("/userManagement")
public class UserManagementServlet extends HttpServlet {
    private UserService userService;
    private boolean hasRole(HttpSession session, String roleName) {
        User user = (User) session.getAttribute("user");
        return user != null && user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(roleName));
    }
    @Override
    public void init() {
        DataSource dataSource = DataSourceConfig.getDataSource();
        UserDao userDao = new UserDaoImpl(dataSource);
        this.userService = new UserService(userDao);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // 新增：处理编辑页面加载（GET请求）
        if ("edit".equals(action)) { // 匹配编辑操作
            String idParam = request.getParameter("id");
            if (idParam == null || !idParam.matches("\\d+")) { // 校验ID合法性
                request.setAttribute("error", "无效的用户ID");
                request.getRequestDispatcher("/home.jsp").forward(request, response); // 错误时返回用户列表
                return;
            }
            Long userId = Long.parseLong(idParam);
            User user = userService.getUserDao().getUserById(userId); // 获取用户信息（需新增DAO方法）
            if (user == null) {
                request.setAttribute("error", "用户不存在");
                request.getRequestDispatcher("/home.jsp").forward(request, response);
                return;
            }
            request.setAttribute("user", user); // 将用户信息存入请求
            request.getRequestDispatcher("/editUser.jsp").forward(request, response); // 转发到编辑页面
            return;
        }

        // 原有删除和搜索逻辑（保持不变）
        if (action != null && action.equals("delete")) {
            Long userId = Long.parseLong(request.getParameter("id"));
            userService.getUserDao().deleteUser(userId);
        }

        String keyword = request.getParameter("keyword");
        List<User> users;
        if (keyword != null && !keyword.isEmpty()) {
            users = userService.getUserDao().searchUsers(keyword);
        } else {
            users = userService.getUserDao().getAllUsers();
        }
        request.setAttribute("users", users);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        if (action != null && action.equals("create")) {
            if (!hasRole(session, "ROLE_ADMIN")) { // 非管理员禁止访问
                request.setAttribute("error", "无权限执行此操作");
                request.getRequestDispatcher("/home.jsp").forward(request, response);
                return;
            }
            try {
                User user = new User();
                user.setUsername(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
                user.setEmail(request.getParameter("email"));
                user.setPhone(request.getParameter("phone"));
                user.setNickname(request.getParameter("nickname"));
                user.setGender(request.getParameter("gender"));
                user.setAvatar(request.getParameter("avatar"));
                user.setAdmin(request.getParameter("isAdmin") != null);
                // 假设前端通过参数传递角色 ID（例如管理员角色 ID 为 1）
                List<Long> roleIds = Arrays.asList(1L); // 示例，实际需根据前端逻辑获取
                userService.createUserByAdmin(user, roleIds);
                response.sendRedirect(request.getContextPath() + "/userManagement");
            } catch (Exception e) {
                request.setAttribute("errorMsg", e.getMessage());
                request.getRequestDispatcher("/addUser.jsp").forward(request, response);
            }
        }
         if ("edit".equals(action)) {

            String idParam = request.getParameter("id");
            Long userId = Long.parseLong(idParam);

            // 1. 初始化错误信息容器
            Map<String, String> errorMessage = new HashMap<>();
             if (hasRole(session, "ROLE_SELLER")) { // 普通商户限制
                 User currentUser = (User) session.getAttribute("user");
                 if (!currentUser.getId().equals(userId)) { // 不允许编辑其他用户
                     request.setAttribute("error", "只能编辑自己的信息");
                     request.getRequestDispatcher("/home.jsp").forward(request, response);
                     return;
                 }
                 // 禁止修改锁定状态和管理员角色
                 request.setAttribute("locked", currentUser.isLocked()); // 隐藏锁定字段
                 request.setAttribute("isAdmin", false); // 禁用管理员设置
             }
            // 2. 校验必填字段
            String username = request.getParameter("username");
            if (username == null || username.trim().isEmpty()) {
                errorMessage.put("username", "用户名不能为空");
            }

            String email = request.getParameter("email");
            if (email == null || email.trim().isEmpty()) {
                errorMessage.put("email", "邮箱不能为空");
            } else if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
                errorMessage.put("email", "邮箱格式不正确"); // 新增格式校验
            }

            String phone = request.getParameter("phone");
            if (phone == null || phone.trim().isEmpty() || phone.length() != 11) {
                errorMessage.put("phone", "手机号必须为11位有效号码");
            }

            String gender = request.getParameter("gender");
            if (gender == null || gender.trim().isEmpty()) {
                errorMessage.put("gender", "请选择性别");
            }

            // 3. 校验失败处理
            if (!errorMessage.isEmpty()) {
                request.setAttribute("errorMessage", errorMessage); // 传递错误信息到页面
                request.setAttribute("user", userService.getUserDao().getUserById(userId)); // 回显原始数据
                request.getRequestDispatcher("/editUser.jsp").forward(request, response);
                return;
            }

            // 4. 原有密码处理逻辑（保留）
            String newPassword = request.getParameter("password");
            User existingUser = userService.getUserDao().getUserById(userId);
            String encodedPassword = newPassword.isEmpty() ?
                    existingUser.getPassword() :
                    userService.getPasswordEncoder().encode(newPassword);

            // 5. 填充用户数据（新增字段校验后赋值）
            User user = new User();
            user.setId(userId);
            user.setUsername(username);
            user.setPassword(encodedPassword);
            user.setEmail(email);
            user.setPhone(phone);
            user.setNickname(request.getParameter("nickname"));
            user.setGender(gender);
            user.setAvatar(request.getParameter("avatar"));
            user.setAdmin(request.getParameter("isAdmin") != null);
            user.setLocked("on".equals(request.getParameter("locked")));

            // 6. 调用更新方法
            userService.getUserDao().updateUser(user);
            response.sendRedirect(request.getContextPath() + "/userManagement");
        }
    }
}