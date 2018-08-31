package webApp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webApp.backend.ApplicationUtilities;
import webApp.backend.ErrorHandler;
import webApp.backend.ErrorMsgs;
import webApp.backend.RegisteredUser;
import webApp.database.DatabaseConnection;

@WebServlet("/jsp/ProfileFillerServlet")
public class ProfileFillerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProfileFillerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html;charset=UTF-8");
	    
        DatabaseConnection databaseConnection = new DatabaseConnection();
        
        ErrorHandler errorHandler = new ErrorHandler();
        String login =  LoginServlet.getLoginNameFromCookies(request);
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int age = Integer.parseInt(request.getParameter("age"));
        String phoneNumber = request.getParameter("phoneNumber");
        String city = request.getParameter("city");
        String preferedActivity = request.getParameter("preferedActivity");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        
        if (login != null)
        {
            errorHandler.addError(databaseConnection.usersTable.updateUserProfile(login, firstName, lastName, age, phoneNumber, city, preferedActivity));
        }
        
        //TODO fix it maybe create separate function high complexity
        if ( ! (oldPassword.isEmpty() || newPassword.isEmpty()))
        {
            errorHandler.addErrors(ApplicationUtilities.validatePassword(newPassword));            
            if (true == databaseConnection.registeredUsersTable.getRegisteredUser(login).isPasswordCorrect(oldPassword))
            {
                errorHandler.addError(databaseConnection.registeredUsersTable.updatePassword(login, newPassword));
            }
            else
            {
                errorHandler.addError(ErrorMsgs.EMAIL_INCORRECT);      
            }            
        }
        else
        {
            System.out.println("User doesn't provide enough fields  in password form -> password not updated.");            
        }
        
        if (!errorHandler.hasErrorReported())
        {
            response.sendRedirect("home.jsp");
            return;
        }
        
        request.setAttribute("errMessage", errorHandler.toHtml());
        System.out.println("User "+login+" has not updated profile.");
        request.getRequestDispatcher("profileForm.jsp").forward(request, response);
	}

}
