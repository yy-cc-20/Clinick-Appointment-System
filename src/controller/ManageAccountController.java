package controller;
// For the user to change their username and password

import entity.User;

public class ManageAccountController {
	User currentUser;
	
	public ManageAccountController(User uc) {
		currentUser = uc;
	}
	
    public void updatePassword(String password) {
    	currentUser.setPassword(password);
    	// TODO update to database
    }
}
