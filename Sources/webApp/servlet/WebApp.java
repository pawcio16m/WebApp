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
	public static DatabaseConnection databaseConnection;
    
	public WebApp() {
        super();
        databaseConnection = new DatabaseConnection();
        databaseConnection.createAllTables();
        System.out.println("WebApp created");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	
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
	}

}
