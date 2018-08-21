package webApp.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webApp.database.DatabaseConnection;

@WebServlet("/jsp/WebApp")
public class WebApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String LOGIN_PARAMETER = "login";
	public static final String REGISTER_PARAMETER = "register";
	public static final String LOGOUT_PARAMETER = "logout";
	public static final String PRINT_USERS_PARAMETER = "printUsers";
	public static final String DELETE_USERS_PARAMETER = "deleteUsers";
    
	public WebApp() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		DatabaseConnection databaseConnection = new DatabaseConnection();
		
		if (request.getParameter(LOGIN_PARAMETER) != null) {
			response.sendRedirect("login.jsp");	
		}
		else if (request.getParameter(REGISTER_PARAMETER) != null) {
			response.sendRedirect("registration.jsp");	
		}
		else if (request.getParameter(LOGOUT_PARAMETER) != null) {
		    RequestDispatcher rd = request.getRequestDispatcher("/LogoutServlet");
		    rd.forward(request, response);
		}
		else if (request.getParameter(PRINT_USERS_PARAMETER) != null) {
			String registeredUserTable = databaseConnection.registeredUsersTable.toHtml();
			System.out.print(databaseConnection.registeredUsersTable.toString());
			request.setAttribute("registeredUserTable", registeredUserTable);
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		else if (request.getParameter(DELETE_USERS_PARAMETER) != null) {
			if (databaseConnection.registeredUsersTable.removeAllRegisteredUsers()) {
				System.out.println("All users deleted.");
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
		}
		
	}

}
