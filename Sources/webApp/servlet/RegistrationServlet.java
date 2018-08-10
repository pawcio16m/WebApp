package webApp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webApp.database.DatabaseConnection;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseConnection databaseConnection;
       
    public RegistrationServlet() {
        super();        
        databaseConnection = new DatabaseConnection();
    }
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
    	doPost(request, response); // TODO Auto-generated constructor stub }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String login = request.getParameter("login");
		String emailAddress = request.getParameter("emailAddress");
		String password = request.getParameter("password").toString();
		
		if (databaseConnection.registeredUsersTable.insertUser(login, emailAddress, password)) {
			out.println("You are sucessfully registered");
		}
	}

}
