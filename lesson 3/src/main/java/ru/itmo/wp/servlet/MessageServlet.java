package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import ru.itmo.wp.util.ResponseWriteUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageServlet extends HttpServlet {
    List<Message> messagesList = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        response.setContentType("application/json; charset=windows-1251");
        switch (request.getRequestURI()) {
            case "/message/findAll" -> ResponseWriteUtil.writeResponse(new Gson().toJson(messagesList), response);
            case "/message/auth" -> {
                String user = request.getParameter("user");

                if (user != null && !user.trim().isEmpty()) {
                    session.setAttribute("user", user);
                }
                String s = ((String) Objects.requireNonNullElse(session.getAttribute("user"), "")).trim();
                ResponseWriteUtil.writeResponse(new Gson().toJson(s), response);
            }
            case "/message/add" -> {
                if (!request.getParameter("text").trim().isEmpty()) {
                    messagesList.add(new Message((String) session.getAttribute("user"),
                            request.getParameter("text")));
                }
            }
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    static class Message {
        String user;
        String text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }

        public String getUser() {
            return user;
        }

        public String getText() {
            return text;
        }

    }
}
