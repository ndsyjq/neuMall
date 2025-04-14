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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordServlet.class);
    private UserService userService;

    @Override
    public void init() {
        try {
            // 初始化 UserService
            DataSource dataSource = DataSourceConfig.getDataSource();
            UserDao userDao = new UserDaoImpl(dataSource);
            userService = new UserService(userDao);
            logger.info("ResetPasswordServlet initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize ResetPasswordServlet", e);
            throw new RuntimeException("Failed to initialize ResetPasswordServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String account = request.getParameter("account");
        String newPassword = request.getParameter("newPassword");

        try {
            // 查找用户
            User user = userService.getUserDao().findByUsername(account);
            if (user == null) {
                user = userService.getUserDao().findByEmail(account);
            }

            if (user == null) {
                request.setAttribute("error", "未找到对应的用户");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return;
            }

            // 加密新密码
            String encodedPassword = userService.getPasswordEncoder().encode(newPassword);
            user.setPassword(encodedPassword);

            // 更新用户密码
            userService.getUserDao().updatePassword(user.getId(), encodedPassword);

            request.setAttribute("message", "密码重置成功，请使用新密码登录。");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "密码重置失败，请稍后重试。");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            logger.error("Failed to reset password for account: {}", account, e);
        }
    }
}