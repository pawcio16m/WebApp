package webAppTest.database;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import webApp.database.DatabaseConnection;
import webApp.database.RegisteredUsersDatabaseTable;
import webApp.backend.ErrorMsgs;
import webApp.backend.RegisteredUser;

@RunWith(MockitoJUnitRunner.class)
public class RegisteredUsersDatabaseTableTestSuite
{
	private final int USER_ID = 1;
	private final String LOGIN = "myLogin"; 
	private final String EMAIL_ADDRESS = "myLogin@at.com"; 
	private final String PASSWORD = "myPassword"; 
	private final String UPDATED_PASSWORD = "myUpdatedPassword"; 
	private final RegisteredUser REGISTERED_USER = new RegisteredUser(USER_ID, LOGIN, EMAIL_ADDRESS, PASSWORD);
	
	private final String insertUser = "insert into registered_users values (NULL, "+LOGIN+", "+EMAIL_ADDRESS+", "+PASSWORD+");";
	private final String selectUserWithGivenLogin = "SELECT * FROM registered_users WHERE login = '"+LOGIN+"'";
	private final String updatePassword = "UPDATE registered_users SET password = '"+UPDATED_PASSWORD+"' WHERE login = '"+LOGIN+"'";
	private final String removeRegisteredUser = "DELETE FROM registered_users WHERE login = '"+LOGIN+"'";
	   
    private RegisteredUsersDatabaseTable sut; 

    @Mock
	Statement statementMock;
    
    @Mock
    PreparedStatement preparedStatementMock;
    
    @Mock
    ResultSet resultSetMock;    
    
    @Mock
    Connection connectionMock;    

	@Before
	public void setUp() throws Exception
	{
		new DatabaseConnection();
		
		sut = new RegisteredUsersDatabaseTable(statementMock);
	}
	
	@After
	public void tearDown() throws SQLException
	{
		DatabaseConnection.closeConnection();
	}
	
    @Test
    public void testCreateUsersTable()
    {
        assertEquals(ErrorMsgs.NO_ERROR, sut.createRegisteredUsersTable());
    }
    
    @Test
    public void testCreateUsersTableWithError()
    {
        try
        {
            doThrow(new SQLException()).when(statementMock).execute(sut.createRegisteredUsersTable);
            
            assertEquals(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED, sut.createRegisteredUsersTable());
        }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testDeleteTable()
    {
        assertEquals(ErrorMsgs.NO_ERROR, sut.deleteTable());
    }
    
    @Test
    public void testDeleteUsersTableWithError()
    {
        try
        {
            doThrow(new SQLException()).when(statementMock).execute(sut.deleteRegisteredUsersTable);
            
            assertEquals(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED, sut.deleteTable());
        }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }
    }

	@Test
	public void testToString()
	{
	   String result = sut.tableHeader+REGISTERED_USER.toString()+"\n";	   
	   try
	   {
	       setExpectationForGettingRegisteredUser(sut.selectAllRegisteredUsers);
	   }
       catch (SQLException e)
       {
           fail("Unexpected exception.");
       }
	   assertEquals(result, sut.toString());
	}
	
	@Test
	public void testToHtml() throws SQLException
	{
	    String result = sut.tableHtmlHeader+"<tr>"+REGISTERED_USER.toHtml()+"</tr></table></div>";
	    try
	    {
	        setExpectationForGettingRegisteredUser(sut.selectAllRegisteredUsers);
	    }
	    catch (SQLException e)
	    {
	        fail("Unexpected exception.");
	    }
	    assertEquals(result, sut.toHtml()); 
	}
	
	@Test
	public void testInsertUser()
	{	 
        assertEquals(ErrorMsgs.NO_ERROR, sut.insertUser(LOGIN, EMAIL_ADDRESS, PASSWORD));
	    try
	    {
	        verify(statementMock).execute(insertUser);
	    }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }
	}
	
	@Test
	public void testInsertUserWithExceptions()
	{
	    try
	    {
	        doThrow(new SQLException()).when(statementMock).execute(insertUser);
	    }
	    catch (SQLException e)
        {
            fail("Unexpected exception.");
        }        
	    assertEquals(ErrorMsgs.INSERT_RECORD_FAILED, sut.insertUser(LOGIN, EMAIL_ADDRESS, PASSWORD));
	}

	@Test
	public void testGetRegisteredUser_shouldReturnNullWehnNotFoundInTable()
	{
	    try
	    {
	        when(statementMock.executeQuery(selectUserWithGivenLogin)).thenReturn(resultSetMock);
	    }
	    catch (SQLException e)
        {
            fail("Unexpected exception.");
        }   
		assertEquals(null, sut.getRegisteredUser(LOGIN));
	}
	
	@Test
	public void testGetRegisteredUser()
	{
	    try
	    {
	        setExpectationForGettingRegisteredUser(selectUserWithGivenLogin);
	    }
	    catch (SQLException e)
        {
            fail("Unexpected exception.");
        }  
	    assertEquals(REGISTERED_USER.toString(), sut.getRegisteredUser(LOGIN).toString());
	}
	
	@Test
	public void testGetRegisteredUserWithException()
	{
	    try
	    {	    
	        doThrow(new SQLException()).when(statementMock).executeQuery(selectUserWithGivenLogin);
	    }  
	    catch (SQLException e)
        {
            fail("Unexpected exception.");
        }  
	    assertEquals(null, sut.getRegisteredUser(LOGIN));   
	}
	
	@Test
	public void testGetUsers()
	{
        try
        {
            setExpectationForGettingRegisteredUser(sut.selectAllRegisteredUsers);
        }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }  				
	    assertEquals(REGISTERED_USER.toString(), sut.getUsers().get(0).toString());
	}
	
	@Test
	public void testGetUsersWithExceptions()
	{
	    try
	    {
	        doThrow(new SQLException()).when(statementMock).executeQuery(sut.selectAllRegisteredUsers);
	    }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }       
	    assertEquals(null, sut.getUsers());
	}
	
	@Test
	public void testUpdatePassword()
	{	
	    try
	    {
	        doReturn(true).when(statementMock).execute(updatePassword);
	    }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }		
        assertEquals(ErrorMsgs.NO_ERROR, sut.updatePassword(LOGIN, UPDATED_PASSWORD));
	}
	
    @Test
    public void testUpdatePasswordWithException()
    {   
        try
        {
            doThrow(new SQLException()).when(statementMock).execute(updatePassword);
        }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }        
        assertEquals(ErrorMsgs.UPDATE_USER_PASSWORD_FAILED, sut.updatePassword(LOGIN, UPDATED_PASSWORD));
    }	
	
	@Test
	public void testRemoveRegisteredUser()
	{
	    try
	    {
	        doReturn(true).when(statementMock).execute(removeRegisteredUser);
	    }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }    
        assertEquals(ErrorMsgs.NO_ERROR, sut.removeRegisteredUser(LOGIN));
	}
	
	@Test
	public void testRemoveRegisteredUserWithException() throws SQLException
	{
        try
        {
            doThrow(new SQLException()).when(statementMock).execute(removeRegisteredUser);
        }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }  
	    assertEquals(ErrorMsgs.REMOVE_RECORD_FAILED, sut.removeRegisteredUser(LOGIN));
	}

	@Test
	public void testRemoveAllRegisteredUsers()
	{        
        try
        {
            doReturn(true).when(statementMock).execute(sut.removeAllRegisteredUser);
        }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }  
        assertEquals(ErrorMsgs.NO_ERROR, sut.removeAllRegisteredUsers());
	}
	
	@Test
	public void testRemoveAllRegisteredUsersWithException()
	{
        try
        {
            doThrow(new SQLException()).when(statementMock).execute(sut.removeAllRegisteredUser);
        }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }          
	    assertEquals(ErrorMsgs.REMOVE_ALL_RECORDS_FAILED, sut.removeAllRegisteredUsers());
	}
	
	private void setExpectationForGettingRegisteredUser(String p_query) throws SQLException
	{
	    when(statementMock.executeQuery(p_query)).thenReturn(resultSetMock);
	    when(resultSetMock.next()).thenReturn(true).thenReturn(false);
	    when(resultSetMock.getInt("user_id")).thenReturn(USER_ID);
	    when(resultSetMock.getString("login")).thenReturn(LOGIN);
	    when(resultSetMock.getString("mailAddress")).thenReturn(EMAIL_ADDRESS);
	    when(resultSetMock.getString("password")).thenReturn(PASSWORD);
	}
}