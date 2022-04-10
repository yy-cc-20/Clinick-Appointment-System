package boundary;

import entity.User;
import boundary.*;
import controller.ManageAccountController;

/*
 * How to use this class:
 * new ManageAccountUI(systemUser).changePassword(); // Just 1 line
 * the new password will be updated to the database
 */

public class ManageAccountUI {
    private ManageAccountController controller;
    private User currentUser;
    
    public ManageAccountUI(User uc) {
    	this.currentUser = uc;
    	controller = new ManageAccountController(currentUser);
    }
    
    // Exit this method if the user do not want to change the password
    public void changePassword() {
    	if (!KeyboardInput.askBoolean("Change password"))
    		return; // Don't want to change password

        String inputPassword;
        String confirmedPassword;
        
        while (true) {
            System.out.print("> New password  ");
            inputPassword = SingletonScanner.scanner.nextLine();
            if (inputPassword.equalsIgnoreCase("x"))
            	return;
            
            if (ManageAccountController.isValidPassword(inputPassword)) {
                break;
            } else {
                System.out.printf(ManageAccountController.PASSWORD_CRITERIA);
                System.out.println("Enter 'x' to cancel.");
            }
        }
        
        System.out.print("> Confirm new password ");
        confirmedPassword = SingletonScanner.scanner.nextLine();
        
        if (inputPassword.equals(confirmedPassword)) {
        	controller.updatePassword(confirmedPassword);
        	System.out.println("Password changed.");
        } else
        	System.out.println("Password did not match.");
    }
    
    /*
    public void modifyAccount(User currentUser) {
        System.out.println("[1] Change Username");
        System.out.println("[2] Change Password");
        int action = ConsoleUI.askEventNo(1, 2);
        String username = "adf";
        String password = "adsf";

        switch (action) {
            case 1 -> {
                String inputUsername;
                while (true) {
                    System.out.print("> Username ");
                    inputUsername = SingletonScanner.scanner.nextLine();
                    if (KeyboardInput.hasDelimiter(inputUsername)) {
                        System.out.println();
                    } else {
                        username = inputUsername;
                        break;
                    }
                }
                System.out.println("The username has changed to " + username);
            }
            case 2 -> {
                String inputPassword;
                System.out.print("> Enter current password ");
                inputPassword = SingletonScanner.scanner.nextLine();
                if (inputPassword.equals(password)) {
                    askPassword();
                    System.out.println("The password has changed.");
                } else {
                    System.out.println("Wrong password.");
                }
            }
        }
        // TODO update the arraylist / database
    }*/
}
