package webApp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/jsp/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegistrationServlet() {
        super();    
    }
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
    	doPost(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String login = request.getParameter("login");
		String emailAddress = request.getParameter("email");
		String password = request.getParameter("password").toString();
		
		System.out.println("User["+login+"] "+emailAddress);
		
		if (WebApp.databaseConnection.registeredUsersTable.insertUser(login, emailAddress, password)) {
			System.out.println("User["+login+"] added to database");
		}
		else
		{
			System.out.println("User["+login+"] not added to database");
		}
		response.sendRedirect("home.jsp");
	}

}
