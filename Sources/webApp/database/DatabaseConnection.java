package webApp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import webApp.backend.ErrorMsgs;

public final class DatabaseConnection
{
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:application.db";
    public static UsersDatabaseTable usersTable;
    public static RegisteredUsersDatabaseTable registeredUsersTable;

	private DatabaseConnection()
	{}
	
	public static void initializeDatabase()
	{
		try
		{
		    System.out.println("Database driver installed.");
			Class.forName(DatabaseConnection.DRIVER);
	    } 
		catch (ClassNotFoundException e)
		{
	    	System.err.println("JDBC driver not found.");
	        e.printStackTrace();
	    }
		try 
		{
	        System.out.println("Database table initialized.");
		    registeredUsersTable = new RegisteredUsersDatabaseTable(DatabaseConnection.getDatabaseConnection().createStatement());
		    usersTable = new UsersDatabaseTable(DatabaseConnection.getDatabaseConnection().createStatement());
		}
		catch (SQLException e)
		{
		    System.err.println(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED.toString());
		    e.printStackTrace();
		}        
	}

	public static Connection getDatabaseConnection()
	{
		try
		{
			return DriverManager.getConnection(DB_URL);
	    } 
		catch (SQLException e) 
		{
	    	System.err.println("Error when connecting to database.");
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
	public static ErrorMsgs closeConnection()
	{
	    Connection connection = getDatabaseConnection();
	    if (null != connection)
	    {
	        try
	        {
	            connection.close();
	            return ErrorMsgs.NO_ERROR;
	        } 
	        catch (SQLException e)
	        {
	            System.err.println(ErrorMsgs.DATABASE_CONNECTION_CLOSURE_FAILED.toString());
	            e.printStackTrace();
	            return ErrorMsgs.DATABASE_CONNECTION_CLOSURE_FAILED;
	        }
	    }
	    else
	    {
            System.err.println(ErrorMsgs.EMPTY_DATABASE_CONNECTION.toString());
	        return ErrorMsgs.EMPTY_DATABASE_CONNECTION;
	    }
	}

	public static ErrorMsgs createAllTables()
	{
	    if (ErrorMsgs.NO_ERROR == registeredUsersTable.createRegisteredUsersTable() &&
	        ErrorMsgs.NO_ERROR == usersTable.createUsersTable())
	    {			
			return ErrorMsgs.NO_ERROR;			
		}
	    else
		{
	    	System.err.println( ErrorMsgs.DATABASE_TABLE_CREATION_FAILED.toString());
	    	return ErrorMsgs.DATABASE_TABLE_CREATION_FAILED;
		}
	}
	
	public static ErrorMsgs deleteAllTables() 
	{
	    if (ErrorMsgs.NO_ERROR == registeredUsersTable.deleteTable() &&
	        ErrorMsgs.NO_ERROR == usersTable.deleteTable())
	    {           
	        return ErrorMsgs.NO_ERROR;          
	    }
	    else
	    {
	        System.err.println(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED.toString());
	        return ErrorMsgs.DATABASE_TABLE_DELETION_FAILED;
	    }
	}
	
	public static UsersDatabaseTable getUsersDatabaseTable()
	{
	    return usersTable;
	}

    public static RegisteredUsersDatabaseTable getRegisteredUsersDatabaseTable()
    {
        return registeredUsersTable;
    }
}