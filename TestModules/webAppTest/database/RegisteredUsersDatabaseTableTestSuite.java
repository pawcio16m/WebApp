package webAppTest.database;

import static org.junit.Assert.*;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import webApp.database.DatabaseConnection;
import webApp.database.RegisteredUsersDatabaseTable;
import webApp.backend.RegisteredUser;

public class RegisteredUsersDatabaseTableTestSuite {
	private final int USER_ID = 1;
	private final String LOGIN = "myLogin"; 
	private final String EMAIL_ADDRESS = "myLogin@at.com"; 
	private final String PASSWORD = "myPassword"; 
	private final RegisteredUser REGISTERED_USER = new RegisteredUser(USER_ID, LOGIN, EMAIL_ADDRESS, PASSWORD);
	private final int ADMIN_ID = 2;
	private final String ADMIN_ADDRESS = "admin@admin.com"; 
	private final String ADMIN_PASSWORD = "admin1234"; 		
	private final RegisteredUser ADMIN_USER = new RegisteredUser(ADMIN_ID, RegisteredUser.adminLogin, ADMIN_ADDRESS, ADMIN_PASSWORD);
	
	private DatabaseConnection databaseConnection;
	private RegisteredUsersDatabaseTable sut; 

	@Before
	public void setUp() throws Exception {
		databaseConnection = new DatabaseConnection();
		try {
			sut = new RegisteredUsersDatabaseTable();
		}
		catch (SQLException e) {
			System.err.println("TestSuite cannot create connection to database");			
		}
	}
	
	@After
	public void tearDown() throws Exception {
		sut.deleteTable();
		databaseConnection.closeConnection();
	}

	@Test
	public void testInsertUser() {
		sut.insertUser(LOGIN, EMAIL_ADDRESS, PASSWORD);
		
		assertEquals(REGISTERED_USER.toString(), sut.getRegisteredUser(LOGIN).toString());
	}

	@Test
	public void testGetRegisteredUser_shouldReturnNulWehnNotFoundInTable() {
		RegisteredUser userNotRegistered = null;
		
		assertEquals(userNotRegistered, sut.getRegisteredUser(LOGIN));
	}
	
	@Test
	public void testGetUsers() {
		int numberOfUsersBeforeInsertion = 0;
		int numberOfUsersAfterInsertions = 2;
		
		assertEquals(numberOfUsersBeforeInsertion, sut.getUsers().size());
		
		sut.insertUser(LOGIN, EMAIL_ADDRESS, PASSWORD);
		sut.insertUser(RegisteredUser.adminLogin, ADMIN_ADDRESS, ADMIN_PASSWORD);
		
		assertEquals(numberOfUsersAfterInsertions, sut.getUsers().size());
	}
	
	@Test
	public void testUpdatePassword() {		
		String updatedPassword = "myUpdatedPassword"; 
		RegisteredUser registerdUserWithUpdatedPassword = 
			new RegisteredUser(USER_ID, LOGIN, EMAIL_ADDRESS, updatedPassword);
		
		sut.insertUser(LOGIN, EMAIL_ADDRESS, PASSWORD);
		sut.updatePassword(USER_ID, updatedPassword);
		
		assertEquals(registerdUserWithUpdatedPassword.toString(), sut.getRegisteredUser(LOGIN).toString());
	}
	
	@Test
	public void testRemoveRegisteredUser() {
		int numberOfUsersBeforeDeletion = 2;
		int numberOfUsersAfterDeletion = 1;
		RegisteredUser userNotRegistered = null;
		
		sut.insertUser(LOGIN, EMAIL_ADDRESS, PASSWORD);
		sut.insertUser(RegisteredUser.adminLogin, ADMIN_ADDRESS, ADMIN_PASSWORD);

		assertEquals(numberOfUsersBeforeDeletion, sut.getUsers().size());
		
		sut.removeRegisteredUser(LOGIN);
		
		assertEquals(userNotRegistered, sut.getRegisteredUser(LOGIN));
		assertEquals(numberOfUsersAfterDeletion, sut.getUsers().size());
	}
	
	@Test
	public void testRemoveAllRegisteredUsers() {
		int numberOfUsersBeforeDeletion = 2;
		int numberOfUsersAfterDeletion = 1;
		RegisteredUser userNotRegistered = null;
		
		sut.insertUser(LOGIN, EMAIL_ADDRESS, PASSWORD);
		sut.insertUser(RegisteredUser.adminLogin, ADMIN_ADDRESS, ADMIN_PASSWORD);

		assertEquals(numberOfUsersBeforeDeletion, sut.getUsers().size());
		
		sut.removeAllRegisteredUsers();
		
		assertEquals(userNotRegistered, sut.getRegisteredUser(LOGIN));
		assertEquals(ADMIN_USER.toString(), sut.getRegisteredUser(RegisteredUser.adminLogin).toString());
		assertEquals(numberOfUsersAfterDeletion, sut.getUsers().size());	
	}

}
