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

import javax.sql.DataSource;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);
    private UserService userService;

    @Override
    public void init() {
        try {
            // 初始化 UserService
            DataSource dataSource = DataSourceConfig.getDataSource();
            UserDao userDao = new UserDaoImpl(dataSource);
            userService = new UserService(userDao);
            logger.info("RegisterServlet initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize RegisterServlet", e);
            throw new RuntimeException("Failed to initialize RegisterServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. 获取请求参数
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");

        // 2. 服务端验证
        try {
            // 2.1 检查密码是否匹配
            if (!password.equals(confirmPassword)) {
                throw new IllegalArgumentException("密码和确认密码不一致");
            }

            // 2.2 创建用户对象
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password); // 密码将在Service中加密
            user.setPhone(phone);

            // 2.3 调用Service注册用户
            userService.createUser(user);

            // 3. 注册成功，重定向到登录页
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            logger.info("User registered successfully: {}", username);
        } catch (Exception e) {
            // 4. 处理错误
            request.setAttribute("errorMsg", e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            logger.error("Failed to register user: {}", username, e);
        }
    }
}