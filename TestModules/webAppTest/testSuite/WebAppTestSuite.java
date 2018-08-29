package webAppTest.testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import webApp.Test.servlet.LoginServletTestSuite;
import webAppTest.backend.ErrorHandlerTestSuite;
import webAppTest.backend.RegisteredUserTestSuite;
import webAppTest.backend.UserTestSuite;
import webAppTest.database.DatabaseConnectionTestSuite;
import webAppTest.database.RegisteredUsersDatabaseTableTestSuite;
import webAppTest.database.UsersDatabaseTableTestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	RegisteredUsersDatabaseTableTestSuite.class,
	RegisteredUserTestSuite.class,
    UserTestSuite.class,
    DatabaseConnectionTestSuite.class,
    UsersDatabaseTableTestSuite.class,
    LoginServletTestSuite.class,
    ErrorHandlerTestSuite.class})
public class WebAppTestSuite {

}
