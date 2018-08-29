package webAppTest.backend;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import webApp.backend.ErrorHandler;
import webApp.backend.ErrorMsgs;

public class ErrorHandlerTestSuite
{
    private ErrorHandler sut;
    
    @Before
    public void setUp() throws Exception
    {
        sut = new ErrorHandler();
    }

    @After
    public void tearDown() throws Exception
    {
        sut.clearErrors();
    }

    @Test
    public void testAddError()
    {
        sut.addError(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED);
        
        assertEquals(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED, sut.getReportedErrors().get(0));
    }

    @Test
    public void testClearErrors()
    {
        sut.addError(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED);        
        sut.addError(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED);
        sut.addError(ErrorMsgs.NO_ERROR);        
        assertEquals(3, sut.getReportedErrors().size());
        
        sut.clearErrors();
        assertEquals(0, sut.getReportedErrors().size());      
    }

    @Test
    public void testHasErrorReported()
    {
        sut.addError(ErrorMsgs.NO_ERROR);        
        sut.addError(ErrorMsgs.DATABASE_TABLE_DELETION_FAILED);
        sut.addError(ErrorMsgs.NO_ERROR);  
        
        assertEquals(true, sut.hasErrorReported());
    }
    
    @Test
    public void testHasErrorReportedShouldReturnFalseWhenOnlyNoErrorIsAdded()
    {
        sut.addError(ErrorMsgs.NO_ERROR);        
        sut.addError(ErrorMsgs.NO_ERROR);
        sut.addError(ErrorMsgs.NO_ERROR);  
        
        assertEquals(false, sut.hasErrorReported());
    }

    @Test
    public void testToString()
    {
        sut.addError(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED);
        
        assertEquals(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED.toString()+"\n", sut.toString());
    }

    @Test
    public void testToHtml()
    {
        sut.addError(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED);
        
        assertEquals(ErrorMsgs.DATABASE_TABLE_CREATION_FAILED.toString()+"<br>", sut.toHtml());
    }
}
