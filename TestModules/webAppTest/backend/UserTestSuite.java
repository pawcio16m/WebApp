package webAppTest.backend;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import webApp.backend.ErrorMsgs;
import webApp.backend.User;

public class UserTestSuite
{
    private final int USER_ID = 1;
    private final String LOGIN = "myLogin";
    private final String FIRST_NAME = "myFirstName";
    private final String LAST_NAME = "myLastName";
    private final int AGE = 10;
    private final String PHONE_NUMBER = "801801801";
    private final String CITY = "myCity";
    private final String PREFERED_ACTIVITY = "myActivity";
    private final String USER_TO_STRING = "["+USER_ID+"] | "+LOGIN+" | "+FIRST_NAME+" "+LAST_NAME+" | "
            +AGE+" | "+PHONE_NUMBER+" | "+CITY+" | "+PREFERED_ACTIVITY+" |";
    private final String USER_TO_HTML = "<td>"+USER_ID+"</td><td>"+LOGIN+"</td><td>"+FIRST_NAME+" "+LAST_NAME+"</td><td>"
            +AGE+"</td><td>"+PHONE_NUMBER+"</td><td>"+CITY+"</td><td>"+PREFERED_ACTIVITY+"</td>";
    
    private User sut;
    
    @Before
    public void setUp() throws Exception
    {   
        sut = new User(USER_ID, LOGIN, FIRST_NAME, LAST_NAME, AGE, PHONE_NUMBER, CITY, PREFERED_ACTIVITY);
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
    public void testValidatePhoneNumber()
    {
        assertEquals(ErrorMsgs.NO_ERROR, User.validatePhoneNumber(PHONE_NUMBER));
     
        String invaliPhoneNumber = "a";
        assertEquals(ErrorMsgs.PHONE_NUMBER_INVALID, User.validatePhoneNumber(invaliPhoneNumber));
        
        invaliPhoneNumber = "89914a2";
        assertEquals(ErrorMsgs.PHONE_NUMBER_INVALID, User.validatePhoneNumber(invaliPhoneNumber));
        
        invaliPhoneNumber = "89914,2";
        assertEquals(ErrorMsgs.PHONE_NUMBER_INVALID, User.validatePhoneNumber(invaliPhoneNumber));
    }

}
