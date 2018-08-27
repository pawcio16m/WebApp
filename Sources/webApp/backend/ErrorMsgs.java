package webApp.backend;

public enum ErrorMsgs
{
    NO_ERROR("No error detected."),
    //DatabaseError
    DATABASE_TABLE_CREATION_FAILED("Failure when creating database tables."),
    DATABASE_TABLE_DELETION_FAILED("Failure when deleting database tables."),
    DATABASE_CONNECTION_CLOSURE_FAILED("Failure when closing database connection."),
    EMPTY_DATABASE_CONNECTION("There is no database connection."),
    //ValidationError
    PHONE_NUMBER_INVALID("Phone number contains not allowed characters (should contain only digit)."),
    //RegestrationTable
    INSERT_USER_FAILED("Failure when inserting user to database."),
    UPDATE_USER_PASSWORD_FAILED("Failure when updating password for user."),
    REMOVE_USER_FAILED("Failure when removing record from database."),
    REMOVE_ALL_USERS_FAILED("Failure when removing all records from database."),
    UPDATE_RECORD_FAILED("Failure when updating record in database.")
    ;

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
