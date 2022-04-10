package entity;

public class User {
    private int userId;
    private String username; // Immutable
    private String password;

    public User() {
    }

    public User(int theId, String theUsername, String thePassword) {
        userId = theId;
        username = theUsername;
        password = thePassword;
    }

    public User(String theUsername, String thePassword) {
        username = theUsername;
        password = thePassword;
    }

    /*
    public String getId() {
        return id;
    }    
    
    public int getUserId(){
    	return userId;
	}

    */
    public void setPassword(String pw) {
    	password = pw;
    }
    
    public String getUsername() {
        return username;
    }

    // For login
    public boolean equals(User aUser) {
        return userId == aUser.userId && password.equals(aUser.password);
    }
}
 