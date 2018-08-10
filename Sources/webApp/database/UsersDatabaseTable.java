package webApp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import webApp.backend.User;

public class UsersDatabaseTable {
    private Statement statemant;
    private Connection databaseConnection;
	String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
		+ "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
		+ "firstName text NOT NULL,"
		+ "lastName test NOT NULL,"
		+ "age integer NOT NULL,"
		+ "phoneNumber text NOT NULL UNIQUE,"
		+ "mailAddress text NOT NULL UNIQUE,"
		+ "city text NOT NULL)";

	UsersDatabaseTable(Connection p_databaseConnection) throws SQLException {
		databaseConnection = p_databaseConnection;
		statemant = databaseConnection.createStatement();
	}
	
	public void createUsersTable() {
		try {
			statemant.execute(createUsersTable);
		} catch (SQLException e) {
			System.err.println("Creating Users table failed");
			e.printStackTrace();
		}
	}

	public boolean insertUser(String p_firstName, String p_lastName, int p_age, String p_phoneNumber, String p_mailAddres, String p_city) {
		try {
			PreparedStatement insertStatement = databaseConnection.prepareStatement(
					"insert into users values (NULL, ?, ?, ?, ?, ?, ?);");
			insertStatement.setString(1, p_firstName);
			insertStatement.setString(2, p_lastName);
			insertStatement.setInt(3, p_age);
			insertStatement.setString(4, p_phoneNumber);
			insertStatement.setString(5, p_mailAddres);
			insertStatement.setString(6, p_city);			
			insertStatement.execute();
		} catch (SQLException e) {
			System.err.println("Error when user insertion");
			e.printStackTrace();
			return false;
		}
		return true;	
	}

	public List<User> getUsers() {
		List<User> users = new LinkedList<User>();
		try {
			ResultSet usersTable = statemant.executeQuery("SELECT * FROM users");

			while(usersTable.next()) {
				User user = new User(usersTable.getInt("user_id"),
									 usersTable.getString("firstName"),
									 usersTable.getString("lastName"),
									 usersTable.getInt("age"),
									 usersTable.getString("phoneNumber"),
									 usersTable.getString("mailAddress"),
									 usersTable.getString("city"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	    return users;
	}
	
	public void printAllUsers()
	{
		System.out.println("[UserId] | \tName\t |  Age\t | PhoneNumber\t | emailAddress\t\t | City");
		for (User user : getUsers())
		{
			System.out.println(user.toString());
		}
	}
}
