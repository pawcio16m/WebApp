package webApp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import webApp.backend.ErrorMsgs;

public class DatabaseConnection
{
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:application.db";
    public UsersDatabaseTable usersTable;
    public RegisteredUsersDatabaseTable registeredUsersTable;

	public DatabaseConnection()
	{
		try
		{
			Class.forName(DatabaseConnection.DRIVER);
	    } 
		catch (ClassNotFoundException e)
		{
	    	System.err.println("JDBC driver not found.");
	        e.printStackTrace();
	    }
		try 
		{
		    registeredUsersTable = new RegisteredUsersDatabaseTable(DatabaseConnection.getDatabaseConnection().createStatement());
		    usersTable = new UsersDatabaseTable(DatabaseConnection.getDatabaseConnection().createStatement());
		}
		catch (SQLException e)
		{
		    System.err.println("Error when creatin table.");
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
	    	System.err.println("Connection to database failed.");
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
	            System.err.println("Error when closing database connection");
	            e.printStackTrace();
	            return ErrorMsgs.DATABASE_CONNECTION_CLOSURE_FAILED;
	        }
	    }
	    else
	    {
	        return ErrorMsgs.EMPTY_DATABASE_CONNECTION;
	    }
	}

	public ErrorMsgs createAllTables()
	{
	    if (ErrorMsgs.NO_ERROR == registeredUsersTable.createRegisteredUsersTable() &&
	        ErrorMsgs.NO_ERROR == usersTable.createUsersTable())
	    {			
			return ErrorMsgs.NO_ERROR;			
		}
	    else
		{
	    	System.err.println("Cannot create tables.");
	    	return ErrorMsgs.DATABASE_TABLE_CREATION_FAILED;
		}
	}
	
	public ErrorMsgs deleteAllTables() 
	{
	    if (ErrorMsgs.NO_ERROR == registeredUsersTable.deleteTable() &&
	        ErrorMsgs.NO_ERROR == usersTable.deleteTable())
	    {           
	        return ErrorMsgs.NO_ERROR;          
	    }
	    else
	    {
	        System.err.println("Cannot create tables.");
	        return ErrorMsgs.DATABASE_TABLE_DELETION_FAILED;
	    }
	}
}