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
	private Statement statemant;
	private final Connection databaseConnection = DriverManager.getConnection(DatabaseConnection.DB_URL);


	public RegisteredUsersDatabaseTable() throws SQLException {
		statemant = databaseConnection.createStatement();
	}
		
	public void createRegisteredUsersTable() {
		try {
			statemant.execute(createRegisteredUsersTable);
		} catch (SQLException e) {
			System.err.println("Creating Registered Users table failed");
			e.printStackTrace();
		}
	}

	public boolean insertUser(String p_login, String p_emailAddress, String p_password) {
		try {
			PreparedStatement insertStatement = databaseConnection.prepareStatement(
					"insert into "+tableName+" values (NULL, ?, ?, ?);");
			insertStatement.setString(1, p_login);
			insertStatement.setString(2, p_emailAddress);
			insertStatement.setString(3, p_password);
			insertStatement.execute();
		} catch (SQLException e) {
			System.err.println("Error when user insertion");
			e.printStackTrace();
			return false;
		}
		return true;	
	}

	public List<RegisteredUser> getUsers() {
		List<RegisteredUser> registeredUsers = new LinkedList<RegisteredUser>();
		try {
			ResultSet registeredUsersTable = statemant.executeQuery("SELECT * FROM "+tableName);
	
			while(registeredUsersTable.next()) {
				RegisteredUser registeredUser = new RegisteredUser(registeredUsersTable.getInt("user_id"),
																   registeredUsersTable.getString("login"),
																   registeredUsersTable.getString("mailAddress"),
																   registeredUsersTable.getString("password"));
				registeredUsers.add(registeredUser);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return registeredUsers;
	}
	
	public void deleteTable()
	{
		try {
			statemant.execute(deleteRegisteredUsersTable);
		} catch (SQLException e) {
			System.err.println("Deleting Registered Users table failed");
			e.printStackTrace();
		}		
	}
		
	@Override
    public String toString()
	{
		String result = new String("[UserId] | login\t |  emailAddress\n");
		for (RegisteredUser registeredUser : getUsers())
		{
			result = result.concat(registeredUser.toString()+"\n");
		}
		return result;
	}
	
	public RegisteredUser getRegisteredUser(String p_login)
	{
		int onlyOneUserFound = 1;
		List<RegisteredUser> registeredUsers = new LinkedList<RegisteredUser>();
		try {
			ResultSet registeredUsersTable = statemant.executeQuery("SELECT * FROM "+tableName+ " WHERE login = '"+p_login+"'");
			
			while(registeredUsersTable.next()) {
				RegisteredUser registeredUser = new RegisteredUser(registeredUsersTable.getInt("user_id"),
																   registeredUsersTable.getString("login"),
																   registeredUsersTable.getString("mailAddress"),
																   registeredUsersTable.getString("password"));
				registeredUsers.add(registeredUser);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		if (registeredUsers.size() == onlyOneUserFound)
		{
			return registeredUsers.get(onlyOneUserFound - 1);
		}
		System.err.println("User "+p_login+" not found in database");
		return null;		
	}
	
	public boolean updatePassword(int p_userId, String p_newPassword)
	{
		String updatePassword = "UPDATE "+tableName+ " SET password = '"+p_newPassword+"' WHERE user_id = "+p_userId;
		try {
			statemant.execute(updatePassword);
		} catch (SQLException e) {
			System.err.println("Updating password for user "+p_userId+" failed.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
