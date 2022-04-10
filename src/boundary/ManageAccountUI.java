package boundary;

import entity.User;

public class ManageAccountUI {
    private static final String passwordCriteria = "Passwords must have at least 8 characters and contain at least one lowercase letter, one uppercase letter and one digit.%n%n";

    public void modifyAccount(User user) {
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
    }


    // assume the user has login
    // post: has to update password file
    void changePassword() {

    }

    // will not stop until get the valid username
    // verify the username
    private void changeUsername() {

    }

    // verify the password
    // will not stop until get the valid password
    private void askPassword() {
        String inputPassword;
        String password;
        boolean isValidPassword = false;

        while (!isValidPassword) {
            System.out.print("> Password ");
            inputPassword = SingletonScanner.scanner.nextLine();//String.valueOf(System.console().readPassword());
            isValidPassword = User.isValidPassword(inputPassword);
            if (isValidPassword) {
                password = inputPassword;
            } else {
                System.out.printf(passwordCriteria);
            }
        }
    }
}
