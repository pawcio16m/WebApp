package webApp.database;

import java.util.List;
import webApp.backend.TableRecordType;
import webApp.backend.ErrorMsgs;

public interface DatabaseTable
{
    public ErrorMsgs createDatabaseTable();
    public ErrorMsgs deleteDatabaseTable();
    public List<TableRecordType> getAllRecords();
    public TableRecordType getSpecificRecord(int p_recordId);
    public ErrorMsgs removeRecord(int p_recordId);
    public ErrorMsgs removeAllRecords();
    public String toHtml();
}
