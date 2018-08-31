package webApp.backend;

public enum ErrorMsgs
{
    NO_ERROR("No error detected."),
    //DatabaseError
    DATABASE_TABLE_CREATION_FAILED("Failure when creating database table."),
    DATABASE_TABLE_DELETION_FAILED("Failure when deleting database table."),
    DATABASE_CONNECTION_CLOSURE_FAILED("Failure when closing database connection."),
    EMPTY_DATABASE_CONNECTION("There is no database connection."),
    REMOVE_RECORD_FAILED("Failure when removing record from database."),
    REMOVE_ALL_RECORDS_FAILED("Failure when removing all records from database."),
    UPDATE_RECORD_FAILED("Failure when updating record in database."),
    INSERT_RECORD_FAILED("Failure when inserting user to database."),
    //ValidationError
    PHONE_NUMBER_INVALID("Phone number contains not allowed characters (should contain only digit)."),
    EMAIL_INCORRECT("User password is incorrect."),
    EMAIL_INVALID("Email is invalid."),
    PASSWORD_TOO_SHORT("Password contains less then 8 characters."),
    PASSWORD_HAS_NO_UPPER_CASE("Password doesn't contain at least one upper case."),
    PASSWORD_HAS_NO_LOWER_CASE("Password doesn't contain at least one lower case."),
    PASSWORD_HAS_NO_NUMBER("Password doesn't contain at least one number."),
    //RegestrationTable
    UPDATE_USER_PASSWORD_FAILED("Failure when updating password for user.");

    private final String erroMsg;
    
    ErrorMsgs(final String p_errMsg)
    {
        this.erroMsg = p_errMsg;
    }

    @Override
    public String toString()
    {
        return erroMsg;
    }    
}
