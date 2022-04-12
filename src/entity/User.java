package entity;

public class User {
    private int userId;
    private String username;
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
	
    public User(String theUsername) {
    	 username = theUsername;
	}

    public int getUserId(){
    	return userId;
	}

    
    public void setPassword(String pw) {
    	password = pw;
    }
    
    public String getUsername() {
        return username;
    }
}
 
