package webApp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import webApp.backend.RegisteredUser;

public class RegisteredUsersDatabaseTable {
	private final String tableName = "registered_users";
	private final String createRegisteredUsersTable = "CREATE TABLE IF NOT EXISTS "+tableName+"("
		+ "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
		+ "login text NOT NULL UNIQUE,"
		+ "mailAddress text NOT NULL UNIQUE,"
		+ "password text NOT NULL)";
	private final String deleteRegisteredUsersTable = "DROP TABLE "+tableName;
	private final String tableHeader = "[UserId] | login\t |  emailAddress\n";
	private Statement statemant;
	private final Connection databaseConnection = DriverManager.getConnection(DatabaseConnection.DB_URL);


	public RegisteredUsersDatabaseTable() throws SQLException
	{
		statemant = databaseConnection.createStatement();
		createRegisteredUsersTable();
	}
		
	private void createRegisteredUsersTable() throws SQLException
	{
		statemant.execute(createRegisteredUsersTable);
	}
	
	public boolean insertUser(String p_login, String p_emailAddress, String p_password)
	{
		try
		{
			PreparedStatement insertStatement = databaseConnection.prepareStatement(
					"insert into "+tableName+" values (NULL, ?, ?, ?);");
			insertStatement.setString(1, p_login);
			insertStatement.setString(2, p_emailAddress);
			insertStatement.setString(3, p_password);
			insertStatement.execute();
		}
		catch (SQLException e)
		{
			System.err.println("Error when user insertion.");
			e.printStackTrace();
			return false;
		}
		return true;	
	}

	public List<RegisteredUser> getUsers()
	{
		List<RegisteredUser> registeredUsers = new LinkedList<RegisteredUser>();
		try
		{
			ResultSet registeredUsersTable = statemant.executeQuery("SELECT * FROM "+tableName);
	
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
	
	public void deleteTable() throws SQLException
	{
		statemant.execute(deleteRegisteredUsersTable);
	}
		
	@Override
    public String toString()
	{
		String result = tableHeader;
		for (RegisteredUser registeredUser : getUsers())
		{
			result = result.concat(registeredUser.toString()+"\n");
		}
		return result;
	}
	
    public String toHtml()
	{
		String result = "<div><table border=\"1\"><tr><th>UserId</th><th>login</th><th>email</th></tr>"; 
		for (RegisteredUser registeredUser : getUsers())
		{
			result = result.concat("<tr>"+registeredUser.toHtml()+"</tr>");
		}
		result = result.concat("</table></div>");
		return result;
	}
    
	public RegisteredUser getRegisteredUser(String p_login)
	{
		int onlyOneUserFound = 1;
		List<RegisteredUser> registeredUsers = new LinkedList<RegisteredUser>();
		try
		{
			ResultSet registeredUsersTable = statemant.executeQuery("SELECT * FROM "+tableName+ " WHERE login = '"+p_login+"'");
			
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
	
	public boolean updatePassword(int p_userId, String p_newPassword)
	{
		String updatePassword = "UPDATE "+tableName+ " SET password = '"+p_newPassword+"' WHERE user_id = "+p_userId;
		try 
		{
			statemant.execute(updatePassword);
		}
		catch (SQLException e)
		{
			System.err.println("Updating password for user "+p_userId+" failed.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean removeRegisteredUser(String p_login)
	{
		String removeRegisteredUser = "DELETE FROM "+tableName+" WHERE login = '"+p_login+"'";
		try
		{
			statemant.execute(removeRegisteredUser);
		}
		catch (SQLException e)
		{
			System.err.println("Removing user "+p_login+" from database failed.");
			e.printStackTrace();
			return false;
		}
		return true;		
	}
	
	public boolean removeAllRegisteredUsers()
	{
		String removeAllRegisteredUser = "DELETE FROM "+tableName+" WHERE login != '"+RegisteredUser.adminLogin+"'";
		try
		{
			statemant.execute(removeAllRegisteredUser);
		} 
		catch (SQLException e)
		{
			System.err.println("Removing all users from database failed.");
			e.printStackTrace();
			return false;
		}
		return true;		
	}
}