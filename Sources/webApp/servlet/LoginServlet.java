package webApp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webApp.backend.RegisteredUser;
import webApp.database.DatabaseConnection;

@WebServlet("/jsp/LoginServlet")
public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private int SESSION_DURATION = 60*60; //1h
	public static String SESSION_ATRIBUTE = "login";
       
    public LoginServlet()
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
		
		String login = request.getParameter("login");
		String password = request.getParameter("password").toString();

		boolean isPasswordCorrect = false;
		
		try
		{
			isPasswordCorrect = databaseConnection.registeredUsersTable.getRegisteredUser(login).isPasswordCorrect(password);
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
			isPasswordCorrect = false;			
		}

		if (isPasswordCorrect)
		{
			System.out.println(login+" is sucessfully logged in.");
			
			HttpSession session = request.getSession();
			session.setAttribute(SESSION_ATRIBUTE, login);
			session.setMaxInactiveInterval(SESSION_DURATION);
			
			if (login.equals(RegisteredUser.adminLogin))
			{
				System.out.println("Hello admin, you have expended functionality on this site.");
			}

			Cookie loginCookie = new Cookie(SESSION_ATRIBUTE, login);
			loginCookie.setMaxAge(SESSION_DURATION);
			
			response.addCookie(loginCookie);
		}
		else
		{
			System.out.println(login+ " not logged in, password incorrect");
		}
		response.sendRedirect("home.jsp");
	}

}
