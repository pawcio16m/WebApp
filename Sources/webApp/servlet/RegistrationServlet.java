package webApp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import webApp.backend.RegisteredUser;
import webApp.database.DatabaseConnection;

@WebServlet("/jsp/RegistrationServlet")
public class RegistrationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public RegistrationServlet()
    {
        super();    
    }
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    { 
    	doPost(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=UTF-8");
	
		DatabaseConnection databaseConnection = new DatabaseConnection();
		
		String errorMsg = "";
		String login = request.getParameter("login");
		String emailAddress = request.getParameter("email");
		String password = request.getParameter("password").toString();
		
		System.out.println("User with login: "+login+" and email address: "+emailAddress);

		if (!RegisteredUser.validatePassword(password))
		{
			errorMsg = "Password is incorrect";
		}
		
		if (!RegisteredUser.validateEmail(emailAddress))
		{
			errorMsg = "Email is incorrect";
		}
		
		if (errorMsg.isEmpty())
		{
			if (databaseConnection.registeredUsersTable.insertUser(login, emailAddress, password))
			{
				System.out.println("User: "+login+" added to database.");
				response.sendRedirect("home.jsp");
				return;
			}
			else
			{
				errorMsg = "User with provided login or email already exist.";
			}
		}		
		request.setAttribute("errMessage", errorMsg);
		System.out.println("User: "+login+" not added to database.");
		request.getRequestDispatcher("registration.jsp").forward(request, response);
	}
}
