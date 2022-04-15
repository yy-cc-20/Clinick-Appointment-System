package presentation;

import applicationLogic.ManageAccountController;
import domain.User;

/*
 * How to use this class:
 * new ManageAccountUI(systemUser).changePassword(); // Just 1 line
 * the new password will be updated to the database
 */

public class ManageAccountUI {
    private ManageAccountController controller;

    public ManageAccountUI() {
        controller = new ManageAccountController();
    }

    // Exit this method if the user do not want to change the password
    public void changePassword(User theUser) {
        if (!ConsoleInput.askBoolean("Change password"))
            return; // Don't want to change password

        String inputPassword;
        String confirmedPassword;

        while (true) {
            System.out.print("New password > ");
            inputPassword = SingletonScanner.nextLine();
            if (inputPassword.equalsIgnoreCase(ConsoleUI.CANCEL_KEY))
                return;

            if (ManageAccountController.isValidPassword(inputPassword)) {
                break;
            } else {
                System.out.print(ManageAccountController.PASSWORD_CRITERIA);
                System.out.println(ConsoleUI.CANCEL_OPERATION);
            }
        }

        System.out.print("Confirm new password > ");
        confirmedPassword = SingletonScanner.nextLine();

        if (inputPassword.equals(confirmedPassword)) {
            controller.updatePassword(theUser, confirmedPassword);
            System.out.println("Password changed.");
        } else
            System.out.println("Password did not match.");
    }
}
