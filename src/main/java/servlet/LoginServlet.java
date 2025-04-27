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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        // 初始化 UserService（可通过依赖注入框架优化）
        DataSource dataSource = DataSourceConfig.getDataSource();
        UserDao userDao = new UserDaoImpl(dataSource);
        this.userService = new UserService(userDao);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");
        HttpSession session = request.getSession();

        // 1. 验证码校验（伪代码）
        String sessionCaptcha = (String) session.getAttribute("CAPTCHA");
        if (!captcha.equalsIgnoreCase(sessionCaptcha)) {
            request.setAttribute("error", "验证码错误");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        // 2. 用户认证
        User user = userService.authenticate(username, password);

        if (user != null&& !user.isLocked()) {
            // 登录成功时存入 Session（推荐）
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/home.jsp");
        } else {
            User lockedUser = userService.getUserDao().findByUsername(username);
            if(lockedUser==null){
                lockedUser=userService.getUserDao().findByEmail(username);
            }
            if (lockedUser != null && lockedUser.isLocked()) {
                request.setAttribute("error", "账号已被锁定，请联系管理员");
            } else {
                request.setAttribute("error", "用户名或密码错误");
            }
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}