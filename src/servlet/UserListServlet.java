package servlet;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import entity.ChatUser;


public class UserListServlet extends ChatServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		PrintWriter pw = response.getWriter();
		Integer count=activeUsers.size();
		pw.println("<!DOCTYPE html>\n<html><head>" +
				"<title></title>"+
				"<meta charset='UTF-8' />" +
				"<meta http-equiv='refresh' content='5'>" +
				"</head>");
		pw.println("<body>");
		pw.println("<style type=\"text/css\">"+
		   "SELECT {"+
		    "width: 100%;" +
		    "}" +
		    " </style>");
		pw.println("<form id=mform action=\"/lab8_1/users.do\" method=\"post\">");
		pw.println("<select size=\""+new Integer(count.intValue()+2).toString()+"\" name=\"userlist\" " +
				"onchange=\"javascript:document.forms['mform'].submit();\">");
		pw.println("<option value = 'toall'>ALL USERS</option>");
		String uname=(String)request.getSession().getAttribute("name");
		for (ChatUser aUser: activeUsers.values()) {
			if(aUser.getName().equals(uname))
				continue;
			pw.println("<option>"+aUser.getName()+"</option>");
		}
		pw.println("</select>");
		pw.println("</form>");
		
		String privatem = (String)request.getSession().getAttribute("privatem");
		boolean b=false;
		for (ChatUser aUser: activeUsers.values()) {
			if(aUser.getName().equals(privatem))
				b=true;
		}
		if(!b){
			privatem="toall";
		}
		String m="ME";


		if(privatem==null || "toall".equals(privatem))
			m+=" All ";
		else
			m+=("\n ->: " + privatem);
		pw.println(m);
		pw.println("</body></html>");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String privatem = (String)request.getParameter("userlist");
		request.getSession().setAttribute("privatem", privatem);
		doGet(request, response);
	}
}