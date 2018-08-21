package webApp.backend;

public class RegisteredUser {
	private int userId;
	private String login;
	private String emailAddress;
	private String password;
	public static final String adminLogin = "pawcio16m";
	
	public RegisteredUser(int p_userId, String p_login, String p_emailAddress, String p_password)
	{
		userId = p_userId;
		login = p_login;
		emailAddress = p_emailAddress;
		password = p_password;
	}
	
	public boolean isPasswordCorrect(String p_passworToValidate)
	{
		return password.equals(p_passworToValidate);
	}
	
	public static boolean isAdminMode(String p_login)
	{
		return adminLogin.equals(p_login);
	}
	
	@Override
	public String toString() {
		return "["+userId+"] "+login+" - "+emailAddress;
	}
	
	public String toHtml() {
		return "<td>"+userId+"</td><td>"+login+"</td><td>"+emailAddress+"</td>";
	}

}
