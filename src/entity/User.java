package entity;

public class User {
    protected int userId;
    protected String username;
    protected String password;

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

    public void setUserId(int id) {
        userId = id;
    }

    public int getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pw) {
        password = pw;
    }

    public String getUsername() {
        return username;
    }
}
 
