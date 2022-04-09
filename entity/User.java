package entity;

public class User {
	int id;
	String username; // unique
	String password;
	
	User() {}
	
	User(int theId, String theUsername, String thePassword) {
		id = theId;
		username = theUsername;
		password = thePassword;
	}
	
	public void logout() {
		username = "";
		password = "";
	}
	
	String getUsername() {
		return username;
	}
	
	boolean equals(User aUser) {
		return username.equals(aUser.username) && password.equals(aUser.password);
	}
}
 