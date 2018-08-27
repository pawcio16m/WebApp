package webApp.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import webApp.backend.ErrorMsgs;
import webApp.backend.RegisteredUser;
import webApp.backend.User;

public class UsersDatabaseTable {
    public final String tableName = "users";
	public String createUsersTable = "CREATE TABLE IF NOT EXISTS "+tableName+" ("
		+ "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
		+ "login text NOT NULL UNIQUE,"
		+ "firstName text,"
		+ "lastName text,"
		+ "age integer,"
		+ "phoneNumber text,"
		+ "city text,"
        + "preferedActivity text)";
	public final String deleteUsersTable = "DROP TABLE "+tableName;
	public final String selectAllUSers = "SELECT * FROM "+tableName;
    public final String removeAllRegisteredUser = "DELETE FROM "+tableName+" WHERE login != '"+RegisteredUser.adminLogin+"'";
	public final String tableHeader = "[UserId] | Login\t | Name\t |  Age\t | PhoneNumber\t | City\t | Prefered Activity\n";
	public final String tableHtmlHeader = "<div><table border=\"1\"><tr><th>UserId</th><th>login</th><th>Name</th><th>Age</th>"
            + "<th>Phone Number</th><th>City</th><th>Prefered Activity</th>"; 
	private Statement statement;
	
	public UsersDatabaseTable(Statement p_statement)
	{
	    statement = p_statement;
	}
	
	public ErrorMsgs createUsersTable()
	{
	    try
	    {
    	    statement.execute(createUsersTable);
    	    System.out.println(tableName+" created");
	    }
	    catch (SQLException e)
	    {
	        System.err.println("Error when creatin users table");
	        e.printStackTrace();
	        return ErrorMsgs.DATABASE_TABLE_CREATION_FAILED;
	    }
	    return ErrorMsgs.NO_ERROR;
	}

	public ErrorMsgs deleteTable()
	{
	    try
	    {
    	    statement.execute(deleteUsersTable);
    	    System.out.println(tableName+" deleted.");
	    }
	    catch (SQLException e)
	    {
	        System.err.println("Error when creatin users table");
	        e.printStackTrace();
	        return ErrorMsgs.DATABASE_TABLE_DELETION_FAILED;
	    }
	    return ErrorMsgs.NO_ERROR;
	}
	
	@Override
	public String toString()
	{
	    String result = tableHeader;
	    List<User> users =  getUsers();
	    if (null != users)
	    {     
	        for (User user : users)
	        {
	            result = result.concat(user.toString()+"\n");
	        }	        
	    }

	    return result;
	}
    
	public String toHtml()
    {
        String result = tableHtmlHeader;
        List<User> users = getUsers();
        if (null != users)
        {
            for (User user : users)
            {
                result = result.concat("<tr>"+user.toHtml()+"</tr>");
            }
        }
        result = result.concat("</table></div>");
        return result;
    }
	
	public ErrorMsgs insertUser(String p_login)
	{
        String insertUser = "INSERT INTO "+tableName+" VALUES (NULL, "+p_login+", NULL, NULL, NULL, NULL, NULL, NULL);";
	    try 
		{
			statement.execute(insertUser);			
		} 
	    catch (SQLException e)
	    {
			System.err.println("Error when user insertion.");
			e.printStackTrace();
			return ErrorMsgs.INSERT_USER_FAILED;
		}
		return ErrorMsgs.NO_ERROR;
	}

    public ErrorMsgs updateUserProfile(String p_login, String p_firstName, String p_lastName, int p_age, String p_phoneNumber,
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
            statement.execute(updateProfile);
        }
        catch (SQLException e)
        {
            System.err.println("Updating profile for user "+p_login+" failed.");
            e.printStackTrace();
            return ErrorMsgs.UPDATE_RECORD_FAILED;
        }
        return ErrorMsgs.NO_ERROR; 
    }
	

	public List<User> getUsers() {
		List<User> users = new LinkedList<User>();
		try {
			ResultSet usersTable = statement.executeQuery(selectAllUSers);

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
	
	public ErrorMsgs removeAllUsers()
	{
	    try
	    {
	        statement.execute(removeAllRegisteredUser);
	    } 
	    catch (SQLException e)
	    {
	        System.err.println("Removing all users from database failed.");
	        e.printStackTrace();
	        return ErrorMsgs.REMOVE_ALL_USERS_FAILED;
	    }
	    return ErrorMsgs.NO_ERROR;        
	}
}
