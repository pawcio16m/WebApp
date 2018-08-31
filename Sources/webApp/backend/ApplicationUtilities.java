package webApp.backend;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ApplicationUtilities
{
    public static final List<String> SUPPORTED_CITIES = Arrays.asList("Kraków", "Wroc³aw");
    public static final List<String> SUPPORTED_ACTIVITIES = Arrays.asList("Siatkówka", "Pi³ka no¿na");
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final String ADMIN_LOGIN = "pawcio16m";
    
    public static ErrorMsgs validateEmail(String p_email)
    {
        if (p_email == null)
        {
            return ErrorMsgs.EMAIL_INCORRECT;
        }
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                 "[a-zA-Z0-9_+&*-]+)*@" +
                 "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                 "A-Z]{2,7}$";
               
        Pattern pat = Pattern.compile(emailRegex);

        if (pat.matcher(p_email).matches())
        {
            return ErrorMsgs.NO_ERROR;
            
        }
        return ErrorMsgs.EMAIL_INCORRECT;
    }
    
    public static List<ErrorMsgs> validatePassword(String p_password)
    {
        ErrorHandler errorHandler = new ErrorHandler();
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        if (p_password.length() < MIN_PASSWORD_LENGTH)
        {
            errorHandler.addError(ErrorMsgs.PASSWORD_TOO_SHORT);
         }
         if (!UpperCasePatten.matcher(p_password).find())
         {
             errorHandler.addError(ErrorMsgs.PASSWORD_HAS_NO_UPPER_CASE);
         }
         if (!lowerCasePatten.matcher(p_password).find())
         {
             errorHandler.addError(ErrorMsgs.PASSWORD_HAS_NO_LOWER_CASE);
         }
         if (!digitCasePatten.matcher(p_password).find())
         {
             errorHandler.addError(ErrorMsgs.PASSWORD_HAS_NO_NUMBER);
         }
         errorHandler.addError(ErrorMsgs.NO_ERROR);
         return errorHandler.getReportedErrors();
    }   
    
    public static boolean isAdminMode(String p_login)
    {
        return ADMIN_LOGIN.equals(p_login);
    }  
}
