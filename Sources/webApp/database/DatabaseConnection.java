package webApp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:application.db";
    public UsersDatabaseTable usersTable;

    private Connection databaseConnection;

	public DatabaseConnection() {
		try {
			Class.forName(DatabaseConnection.DRIVER);
	    } catch (ClassNotFoundException e) {
	    	System.err.println("JDBC driver not found");
	        e.printStackTrace();
	    }

		try {
			databaseConnection = DriverManager.getConnection(DB_URL);
	    } catch (SQLException e) {
	    	System.err.println("Connection to database failed");
	    	e.printStackTrace();
	    }

		createTables();
	}

	public void createTables()  {
		try {
			usersTable = new UsersDatabaseTable(databaseConnection);
			usersTable.createUsersTable();
		} catch	(SQLException e) {
	    	System.err.println("Cannot creat UserDatabaseTable");
	    	e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			databaseConnection.close();
		} catch (SQLException e) {
			System.err.println("Error when closing database connection");
			e.printStackTrace();
		}
	}
}
