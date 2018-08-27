package webAppTest.backend;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import webApp.backend.RegisteredUser;

public class RegisteredUserTestSuite
{
	private final int USER_ID = 1;
	private final String LOGIN = "myLogin"; 
	private final String EMAIL_ADDRESS = "myLogin@at.com"; 
	private final String PASSWORD = "myPassword1"; 
	private final String INCORRECT_PASSWORD = "notMyPassword"; 
	private final boolean PASSWORD_CORRECT = true;
	private final boolean PASSWORD_NOT_CORRECT = false;
	private final String USER_TO_STRING = "[1] myLogin - myLogin@at.com";
	private final String USER_TO_HTML = "<td>1</td><td>myLogin</td><td>myLogin@at.com</td>";
	private final String ADMIN_LOGIN = "pawcio16m";
	
	RegisteredUser sut;

	@Before
	public void setUp() throws Exception
	{
		sut = new RegisteredUser(USER_ID, LOGIN, EMAIL_ADDRESS, PASSWORD);
	}

	@Test
	public void testIsPasswordCorrect()
	{
		assertEquals(PASSWORD_CORRECT, sut.isPasswordCorrect(PASSWORD));
		assertEquals(PASSWORD_NOT_CORRECT, sut.isPasswordCorrect(INCORRECT_PASSWORD));
	}
	
	@Test
	public void testToString()
	{
		assertEquals(USER_TO_STRING, sut.toString());		
	}

	@Test
	public void testToHtml()
	{
	    assertEquals(USER_TO_HTML, sut.toHtml());       
	}
	
	@Test
	public void testisAdminMode()
	{
		assertEquals(false, RegisteredUser.isAdminMode(LOGIN));	
		assertEquals(true, RegisteredUser.isAdminMode(ADMIN_LOGIN));		
	}
	
	@Test 
	public void testCorrectEmailVerification()
	{
	    boolean emaillCorrect = true;
	    String emaill = "m@g.pl";
	    
	    assertEquals(RegisteredUser.validateEmail(emaill), emaillCorrect);
	    assertEquals(RegisteredUser.validateEmail(EMAIL_ADDRESS), emaillCorrect);
	}
	
	@Test
	public void testIncorrectEmailVerification()
	{
	    boolean emailIncorrect = false;
	    String emaill = "@g.l";
	    
	    assertEquals(RegisteredUser.validateEmail(emaill), emailIncorrect);
	    
	    emaill = "1@d.p";
	    assertEquals(RegisteredUser.validateEmail(emaill), emailIncorrect);
	    
	    emaill = "";
	    assertEquals(RegisteredUser.validateEmail(emaill), emailIncorrect);
	    
	    emaill = null;
	    assertEquals(RegisteredUser.validateEmail(emaill), emailIncorrect);
	    
	    emaill = "a";
	    assertEquals(RegisteredUser.validateEmail(emaill), emailIncorrect);
	    
	    emaill = "a.@";
	    assertEquals(RegisteredUser.validateEmail(emaill), emailIncorrect);
	    
	    emaill = "a@a";
	    assertEquals(RegisteredUser.validateEmail(emaill), emailIncorrect);
	        
	    emaill = "a@.p";
	    assertEquals(RegisteredUser.validateEmail(emaill), emailIncorrect);
	        
	    emaill = "a@.a.p";
	    assertEquals(RegisteredUser.validateEmail(emaill), emailIncorrect);
	        
	    emaill = "a.@1.pl";
	    assertEquals(RegisteredUser.validateEmail(emaill), emailIncorrect);
	}
	
	@Test 
	public void testCorrectPasswordVerification()
	{
	    boolean passwordCorrect = true;
	    String password = "M1aaaaaa";
	        
	    assertEquals(RegisteredUser.validatePassword(password), passwordCorrect);
	    assertEquals(RegisteredUser.validatePassword(PASSWORD), passwordCorrect);	    
	}
	
	@Test
	public void testIncorrectPasswordVerification()
	{
	    boolean passwordIncorrect = false;
	    String password = "aa";
	            
	    assertEquals(RegisteredUser.validatePassword(password), passwordIncorrect);
		    
	    password = "Aa1";
	    assertEquals(RegisteredUser.validatePassword(password), passwordIncorrect);
 
	    password = "onlylowercase";
	    assertEquals(RegisteredUser.validatePassword(password), passwordIncorrect);
        
	    password = "ONLYUPPERCASE";
	    assertEquals(RegisteredUser.validatePassword(password), passwordIncorrect);
	    
	    password = "1234567890";
	    assertEquals(RegisteredUser.validatePassword(password), passwordIncorrect);

	    password = "alllowercaseOneupper";
	    assertEquals(RegisteredUser.validatePassword(password), passwordIncorrect);
    
	    password = "onenumber1alllowercase";
	    assertEquals(RegisteredUser.validatePassword(password), passwordIncorrect);
	    
	    password = "ONENUMBER1ALLUPPERCASE";
	    assertEquals(RegisteredUser.validatePassword(password), passwordIncorrect);
	}	
}
