package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;
import ru.itmo.wp.util.ResponseWriteUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    private static final Random random = new Random();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpSession session = httpRequest.getSession();
        String requestCaptcha = request.getParameter("captcha");
        if (requestCaptcha == null &&
                (session.getAttribute("captcha-passed") != null || !httpRequest.getMethod().equals("GET"))) {
            super.doFilter(request, response, chain);
            return;
        }

        if (checkCaptcha(session, requestCaptcha)) {
            session.setAttribute("captcha-passed", true);
            ((HttpServletResponse) response).sendRedirect(httpRequest.getRequestURI());
            return;
        } else if (session.getAttribute("captcha") == null || requestCaptcha != null) {
            session.setAttribute("captcha", random.nextInt(900) + 100);
            ((HttpServletResponse) response).sendRedirect(httpRequest.getRequestURI());
            return;
        }
        ResponseWriteUtil.writeResponse(getHtmlAnswer((int) session.getAttribute("captcha"), httpRequest),
                response);
    }

    private boolean checkCaptcha(HttpSession session, String requestCaptcha) {
        return  requestCaptcha != null && (session.getAttribute("captcha") != null
                && session.getAttribute("captcha").toString().equals(requestCaptcha.trim()) ||
                session.getAttribute("captcha-passed") != null);
    }

    private String getHtmlAnswer(int captchaValue, HttpServletRequest request) {
        byte[] captchaImage = ImageUtils.toPng(Integer.toString(captchaValue));
        String base64Encode = Base64.getEncoder().encodeToString(captchaImage);
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<img src=\"data:image/png;base64," + base64Encode + "\">\n" +
                "<div class=\"captcha-form\">\n" +
                "            <form action=\"" + request.getRequestURI() + "\" method=\"get\">\n" +
                "                <label for=\"captcha-value\">Enter captcha:</label>\n" +
                "                <input name=\"captcha\" id=\"captcha-value\">\n" +
                "            </form>\n" +
                "        </div>" +
                "</body>\n" +
                "</html>";
    }
}

