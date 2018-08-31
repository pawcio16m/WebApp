package webApp.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import webApp.backend.ApplicationUtilities;
import webApp.backend.ErrorMsgs;
import webApp.backend.RegisteredUser;
import webApp.backend.TableRecordType;

public class RegisteredUsersDatabaseTable implements DatabaseTable {
	public final String tableName = "registered_users";
	public final String createRegisteredUsersTable = "CREATE TABLE IF NOT EXISTS "+tableName+"("
		+ "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
		+ "login text NOT NULL UNIQUE,"
		+ "mailAddress text NOT NULL UNIQUE,"
		+ "password text NOT NULL)";
	public final String deleteRegisteredUsersTable = "DROP TABLE "+tableName;
	public final String selectAllRegisteredUsers = "SELECT * FROM "+tableName;
    public final String removeAllRegisteredUser = "DELETE FROM "+tableName+" WHERE login != '"+ApplicationUtilities.ADMIN_LOGIN+"'";
	public final String tableHeader = "[UserId] | login | emailAddress |\n";
	public final String tableHtmlHeader = "<div><table border=\"1\"><tr><th>UserId</th><th>login</th><th>email</th></tr>";
	
	private Statement statement;
	
	public RegisteredUsersDatabaseTable(Statement p_statement)
	{
	    statement = p_statement;
	}
	
	@Override
	public ErrorMsgs createDatabaseTable()
	{ 
	    try
	    {
	        statement.execute(createRegisteredUsersTable);
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	        System.err.println(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED.toString());
	        return ErrorMsgs.DATABASE_TABLE_CREATION_FAILED;
	    }
        System.out.println(tableName+" created");
        return ErrorMsgs.NO_ERROR;
	}
	
	@Override
	public ErrorMsgs deleteDatabaseTable()
	{
	    try
	    {
	        statement.execute(deleteRegisteredUsersTable);
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	        System.err.println(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED.toString());
	        return ErrorMsgs.DATABASE_TABLE_DELETION_FAILED;
	    }
	    
	    System.out.println(tableName+" deleted.");
	    return ErrorMsgs.NO_ERROR;
	}
	
    @Override
	public List<TableRecordType> getAllRecords()
    {
        List<TableRecordType> registeredUsers = new LinkedList<TableRecordType>();
        try
        {
            ResultSet registeredUsersTable = statement.executeQuery(selectAllRegisteredUsers);
    
            while (registeredUsersTable.next())
            {
                RegisteredUser registeredUser = new RegisteredUser(registeredUsersTable.getInt("user_id"),
                                                                   registeredUsersTable.getString("login"),
                                                                   registeredUsersTable.getString("mailAddress"),
                                                                   registeredUsersTable.getString("password"));
                registeredUsers.add(registeredUser);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return registeredUsers;
    }
	
    @Override
    public TableRecordType getSpecificRecord(int p_recordId)
    {
        String selectUserWithGivenLogin = "SELECT * FROM "+tableName+ " WHERE user_id = "+p_recordId;
        int onlyOneUserFound = 1;
        List<RegisteredUser> registeredUsers = new LinkedList<RegisteredUser>();
        try
        {
            ResultSet registeredUsersTable = statement.executeQuery(selectUserWithGivenLogin);
            
            while (registeredUsersTable.next())
            {
                RegisteredUser registeredUser = new RegisteredUser(registeredUsersTable.getInt("user_id"),
                                                                   registeredUsersTable.getString("login"),
                                                                   registeredUsersTable.getString("mailAddress"),
                                                                   registeredUsersTable.getString("password"));
                registeredUsers.add(registeredUser);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        
        if (registeredUsers.size() == onlyOneUserFound)
        {
            return registeredUsers.get(onlyOneUserFound - 1);
        }
        
        System.err.println("User "+p_recordId+" not found in database.");
        return null;        
    }
    
    @Override
    public ErrorMsgs removeRecord(int p_recordId)
    {
        String removeRegisteredUser = "DELETE FROM "+tableName+" WHERE user_id = "+p_recordId;
        try
        {
            statement.execute(removeRegisteredUser);
        }
        catch (SQLException e)
        {
            System.err.println(ErrorMsgs.REMOVE_RECORD_FAILED.toString()+ " user "+p_recordId);
            e.printStackTrace();
            return ErrorMsgs.REMOVE_RECORD_FAILED;
        }
        return ErrorMsgs.NO_ERROR;      
    }
    
    public ErrorMsgs removeAllRecords()
    {
        try
        {
            statement.execute(removeAllRegisteredUser);
        } 
        catch (SQLException e)
        {
            System.err.println(ErrorMsgs.REMOVE_ALL_RECORDS_FAILED.toString());
            e.printStackTrace();
            return ErrorMsgs.REMOVE_ALL_RECORDS_FAILED;
        }
        return ErrorMsgs.NO_ERROR;      
    }
    
    @Override
    public String toString()
    {
        String result = tableHeader;
        List<TableRecordType> registeredUsers = getAllRecords();
        if (registeredUsers != null)
        for (TableRecordType registeredUser : registeredUsers)
        {
            result = result.concat(registeredUser.toString()+"\n");
        }
        return result;
    }
    
    @Override
    public String toHtml()
    {
        String result = tableHtmlHeader; 
        List<TableRecordType> registeredUsers = getAllRecords();
        if (registeredUsers != null)
        {
            for (TableRecordType registeredUser :registeredUsers)
            {
                result = result.concat("<tr>"+registeredUser.toHtml()+"</tr>");
            }
        }
        result = result.concat("</table></div>");
        return result;
    }
    
    public ErrorMsgs insertUser(String p_login, String p_emailAddress, String p_password)
	{
        String insertingUser = "insert into "+tableName+" values (NULL, "+p_login+", "+p_emailAddress+", "+p_password+");";
		try
		{
            statement.execute(insertingUser);
		}
		catch (SQLException e)
		{
			System.err.println(ErrorMsgs.INSERT_RECORD_FAILED.toString());
			e.printStackTrace();
			return ErrorMsgs.INSERT_RECORD_FAILED;
		}
		return ErrorMsgs.NO_ERROR;	
	}

	public ErrorMsgs updatePassword(String p_login, String p_newPassword)
	{
		String updatePassword = "UPDATE "+tableName+ " SET password = '"+p_newPassword+"' WHERE login = '"+p_login+"'";
		try 
		{
		    statement.execute(updatePassword);
		}
		catch (SQLException e)
		{
			System.err.println(ErrorMsgs.UPDATE_USER_PASSWORD_FAILED.toString());
			e.printStackTrace();
			return ErrorMsgs.UPDATE_USER_PASSWORD_FAILED;
		}
		return ErrorMsgs.NO_ERROR;
	}
	
    public int getUserIdForLogin(String p_login)
    {
        String selectUserIdWithGivenLogin = "SELECT user_id FROM "+tableName+ " WHERE login = '"+p_login+"'";
        try
        {
            ResultSet registeredUsersTable = statement.executeQuery(selectUserIdWithGivenLogin);
            
            while (registeredUsersTable.next())
            {
                return registeredUsersTable.getInt("user_id");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
        System.err.println("User "+p_login+" not found in database.");
        return 0;        
    }	
}