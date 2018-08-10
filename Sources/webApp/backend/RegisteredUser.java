package webApp.backend;

public class RegisteredUser {
	private int userId;
	private String login;
	private String emailAddress;
	private String password;
	
	public RegisteredUser(int p_userId, String p_login, String p_emailAddress, String p_password)
	{
		userId = p_userId;
		login = p_login;
		emailAddress = p_emailAddress;
		password = p_password;
	}
	
	@Override
	public String toString() {
		return "["+userId+"] "+login+" - "+emailAddress;
	}

}
