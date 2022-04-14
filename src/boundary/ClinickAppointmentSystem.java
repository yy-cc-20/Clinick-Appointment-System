/**
 * @subject UECS2344 SOFTWARE DESIGN
 * @trimester January 2022
 * @system Clinick Appointment System
 * @date 15/04/2022
 * @author Ling Sun Shuai      2004562 P2
 * @author Kong Suet Hua       2005756 P5
 * @author Olivia Ong Yee Ming 2004564 P5
 * @author Tan Jia Qi          1904022 P2
 * @author Yang Chu Yan        2005912 P2
 * @database MySQL, using JDBC API (driver class version 8.0.28)
 * @description A console program that aims to digitize the process of making an appointment.
 * Applying object-oriented programming concept, using entity-boundary-controller design pattern.
 * @login User id and password can be found at database.DatabaseSetUp
 * @login User id and password can be found at database.DatabaseSetUp
 * @see database.DatabaseConnection class on how to connect this system to the MySQL database on your computer
 */

/**
 * @login User id and password can be found at database.DatabaseSetUp
 */

package boundary;

import database.DatabaseConnection;
import entity.*;

public class ClinickAppointmentSystem {
    private static final LoginUI loginUI = new LoginUI();
    private static MakeAppointmentUI makeAppointmentUI;
    private static ManageAppointmentUI manageAppointmentUI;
    private static ManagePatientUI managePatientUI;
    private static ManageAccountUI manageAccountUI;
    private static ViewSlotsUI viewSlotsUI;

    public static void main(String... args) {
        boolean toExit;

        while (true) {
            makeAppointmentUI = new MakeAppointmentUI();
            manageAppointmentUI = new ManageAppointmentUI();
            managePatientUI = new ManagePatientUI();
            manageAccountUI = new ManageAccountUI();
            viewSlotsUI = new ViewSlotsUI();

            // Guest Mode
            toExit = startGuestView();
            if (toExit)
                break;

            // The user has log in
            User systemUser = loginUI.getUser(); // From systemUser can know the username, id, password, user type

            if (systemUser instanceof Receptionist)
                toExit = startReceptionistView(systemUser);
            else if (systemUser instanceof Doctor)
                toExit = startDoctorView(systemUser);
            else if (systemUser instanceof Patient)
                toExit = startPatientView(systemUser);

            if (toExit)
                break;
        }
        System.out.println("Program stopped. ");
        SingletonScanner.close();
        DatabaseConnection.closeConnection();
        System.exit(0);
    }

    /** @return false to log in, true to exit application */
    static boolean startGuestView() {
        int choiceNo; // the action that user wants to perform

        while (true) {
            ConsoleUI.displaySystemName("Clinick Appointment Booking System");
            ConsoleUI.displayMenuForGuest();
            choiceNo = ConsoleInput.askChoice(0, 2, "Your choice");

            switch (choiceNo) {
                case 1 -> {
                    ConsoleUI.displayFunctionName("View Services and Time Slots for Booking");
                    viewSlotsUI.viewSlots();
                }
                case 2 -> {
                    ConsoleUI.displayFunctionName("Sign In");
                    loginUI.login(); // Suspend the user to login for 10 seconds after 3 failed login attempts
                    if (loginUI.getHasLogin()) {
                        ConsoleUI.clearScreen();
                        return false;
                    }
                }
                case 0 -> {
                    return true;
                }
            }
            ConsoleUI.clearScreen();
        }
    }

    /** @return false to log out, true to exit application */
    static boolean startReceptionistView(User systemUser) {
        int choiceNo; // the action that user wants to perform
        boolean toExit;
        while (true) {

            ConsoleUI.displaySystemName("Clinick Appointment Booking System");
            ConsoleUI.displayMenuForReceptionist();
            choiceNo = ConsoleInput.askChoice(0, 11, "Your choice");

            switch (choiceNo) {
                case 1 -> {
                    ConsoleUI.displayFunctionName("View Appointment");
                    makeAppointmentUI.viewAppointment(systemUser);
                }
                case 2 -> {
                    ConsoleUI.displayFunctionName("Search Appointment");
                    makeAppointmentUI.searchAppointment();
                }
                case 3 -> {
                    ConsoleUI.displayFunctionName("Make Appointment");
                    makeAppointmentUI.makeAppointment();
                }
                case 4 -> {
                    ConsoleUI.displayFunctionName("Update Appointment");
                    manageAppointmentUI.updateAppointment();
                }
                case 5 -> {
                    ConsoleUI.displayFunctionName("Cancel Appointment");
                    manageAppointmentUI.cancelAppointment();
                }
                case 6 -> {
                    ConsoleUI.displayFunctionName("Record Attendance");
                    manageAppointmentUI.recordAttendance();
                }
                case 7 -> {
                    ConsoleUI.displayFunctionName("Create Patient Profile");
                    managePatientUI.createPatientProfile();
                }
                case 8 -> {
                    ConsoleUI.displayFunctionName("Manage Patient Profile");
                    managePatientUI.managePatientProfile(systemUser);
                }
                case 9 -> {
                    ConsoleUI.displayFunctionName("Search Patient");
                    managePatientUI.searchPatient();
                }
                case 10 -> {
                    ConsoleUI.displayFunctionName("Manage Account");
                    manageAccountUI.changePassword(systemUser);
                }
                case 11 -> {
                    ConsoleUI.displayFunctionName("View Services and Time Slots for Booking");
                    viewSlotsUI.viewSlots();
                }
                case 0 -> {
                    System.out.println("[1]Sign out");
                    System.out.println("[0]Exit Application");
                    toExit = ConsoleInput.askChoice(0, 1, "Select number") == 0;
                    if (!toExit)
                        ConsoleUI.clearScreen();
                    return toExit;
                }
            }
            ConsoleUI.clearScreen();
        }
    }

    /** @return false to log out, true to exit application */
    static boolean startDoctorView(User systemUser) {
        int choiceNo; // the action that user wants to perform
        boolean toExit;
        while (true) {
            ConsoleUI.displaySystemName("Clinick Appointment Booking System");
            ConsoleUI.displayMenuForDoctor();
            choiceNo = ConsoleInput.askChoice(0, 4, "Your choice");

            switch (choiceNo) {
                case 1 -> {
                    ConsoleUI.displayFunctionName("View Appointment");
                    makeAppointmentUI.viewAppointment(systemUser);
                }
                case 2 -> {
                    ConsoleUI.displayFunctionName("Search Appointment");
                    makeAppointmentUI.searchAppointment();
                }
                case 3 -> {
                    ConsoleUI.displayFunctionName("Search Patient");
                    managePatientUI.searchPatient();
                }
                case 4 -> {
                    ConsoleUI.displayFunctionName("Manage Account");
                    manageAccountUI.changePassword(systemUser);
                }
                case 0 -> {
                    System.out.println("[1]Sign out");
                    System.out.println("[0]Exit Application");
                    toExit = ConsoleInput.askChoice(0, 1, "Select number") == 0;
                    if (!toExit)
                        ConsoleUI.clearScreen();
                    return toExit;
                }
            }
            ConsoleUI.clearScreen();
        }
    }

    /** @return false to log out, true to exit application */
    static boolean startPatientView(User systemUser) {
        int choiceNo; // the action that user wants to perform
        boolean toExit;
        while (true) {
            ConsoleUI.displaySystemName("Clinick Appointment Booking System");
            ConsoleUI.displayMenuForPatient();
            choiceNo = ConsoleInput.askChoice(0, 5, "Your choice");

            switch (choiceNo) {
                case 1 -> {
                    ConsoleUI.displayFunctionName("View Appointment");
                    makeAppointmentUI.viewAppointment(systemUser);
                }
                case 2 -> {
                    ConsoleUI.displayFunctionName("Search Appointment");
                    makeAppointmentUI.searchAppointment();
                }
                case 3 -> {
                    ConsoleUI.displayFunctionName("Manage Account");
                    manageAccountUI.changePassword(systemUser);
                }
                case 4 -> {
                    ConsoleUI.displayFunctionName("Manage Profile");
                    managePatientUI.managePatientProfile(systemUser);
                }
                case 5 -> {
                    ConsoleUI.displayFunctionName("View Services and Time Slots for Booking");
                    viewSlotsUI.viewSlots();
                }
                case 0 -> {
                    System.out.println("[1]Sign out");
                    System.out.println("[0]Exit Application");
                    toExit = ConsoleInput.askChoice(0, 1, "Select number") == 0;
                    if (!toExit)
                        ConsoleUI.clearScreen();
                    return toExit;
                }
            }
            ConsoleUI.clearScreen();
        }
    }
}
