package webApp.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import webApp.backend.ApplicationUtilities;
import webApp.backend.ErrorMsgs;
import webApp.backend.RegisteredUser;

public class RegisteredUsersDatabaseTable {
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
		
	public ErrorMsgs createRegisteredUsersTable()
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

	public List<RegisteredUser> getUsers()
	{
		List<RegisteredUser> registeredUsers = new LinkedList<RegisteredUser>();
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
	
	public ErrorMsgs deleteTable()
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
    public String toString()
	{
		String result = tableHeader;
		List<RegisteredUser> registeredUsers = getUsers();
		if (registeredUsers != null)
		for (RegisteredUser registeredUser : registeredUsers)
		{
			result = result.concat(registeredUser.toString()+"\n");
		}
		return result;
	}
	
    public String toHtml()
	{
		String result = tableHtmlHeader; 
		List<RegisteredUser> registeredUsers = getUsers();
		if (registeredUsers != null)
		{
    		for (RegisteredUser registeredUser :registeredUsers)
    		{
    			result = result.concat("<tr>"+registeredUser.toHtml()+"</tr>");
    		}
		}
		result = result.concat("</table></div>");
		return result;
	}
    
	public RegisteredUser getRegisteredUser(String p_login)
	{
		String selectUserWithGivenLogin = "SELECT * FROM "+tableName+ " WHERE login = '"+p_login+"'";
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
		
		System.err.println("User "+p_login+" not found in database.");
		return null;		
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
	
	public ErrorMsgs removeRegisteredUser(String p_login)
	{
		String removeRegisteredUser = "DELETE FROM "+tableName+" WHERE login = '"+p_login+"'";
		try
		{
		    statement.execute(removeRegisteredUser);
		}
		catch (SQLException e)
		{
			System.err.println(ErrorMsgs.REMOVE_RECORD_FAILED.toString()+ " user "+p_login);
			e.printStackTrace();
			return ErrorMsgs.REMOVE_RECORD_FAILED;
		}
		return ErrorMsgs.NO_ERROR;		
	}
	
	public ErrorMsgs removeAllRegisteredUsers()
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
	
	
}