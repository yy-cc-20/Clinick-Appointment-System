package applicationLogic;
// For the user to change their username and password

import java.sql.SQLException;
import java.sql.Statement;

import dataAccess.DatabaseConnection;
import domain.*;

public class ManageAccountController {
    public static final String PASSWORD_CRITERIA = "Passwords must have at least 8 characters and contain at least one lowercase letter, one uppercase letter and one digit.%n%n";

    public ManageAccountController() {
    }

    public void updatePassword(User currentUser, String password) {
        currentUser.setPassword(password);
        String table;
        if (currentUser instanceof Receptionist) {
            table = "Receptionist";
        } else if (currentUser instanceof Doctor) {
            table = "Doctor";
        } else if (currentUser instanceof Patient) {
            table = "Patient";
        } else
            throw new IllegalArgumentException();

        String sql = "UPDATE " + table + " SET password = \"" + password + "\" WHERE id = " + currentUser.getUserId();
        try {
            Statement st = DatabaseConnection.getConnection().createStatement();
            st.executeUpdate(sql);
        } catch (SQLException e) { // If the exception was not caught, the program will stop
            e.printStackTrace();
        }
    }


    // @return true if the user has at least 8 characters and contain at least
    // one lowercase letter, one uppercase letter, one digit and no comma
    public static boolean isValidPassword(String password) {
        boolean isStrong;
        boolean isLong = password.length() >= 8;
        boolean tooLong = password.length() > 45;
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasDigit = false;
        boolean thisCharacterHasType; // skip the rest of the checking

        if (!isLong || tooLong) {
            return false;
        }

        for (int i = 0; i < password.length(); ++i) {
            thisCharacterHasType = false;

            if (Character.isLowerCase(password.charAt(i))) {
                hasLowerCase = true;
                thisCharacterHasType = true;
            }

            if (!thisCharacterHasType) {
                if (Character.isUpperCase(password.charAt(i))) {
                    hasUpperCase = true;
                    thisCharacterHasType = true;
                }
            }

            if (!thisCharacterHasType) {
                if (Character.isDigit(password.charAt(i))) {
                    hasDigit = true;
                    thisCharacterHasType = true;
                }
            }

            isStrong = hasLowerCase && hasUpperCase && hasDigit;
            if (isStrong) {
                return true;
            }
        }
        return false;
    }
	/*
	public static void main(String[] args) {
		new ManageAccountController(new Doctor(1, "", "")).updatePassword("password");
	}*/
}
