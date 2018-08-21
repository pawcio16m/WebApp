package webApp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public LogoutServlet()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		boolean isSessionDestroyed = false;
		boolean isCookieRemoved = false; 
		
		String login = null;
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			if (session.getAttribute(LoginServlet.SESSION_ATRIBUTE) != null)
			{
				session.removeAttribute(LoginServlet.SESSION_ATRIBUTE);
				session.invalidate();	
				isSessionDestroyed = true;
				System.out.println("Session terminated.");
			}
		}
		
		Cookie loginCookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
		   for (Cookie cookie : cookies)
		   {
		      if (cookie.getName().equals(LoginServlet.SESSION_ATRIBUTE))
		      {
		         loginCookie = cookie;
		         login = loginCookie.getValue();
		         break;
		      }
		   }
		}
		if (loginCookie != null)
		{
		   loginCookie.setMaxAge(0);
		   response.addCookie(loginCookie);
		   isCookieRemoved = true;
		   System.out.println(LoginServlet.SESSION_ATRIBUTE+ " cookie removed.");
		}
		
		if (isCookieRemoved && isSessionDestroyed)
		{
			System.out.println(login+ ", you are successfuly logged out.");
		}
		response.sendRedirect("home.jsp");
	}

}
