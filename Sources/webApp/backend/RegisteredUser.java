package webApp.backend;

import java.util.regex.Pattern;

public class RegisteredUser {
	private int userId;
	private String login;
	private String emailAddress;
	private String password;
	private final static int MIN_PASSWORD_LENGTH = 8;
	public static final String adminLogin = "pawcio16m";
	
	public static boolean validateEmail(String p_email)
	{
		if (p_email == null)
		{
			return false;
		}
		
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                 "[a-zA-Z0-9_+&*-]+)*@" +
                 "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                 "A-Z]{2,7}$";
               
		Pattern pat = Pattern.compile(emailRegex);

		return pat.matcher(p_email).matches();
	}
	
	public static boolean validatePassword(String p_password)
	{
		 //Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		 Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
		 Pattern lowerCasePatten = Pattern.compile("[a-z ]");
		 Pattern digitCasePatten = Pattern.compile("[0-9 ]");
	 
		 if (p_password.length() < MIN_PASSWORD_LENGTH)
		 {
			 return false;
		 }
//		 if (!specailCharPatten.matcher(p_password).find())
//		 {
//			 return false;
//		 }
		 if (!UpperCasePatten.matcher(p_password).find())
		 {
			 return false;
		 }
		 if (!lowerCasePatten.matcher(p_password).find())
		 {
			 return false;
		 }
		 if (!digitCasePatten.matcher(p_password).find())
		 {
			 return false;
		 }
		 return true;
	}
	
	public static boolean isAdminMode(String p_login)
	{
		return adminLogin.equals(p_login);
	}
	
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
