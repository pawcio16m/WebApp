package webAppTest.database;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import webApp.backend.ErrorMsgs;
import webApp.database.DatabaseConnection;
import webApp.database.UsersDatabaseTable;

public class UsersDatabaseTableTestSuite
{
    private final int USER_ID = 1;
    private final String LOGIN = "myLogin";
    private final String FIRST_NAME = "myFirstName";
    private final String LAST_NAME = "myLastName";
    private final int AGE = 10;
    private final String PHONE_NUMBER = "801801801";
    private final String CITY = "myCity";
    private final String PREFERED_ACTIVITY = "myActivity";    
    private final String USER_TO_STRING = "["+USER_ID+"] | "+LOGIN+" | "+FIRST_NAME+" "+LAST_NAME+" | "
            +AGE+" | "+PHONE_NUMBER+" | "+CITY+" | "+PREFERED_ACTIVITY+" |";
    private final String USER_TO_HTML = "<td>"+USER_ID+"</td><td>"+LOGIN+"</td><td>"+FIRST_NAME+" "+LAST_NAME+"</td><td>"
            +AGE+"</td><td>"+PHONE_NUMBER+"</td><td>"+CITY+"</td><td>"+PREFERED_ACTIVITY+"</td>";
    
    private final String insertUser = "INSERT INTO users VALUES (NULL, "+LOGIN+", NULL, NULL, NULL, NULL, NULL, NULL);";
    private final String updateProfile = "UPDATE users SET firstName = '"+FIRST_NAME+"',"
                                                       + " lastName = '"+LAST_NAME+"',"
                                                       + " age = '"+AGE+"',"
                                                       + " phoneNumber = '"+PHONE_NUMBER+"',"
                                                       + " city = '"+CITY+"',"
                                                       + " preferedActivity = '"+PREFERED_ACTIVITY+"'"
                                                       + " WHERE login = '"+LOGIN+"'";   

    private UsersDatabaseTable sut; 

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
        statementMock = mock(Statement.class);
        preparedStatementMock = mock(PreparedStatement.class);
        resultSetMock = mock(ResultSet.class);
        connectionMock = mock(Connection.class);
        
        sut = new UsersDatabaseTable(statementMock);
    }

    @After
    public void tearDown() throws Exception
    {
        DatabaseConnection.closeConnection();
    }

    @Test
    public void testCreateUsersTable()
    {
        assertEquals(ErrorMsgs.NO_ERROR, sut.createUsersTable());
    }
    
    @Test
    public void testCreateUsersTableWithException()
    {
        try
        {
            doThrow(new SQLException()).when(statementMock).execute(sut.createUsersTable);
            
            assertEquals(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED, sut.createUsersTable());
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
    public void testDeleteTableWitException()
    {
        try
        {
            doThrow(new SQLException()).when(statementMock).execute(sut.deleteUsersTable);
            
            assertEquals(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED, sut.deleteTable());
        }
        catch (SQLException e)
        {
            fail("Unexpected exception.");
        }
    }
    
    @Test
    public void testToHtml()
    {
        String result = sut.tableHtmlHeader+"<tr>"+USER_TO_HTML+"</tr></table></div>";
        try
        {
            setGetUserExpectations();
            
            assertEquals(result, sut.toHtml());
        }
        catch (SQLException e)
        {
            fail("Exception unexpected");
        }
    }

    @Test
    public void testInsertUser()
    {
        try
        {
            doReturn(true).when(statementMock).execute(insertUser);
            
            assertEquals(ErrorMsgs.NO_ERROR, sut.insertUser(LOGIN));            
        }
        catch (SQLException e)
        {
            fail("Exception unexpected");
        }
    }
    
    @Test
    public void testInsertUserWithException()
    {
        try
        {
            doThrow(new SQLException()).when(statementMock).execute(insertUser);
            
            assertEquals(ErrorMsgs.INSERT_USER_FAILED, sut.insertUser(LOGIN));
        }
        catch (SQLException e)
        {
            fail("Exception unexpected");
        }
    }

    @Test
    public void testUpdateUserProfile()
    {
        try
        {
            doReturn(true).when(statementMock).execute(updateProfile);
            
            assertEquals(ErrorMsgs.NO_ERROR, 
                    sut.updateUserProfile(LOGIN, FIRST_NAME, LAST_NAME, AGE, PHONE_NUMBER, CITY, PREFERED_ACTIVITY));
        }
        catch (SQLException e)
        {
            fail("Exception unexpected");
        }
    }
    
    @Test
    public void testUpdateUserProfileWithException()
    {
        try
        {
            doThrow(new SQLException()).when(statementMock).execute(updateProfile);
            
            assertEquals(ErrorMsgs.UPDATE_RECORD_FAILED, 
                    sut.updateUserProfile(LOGIN, FIRST_NAME, LAST_NAME, AGE, PHONE_NUMBER, CITY, PREFERED_ACTIVITY));
        }
        catch (SQLException e)
        {
            fail("Exception unexpected");
        }
    }

    @Test
    public void testGetUsers()
    {
        try
        {
            setGetUserExpectations();
            
            assertEquals(USER_TO_STRING, sut.getUsers().get(0).toString());
        }
        catch (SQLException e)
        {
            fail("Exception unexpected");
        }
    }
    
    @Test
    public void testGetUsersWithException()
    {
        try
        {
            doThrow(new SQLException()).when(statementMock).executeQuery(sut.selectAllUSers);
              
            assertEquals(null, sut.getUsers());
        }
        catch (SQLException e)
        {
            fail("Exception unexpected");
        }
    }

    @Test
    public void testToString()
    {
        String result = sut.tableHeader+USER_TO_STRING+"\n";        
        try
        {
            setGetUserExpectations();
            
            assertEquals(result, sut.toString());
        }
        catch (SQLException e)
        {
            fail("Exception unexpected");
        }
    }

    @Test
    public void testRemoveAllUsers()
    {
        try
        {
            doReturn(true).when(statementMock).execute(sut.removeAllRegisteredUser);
            
            assertEquals(ErrorMsgs.NO_ERROR, sut.removeAllUsers());            
        }
        catch (SQLException e)
        {
            fail("Exception unexpected");
        }
    }
    
    @Test
    public void testRemoveAllUsersWithException()
    {
        try
        {
            doThrow(new SQLException()).when(statementMock).execute(sut.removeAllRegisteredUser);
            
            assertEquals(ErrorMsgs.REMOVE_ALL_USERS_FAILED, sut.removeAllUsers());
        }
        catch (SQLException e)
        {
            fail("Exception unexpected");
        }
    }

    private void setGetUserExpectations() throws SQLException
    {
        when(statementMock.executeQuery(sut.selectAllUSers)).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("user_id")).thenReturn(USER_ID);
        when(resultSetMock.getString("login")).thenReturn(LOGIN);
        when(resultSetMock.getString("firstName")).thenReturn(FIRST_NAME);
        when(resultSetMock.getString("lastName")).thenReturn(LAST_NAME);
        when(resultSetMock.getInt("age")).thenReturn(AGE);
        when(resultSetMock.getString("phoneNumber")).thenReturn(PHONE_NUMBER);
        when(resultSetMock.getString("city")).thenReturn(CITY);
        when(resultSetMock.getString("preferedActivity")).thenReturn(PREFERED_ACTIVITY);        
    }
}