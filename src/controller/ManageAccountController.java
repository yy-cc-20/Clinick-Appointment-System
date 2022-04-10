package controller;
// For the user to change their username and password

import entity.*;

public class ManageAccountController {
    public static final String PASSWORD_CRITERIA = "Passwords must have at least 8 characters and contain at least one lowercase letter, one uppercase letter and one digit.%n%n";
    private User currentUser;
	
	public ManageAccountController(User uc) {
		currentUser = uc;
	}
	
    public void updatePassword(String password) {
    	currentUser.setPassword(password);
    	
    	// TODO update to database
    	if (currentUser instanceof Receptionist) {
    		
    	} else if (currentUser instanceof Doctor) {
    		
    	} else if (currentUser instanceof Patient) {
    		
    	} else 
    		throw new IllegalArgumentException();
    }
    

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
}
