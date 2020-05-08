package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.http.Cookie;

import entity.ChatUser;

@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends ChatServlet {

    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String) request.getSession().getAttribute("name");
        if (name!=null) {
            ChatUser aUser = activeUsers.get(name);
            if (aUser!=null && aUser.getSessionId().equals((String)request.getSession().getId())) {
                synchronized (activeUsers) {
                    activeUsers.remove(name);
                }
                request.getSession().setAttribute("name", null);
                response.addCookie(new Cookie("sessionId", null));
                response.sendRedirect(response.encodeRedirectURL("/lab8_1/"));
            } else {
                response.addCookie(new Cookie("sessionId", null));
                response.sendRedirect(response.encodeRedirectURL("/lab8_1/view.html"));
            }
        } else {
            response.sendRedirect(response.encodeRedirectURL("/lab8_1/view.html"));
        }
    }
}