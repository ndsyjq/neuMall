package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/captcha")
public class CaptchaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 生成随机4位验证码
        String captcha = RandomStringUtils.randomNumeric(4);

        // 存储到 Session
        HttpSession session = request.getSession();
        session.setAttribute("CAPTCHA", captcha);

        // 生成图片（示例使用简单文本）
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            BufferedImage image = new BufferedImage(100, 30, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 100, 30);
            g.setColor(Color.BLACK);
            g.drawString(captcha, 20, 20);
            ImageIO.write(image, "PNG", out);
        }
    }
}