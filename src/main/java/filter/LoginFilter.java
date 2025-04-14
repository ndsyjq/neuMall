package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = "/home.jsp")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // 检查用户是否已登录
        if (session == null || session.getAttribute("user") == null) {
            // 如果未登录，重定向到登录页面
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
        } else {
            // 如果已登录，继续处理请求
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化方法，可留空
    }

    @Override
    public void destroy() {
        // 销毁方法，可留空
    }
}