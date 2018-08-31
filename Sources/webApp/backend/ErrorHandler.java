package webApp.backend;

import java.util.LinkedList;
import java.util.List;

public class ErrorHandler
{
    private List<ErrorMsgs> reportedErrors;
  
    public ErrorHandler()
    {
        this.reportedErrors = new LinkedList<ErrorMsgs>();
    }    
    
    public void addError(ErrorMsgs p_error)
    {
        reportedErrors.add(p_error);
    }
    
    public void addErrors(List<ErrorMsgs> p_errors)
    {
        reportedErrors.addAll(p_errors);
    }
    
    public List<ErrorMsgs> getReportedErrors()
    {
        return this.reportedErrors;
    }
    
    public void clearErrors()
    {
        reportedErrors.clear();
    }
    
    public boolean hasErrorReported()
    {
        for (ErrorMsgs error : reportedErrors)
        {
            if (ErrorMsgs.NO_ERROR != error)
            {
                return true;
            }
        }
        return false;        
    }
    
    @Override
    public String toString()
    {
        String result = new String();
        for (ErrorMsgs error : reportedErrors)
        {
            if (ErrorMsgs.NO_ERROR != error)
            {
                result = result.concat(error.toString()+"\n");
            }
        }
        return result;        
    }
    
    public String toHtml()
    {
        String result = new String();
        for (ErrorMsgs error : reportedErrors)
        {
            if (ErrorMsgs.NO_ERROR != error)
            {
                result = result.concat(error.toString()+"<br>");
            }
        }
        return result;        
    }
}