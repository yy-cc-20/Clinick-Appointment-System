package entity;

public class User {
	String id;
	String username; // unique
	String password;
	
	public User() {}
	
	public User(String theId, String theUsername, String thePassword) {
		id = theId;
		username = theUsername;
		password = thePassword;
	}
	
	public User(String theUsername, String thePassword) {
		username = theUsername;
		password = thePassword;
	}
	
	public String getId() {
		return id;
	}
	
	/*
	public void logout() {
		username = "";
		password = "";
	}
	
	public String getUsername() {
		return username;
	}*/
	
	// For login
	public boolean equals(User aUser) {
		return username.equals(aUser.username) && password.equals(aUser.password);
	}
}
 