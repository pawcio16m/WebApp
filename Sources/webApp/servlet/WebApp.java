package webApp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webApp.database.DatabaseConnection;

/**
 * Servlet implementation class WebApp
 */
@WebServlet("/jsp/WebApp")
public class WebApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public WebApp() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		DatabaseConnection databaseConnection = new DatabaseConnection();
		
		if (request.getParameter("login") != null) {
			response.sendRedirect("login.jsp");	
		}
		else if (request.getParameter("register") != null) {
			response.sendRedirect("registration.jsp");	
		}
		else if (request.getParameter("logout") != null) {
		    RequestDispatcher rd = request.getRequestDispatcher("/LogoutServlet");
		    rd.forward(request, response);
		}
		else if (request.getParameter("printUsers") != null) {
			String registeredUserTable = databaseConnection.registeredUsersTable.toHtml();
			System.out.print(databaseConnection.registeredUsersTable.toString());
			request.setAttribute("registeredUserTable", registeredUserTable);
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		else if (request.getParameter("deleteUsers") != null) {
			if (databaseConnection.registeredUsersTable.removeAllRegisteredUsers()) {
				System.out.println("All users deleted");
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
		}
		
	}

}
