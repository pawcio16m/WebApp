package webAppTest.backend;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import webApp.backend.ApplicationUtilities;
import webApp.backend.ErrorMsgs;

public class ApplicationUtilitiesTestSuite
{
    private final String EMAIL_ADDRESS = "myLogin@at.com"; 
    private final String ADMIN_LOGIN = "pawcio16m";
    private final String LOGIN = "myLogin"; 
    private final String PASSWORD = "myPassword1"; 
    
    @Test
    public void testisAdminMode()
    {
        assertEquals(false, ApplicationUtilities.isAdminMode(LOGIN)); 
        assertEquals(true, ApplicationUtilities.isAdminMode(ADMIN_LOGIN));        
    }
    
    @Test 
    public void testCorrectEmailVerification()
    {
        String emaill = "m@g.pl";
        
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.NO_ERROR);
        assertEquals(ApplicationUtilities.validateEmail(EMAIL_ADDRESS), ErrorMsgs.NO_ERROR);
    }
    
    @Test
    public void testIncorrectEmailVerification()
    {
        String emaill = "@g.l";        
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.EMAIL_INVALID);
        
        emaill = "1@d.p";
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.EMAIL_INVALID);
        
        emaill = "";
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.EMAIL_INVALID);
        
        emaill = null;
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.EMAIL_INVALID);
        
        emaill = "a";
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.EMAIL_INVALID);
        
        emaill = "a.@";
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.EMAIL_INVALID);
        
        emaill = "a@a";
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.EMAIL_INVALID);
            
        emaill = "a@.p";
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.EMAIL_INVALID);
            
        emaill = "a@.a.p";
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.EMAIL_INVALID);
            
        emaill = "a.@1.pl";
        assertEquals(ApplicationUtilities.validateEmail(emaill), ErrorMsgs.EMAIL_INVALID);
    }
    
    @Test 
    public void testCorrectPasswordVerification()
    {
        String password = "M1aaaaaa";
            
        assertEquals(ApplicationUtilities.validatePassword(password), ErrorMsgs.NO_ERROR);
        assertEquals(ApplicationUtilities.validatePassword(PASSWORD), ErrorMsgs.NO_ERROR);       
    }
    
    @Test
    public void testIncorrectPasswordVerification()
    {
        List<ErrorMsgs> errorMsgs = new LinkedList<ErrorMsgs>();
 
        String password = "aa";
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_UPPER_CASE);
        errorMsgs.add(ErrorMsgs.PASSWORD_TOO_SHORT);
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_NUMBER);
        assertEquals(ApplicationUtilities.validatePassword(password), errorMsgs);
        errorMsgs.clear();
            
        password = "Aa1";
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_UPPER_CASE);
        errorMsgs.add(ErrorMsgs.PASSWORD_TOO_SHORT);
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_NUMBER);
        assertEquals(ApplicationUtilities.validatePassword(password), errorMsgs);
        errorMsgs.clear();
 
        password = "onlylowercase";
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_UPPER_CASE);
        errorMsgs.add(ErrorMsgs.PASSWORD_TOO_SHORT);
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_NUMBER);
        assertEquals(ApplicationUtilities.validatePassword(password), errorMsgs);
        errorMsgs.clear();
        
        password = "ONLYUPPERCASE";
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_UPPER_CASE);
        errorMsgs.add(ErrorMsgs.PASSWORD_TOO_SHORT);
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_NUMBER);
        assertEquals(ApplicationUtilities.validatePassword(password), errorMsgs);
        errorMsgs.clear();
        
        password = "1234567890";
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_UPPER_CASE);
        errorMsgs.add(ErrorMsgs.PASSWORD_TOO_SHORT);
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_NUMBER);
        assertEquals(ApplicationUtilities.validatePassword(password), errorMsgs);

        password = "alllowercaseOneupper";
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_UPPER_CASE);
        errorMsgs.add(ErrorMsgs.PASSWORD_TOO_SHORT);
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_NUMBER);
        assertEquals(ApplicationUtilities.validatePassword(password), errorMsgs);
    
        password = "onenumber1alllowercase";
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_UPPER_CASE);
        errorMsgs.add(ErrorMsgs.PASSWORD_TOO_SHORT);
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_NUMBER);
        assertEquals(ApplicationUtilities.validatePassword(password), errorMsgs);
        
        password = "ONENUMBER1ALLUPPERCASE";
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_UPPER_CASE);
        errorMsgs.add(ErrorMsgs.PASSWORD_TOO_SHORT);
        errorMsgs.add(ErrorMsgs.PASSWORD_HAS_NO_NUMBER);
        assertEquals(ApplicationUtilities.validatePassword(password), errorMsgs);
    }   

}
