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
import webApp.backend.User;

public class UsersDatabaseTable {
    private final String tableName = "users";
	private String createUsersTable = "CREATE TABLE IF NOT EXISTS "+tableName+" ("
		+ "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
		+ "login text NOT NULL UNIQUE,"
		+ "firstName text,"
		+ "lastName text,"
		+ "age integer,"
		+ "phoneNumber text,"
		+ "city text,"
        + "preferedActivity text)";
	private final String deleteUsersTable = "DROP TABLE "+tableName;
    private final Connection databaseConnection = DriverManager.getConnection(DatabaseConnection.DB_URL);    
    private Statement statemant;
	
	UsersDatabaseTable() throws SQLException {
		statemant = databaseConnection.createStatement(); 
		createUsersTable();
	}
	
	public void createUsersTable() throws SQLException
	{
	    statemant.execute(createUsersTable);
	    System.out.println(tableName+" created");
	}

	public void deleteTable() throws SQLException
	{
	    System.out.println(tableName+" deleted.");
	    statemant.execute(deleteUsersTable);
	}
	
    public String toHtml()
    {
        String result = "<div><table border=\"1\"><tr><th>UserId</th><th>login</th><th>Name</th><th>Age</th>"
                + "<th>Phone Number</th><th>City</th><th>Prefered Activity</th>"; 
       
        List<User> users = getUsers();
        if (users != null)
        {
            for (User user : users)
            {
                result = result.concat("<tr>"+user.toHtml()+"</tr>");
            }
        }
        result = result.concat("</table></div>");
        return result;
    }
	
	public boolean insertUser(String p_login) {
		try {
			PreparedStatement insertStatement = databaseConnection.prepareStatement(
					"INSERT INTO "+tableName+" VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);");
			insertStatement.setString(1, p_login);
			insertStatement.setString(2, "");
			insertStatement.setString(3, "");
			insertStatement.setInt(4, 0);
			insertStatement.setString(5, "");
			insertStatement.setString(6, "");
			insertStatement.setString(7, "");
			insertStatement.execute();
		} catch (SQLException e) {
			System.err.println("Error when user insertion.");
			e.printStackTrace();
			return false;
		}
		return true;	
	}

    public boolean updateUserProfile(String p_login, String p_firstName, String p_lastName, int p_age, String p_phoneNumber,
            String p_city, String p_preferedActivity)
    {
        String updateProfile = "UPDATE "+tableName+ " SET firstName = '"+p_firstName+"',"
                + " lastName = '"+p_lastName+"',"
                + " age = '"+p_age+"',"
                + " phoneNumber = '"+p_phoneNumber+"',"
                + " city = '"+p_city+"',"
                + " preferedActivity = '"+p_preferedActivity+"'"
                + " WHERE login = '"+p_login+"'";   
        try 
        {
            statemant.execute(updateProfile);
        }
        catch (SQLException e)
        {
            System.err.println("Updating profile for user "+p_login+" failed.");
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
				                     usersTable.getString("login"),
									 usersTable.getString("firstName"),
									 usersTable.getString("lastName"),
									 usersTable.getInt("age"),
									 usersTable.getString("phoneNumber"),
									 usersTable.getString("city"),
	                                 usersTable.getString("preferedActivity"));
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
		System.out.println("[UserId] | Login\t | Name\t |  Age\t | PhoneNumber\t | City\t | Prefered Activity");
		for (User user : getUsers())
		{
			System.out.println(user.toString());
		}
	}
	
	public boolean removeAllUsers()
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
