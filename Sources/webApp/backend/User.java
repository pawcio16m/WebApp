package webApp.backend;

public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private int age;
	private String phoneNumber;
	private String mailAddress;
	private String city;
	
	public User(int p_userId, String p_firstName, String p_lastName, int p_age, String p_phoneNumber, String p_mailAddres, String p_city)
	{
		userId = p_userId;
		firstName = p_firstName;
		lastName = p_lastName;
		age = p_age;
		phoneNumber = p_phoneNumber;
		mailAddress = p_mailAddres;
		city = p_city;
	}
	
    @Override
    public String toString() {
        return "["+userId+"]\t | "+firstName+" "+lastName+"\t |  "+age+"\t | "+phoneNumber+"\t | "+mailAddress+"\t | "+city;
    }

}