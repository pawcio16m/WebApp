package webApp.backend;

public class User {
	private int userId;
	private String login;
	private String firstName;
	private String lastName;
	private int age;
	private String phoneNumber;
	private String city;
	private String preferedActivity;
	
	public User(int p_userId, String p_login, String p_firstName, String p_lastName, int p_age,
	        String p_phoneNumber, String p_city, String p_preferedActivity)
	{
		userId = p_userId;
		login = p_login;
		firstName = p_firstName;
		lastName = p_lastName;
		age = p_age;
		phoneNumber = p_phoneNumber;
		city = p_city;
		preferedActivity = p_preferedActivity;
	}
	
    @Override
    public String toString() {
        return "["+userId+"]\t | "+login+"\t | "+firstName+" "+lastName+"\t |  "+age+"\t | "+phoneNumber+"\t | "+preferedActivity+"\t | "+city;
    }
    
    public String toHtml() {
        return "<td>"+userId+"</td><td>"+login+"</td><td>"+firstName+" "+lastName+"</td><td>"+age+"</td><td>"
                +phoneNumber+"</td><td>"+city+"</td><td>"+preferedActivity+"</td>";
    }

}