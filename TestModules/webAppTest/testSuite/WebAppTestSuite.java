package webAppTest.testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import webAppTest.database.RegisteredUsersDatabaseTableTestSuite;

@RunWith(Suite.class)
@SuiteClasses({RegisteredUsersDatabaseTableTestSuite.class})
public class WebAppTestSuite {

}
