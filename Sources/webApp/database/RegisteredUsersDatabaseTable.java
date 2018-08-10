package webApp.database;

import java.sql.Connection;
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
	private Statement statemant;
	private Connection databaseConnection;


	RegisteredUsersDatabaseTable(Connection p_databaseConnection) throws SQLException {
		databaseConnection = p_databaseConnection;
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
		
	public void printAllUsers()
	{
		System.out.println("[UserId] | login\t |  emailAddress");
		for (RegisteredUser registeredUser : getUsers())
		{
			System.out.println(registeredUser.toString());
		}
	}
}
