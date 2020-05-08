package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import entity.ChatMessage;
import entity.ChatUser;

@WebServlet(name = "NewMessageServlet")
public class NewMessageServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pname=null;
        String message = (String)request.getParameter("message");
        if (message!=null && !"".equals(message)) {
            String privatem=(String)request.getSession().getAttribute("privatem");
            if(privatem != null && !"toall".equals(privatem))
                pname=privatem;
            // По имени из сессии получить ссылку на объект ChatUser
            ChatUser author = activeUsers.get((String) request.getSession().getAttribute("name"));
            synchronized (messages) {
                // Добавить в список сообщений новое
                messages.add(new ChatMessage(message, author,
                        Calendar.getInstance().getTimeInMillis(),pname));
            }
        }
        response.sendRedirect("/lab8_1/message.html");
    }
}
