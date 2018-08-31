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
}
