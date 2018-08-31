package webApp.Test.servlet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import webApp.backend.RegisteredUser;
import webApp.database.DatabaseConnection;
import webApp.database.RegisteredUsersDatabaseTable;
import webApp.servlet.LoginServlet;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTestSuite
{
    private final int USER_ID = 1;
    private final String LOGIN = "myLogin"; 
    private final String EMAIL_ADDRESS = "myLogin@at.com"; 
    private final String PASSWORD = "myPassword"; 
  //  private final String UPDATED_PASSWORD = "myUpdatedPassword"; 
    private final RegisteredUser REGISTERED_USER = new RegisteredUser(USER_ID, LOGIN, EMAIL_ADDRESS, PASSWORD);
    
    @Mock
    HttpServletRequest requestMock;
    
    @Mock
    HttpServletResponse responseMock;
    
    @Mock
    DatabaseConnection databaseMock;
    
    @Mock
    RegisteredUsersDatabaseTable resgisteredUsersDatabaseMock;
    
    @Mock
    HttpSession sessionMock;
   
    private LoginServlet sut;
    
    @Before
    public void setUp() throws Exception
    {
//        databaseMock = new DatabaseConnection();
//        databaseMock.registeredUsersTable = resgisteredUsersDatabaseMock;
        sut = new LoginServlet();
        sut.databaseConnection =databaseMock;
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testDoPostHttpServletRequestHttpServletResponse()
    {
        when(requestMock.getAttribute("login")).thenReturn(LOGIN);
        when(requestMock.getAttribute("password")).thenReturn(PASSWORD);
        when(resgisteredUsersDatabaseMock.getRegisteredUser(LOGIN)).thenReturn(REGISTERED_USER).thenCallRealMethod();
        when(requestMock.getSession()).thenReturn(sessionMock);
        
        
        try
        {
            sut.doPost(requestMock,responseMock);
        } 
        catch (ServletException e)
        {
            fail();
        } catch (IOException e)
        {
            fail();
        }
        verify(responseMock).setContentType("text/html;charset=UTF-8");
        verify(sessionMock).setAttribute(LoginServlet.SESSION_ATRIBUTE, LOGIN);
        verify(sessionMock).setMaxInactiveInterval(sut.SESSION_DURATION);
    }

}
