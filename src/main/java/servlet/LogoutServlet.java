package servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 处理用户退出登录的Servlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        // 获取当前会话
        HttpSession session = request.getSession(false);
        
        // 如果会话存在，则使其失效
        if (session != null) {
            session.invalidate();
        }
        
        // 重定向到首页
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        // POST请求也执行相同的逻辑
        doGet(request, response);
    }
}