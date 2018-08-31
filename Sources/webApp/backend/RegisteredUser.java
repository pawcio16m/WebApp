package webApp.backend;

public class RegisteredUser implements TableRecordType {
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
	
	public boolean isPasswordCorrect(String p_passworToValidate)
	{
		return password.equals(p_passworToValidate);
	}
	
	@Override
	public String toHtml()
	{
		return "<td>"+userId+"</td><td>"+login+"</td><td>"+emailAddress+"</td>";
	}
	
	@Override
	public String toString()
	{
		return "["+userId+"] "+login+" - "+emailAddress;
	}
}
