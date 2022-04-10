package entity;

public class User {
    public static final String PASSWORD_CRITERIA = "Passwords must have at least 8 characters and contain at least one lowercase letter, one uppercase letter and one digit.%n%n";
    private int userId;
    private String username; // unique
    private String password;

	// password cannot contain comma
	// @return true if have at least 8 characters and contain at least
	// one lowercase letter, one uppercase letter, one digit and no comma
	public static boolean isValidPassword(String password) {
		boolean isStrong = false;
		boolean isLong = password.length() >= 8;
		boolean tooLong = password.length() > 45;
		boolean hasLowerCase = false;
		boolean hasUpperCase = false;
		boolean hasDigit = false;
		boolean thisCharacterHasType = false; // skip the rest of the checking
		
		if(!isLong || tooLong) {
			return false;
		}
		
		for(int i = 0; i < password.length(); ++i) {
			thisCharacterHasType = false;
			
			if(Character.isLowerCase(password.charAt(i))) {
				hasLowerCase = true;
				thisCharacterHasType = true;
			}
		
			if(!thisCharacterHasType) {
				if(Character.isUpperCase(password.charAt(i))) {
					hasUpperCase = true;
					thisCharacterHasType = true;
				}
			}
			
			if(!thisCharacterHasType) {
				if(Character.isDigit(password.charAt(i))) {
					hasDigit = true;
					thisCharacterHasType = true;
				}
			}
			
			isStrong = hasLowerCase && hasUpperCase && hasDigit;
			if(isStrong) {
				return true;
			}
		} 
		return false;
	}

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


    public void logout() {
        username = "";
        password = "";
    }
    */
    public void setPassword(String pw) {
    	password = pw;
    }
    
    public String getUsername() {
        return username;
    }

    public int getUserId(){
    	return userId;
	}

    // For login
    public boolean equals(User aUser) {
        return userId == aUser.userId && password.equals(aUser.password);
    }
}
 