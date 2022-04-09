package boundary;

public class ManageAccountView {
	private static final String passwordCriteria = "Passwords must have at least 8 characters and contain at least one lowercase letter, one uppercase letter and one digit. Cannot contain comma.%n%n";

	public void modify() {
		System.out.println("[1]Change Username");
		System.out.println("[2]Change Password");		
		int action = UserInterface.askEventNo(1, 2);
		
		switch(action) {
			case 1: currentUser.changeUsername();
				break;
			case 2: currentUser.changePassword();
		}
		userList.set(currentUserIndex, currentUser);
		userDatabase.save(userList);
	}
	
	// assume the user has login 
	// post: has to update password file
	void changeUsername() {
		askUsername2();
		System.out.println("The username has changed to " + username);
	}
	
	// assume the user has login 
	// post: has to update password file
	void changePassword() {
		String inputPassword;
		System.out.print("> Enter current password ");
		inputPassword = Singleton.keyboard.nextLine();
		if(inputPassword.equals(password)) {
			askPassword2();
			System.out.println("The password has changed.");
		} else {
			System.out.println("Wrong password.");
		}
	}
	
	// for signUp()
	// will not stop until get the valid username
	// verify the username
	private void askUsername2() {
		String inputUsername;
		
		while(true) {
			System.out.print("> Username ");
			inputUsername = Singleton.keyboard.nextLine();
			if(KeyboardInput.hasDelimiter(inputUsername)) {
				System.out.printf(KeyboardInput.getStringError());
			} else {
				username = inputUsername;
				break;
			}
		} 
	}

	// for signUp()
	// verify the password
	// will not stop until get the valid password
	private void askPassword2() {
		String inputPassword;
		boolean isValidPassword = false;
		
		while(!isValidPassword) {
			System.out.print("> Password ");
			inputPassword = Singleton.keyboard.nextLine();//String.valueOf(System.console().readPassword());
			isValidPassword = isValidPassword(inputPassword);
			if(isValidPassword) {
				password = inputPassword;
			} else {
				System.out.printf(passwordCriteria);
			}
		}
	}
	
	// only for sign up, not for login
		// password cannot contain comma
		// @return true if have at least 8 characters and contain at least
		// one lowercase letter, one uppercase letter, one digit and no comma
		private boolean isValidPassword(String password) {
			boolean isStrong = false;
			boolean isLong = password.length() >= 8;
			boolean hasLowerCase = false;
			boolean hasUpperCase = false;
			boolean hasDigit = false;
			boolean thisCharacterHasType = false; // skip the rest of the checking
			
			if(!isLong || KeyboardInput.hasDelimiter(password)) {
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
