package webAppTest.database;

import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.sql.Connection;
import webApp.backend.ErrorMsgs;
import webApp.database.DatabaseConnection;
import webApp.database.RegisteredUsersDatabaseTable;
import webApp.database.UsersDatabaseTable;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseConnectionTestSuite
{
    @Mock 
    RegisteredUsersDatabaseTable registeredUsersDatabaseMock;
    
    @Mock
    UsersDatabaseTable usersDatabaseMock;
    
    @Mock
    Connection connectionMock;
    
    @Before
    public void setUp() throws Exception
    {    
        DatabaseConnection.initializeDatabase();
        DatabaseConnection.registeredUsersTable = registeredUsersDatabaseMock;
        DatabaseConnection.usersTable = usersDatabaseMock;
    }

    @Test
    public void testCloseConnection()
    {
        assertEquals(ErrorMsgs.NO_ERROR, DatabaseConnection.closeConnection());
    }
    
    @Test
    public void testCreateAllTables()
    {
        doReturn(ErrorMsgs.NO_ERROR).when(registeredUsersDatabaseMock).createDatabaseTable();
        doReturn(ErrorMsgs.NO_ERROR).when(usersDatabaseMock).createUsersTable();
        
        assertEquals(ErrorMsgs.NO_ERROR, DatabaseConnection.createAllTables());
    }
    
    @Test
    public void testCreateAllTablesWhenRegisteredUsersTableReturnsError()
    {
        doReturn(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED).when(registeredUsersDatabaseMock).createDatabaseTable();
        doReturn(ErrorMsgs.NO_ERROR).when(usersDatabaseMock).createUsersTable();
     
        assertEquals(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED, DatabaseConnection.createAllTables());
    }
    
    @Test
    public void testCreateAllTablesWhenUsersTableReturnsError()
    {
        doReturn(ErrorMsgs.NO_ERROR).when(registeredUsersDatabaseMock).createDatabaseTable();
        doReturn(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED).when(usersDatabaseMock).createUsersTable();
     
        assertEquals(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED, DatabaseConnection.createAllTables());
    }
    
    @Test
    public void testCreateAllTablesWhenAllTablesReturnsError()
    {
        doReturn(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED).when(registeredUsersDatabaseMock).createDatabaseTable();
        doReturn(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED).when(usersDatabaseMock).createUsersTable();
     
        assertEquals(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED, DatabaseConnection.createAllTables());
    }
    
    @Test
    public void testDeleteAllTables()
    {
        doReturn(ErrorMsgs.NO_ERROR).when(registeredUsersDatabaseMock).deleteDatabaseTable();
        doReturn(ErrorMsgs.NO_ERROR).when(usersDatabaseMock).deleteTable();
        
        assertEquals(ErrorMsgs.NO_ERROR, DatabaseConnection.deleteAllTables());
    }
    
    @Test
    public void testDeleteAllTablesWhenRegisteredUsersTableReturnsError()
    {
        doReturn(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED).when(registeredUsersDatabaseMock).deleteDatabaseTable();
        doReturn(ErrorMsgs.NO_ERROR).when(usersDatabaseMock).deleteTable();
     
        assertEquals(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED, DatabaseConnection.deleteAllTables());
    }
    
    @Test
    public void testDeleteAllTablesWhenUsersTableReturnsError()
    {
        doReturn(ErrorMsgs.NO_ERROR).when(registeredUsersDatabaseMock).deleteDatabaseTable();
        doReturn(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED).when(usersDatabaseMock).deleteTable();
     
        assertEquals(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED, DatabaseConnection.deleteAllTables());
    }
    
    @Test
    public void testDeleteAllTablesAllTablesReturnsError()
    {
        doReturn(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED).when(registeredUsersDatabaseMock).deleteDatabaseTable();
        doReturn(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED).when(usersDatabaseMock).deleteTable();
     
        assertEquals(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED, DatabaseConnection.deleteAllTables());
    }

}
