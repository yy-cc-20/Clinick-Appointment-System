package boundary;

import entity.User;

// display message on the screen

public class ConsoleUI { // UI: user interface
    // only static variable can be used in static method
    private static final int headingWidth = 50; // the number of characters
    private static int screenHeight = 22; // the number of lines
    
    // instantiating the user interfaces
    MakeAppointmentUI makeAppointmentInterface = new MakeAppointmentUI();
    ManageAppointmentUI manageAppointmentInterface = new ManageAppointmentUI();
    ManagePatientUI managePatientInterface = new ManagePatientUI();

    // start the user interface
    public void start() {
        displaySystemName("Clinic Booking System");
        User systemUser = new LoginUI().login(); // Suspend the user to login for 10 seconds after 3 failed login
                                                 // attempts
        // From systemUser can know the username, id, password, user type

        clearScreen();

        int choiceNo; // the action that user wants to perform
        final int beginChoiceNo = 1;
        final int endChoiceNo = 3;

        while (true) {
            displaySystemName("System Name");
            // ConsoleUI.displayMenu(); // need to change the menu
            choiceNo = KeyboardInput.askChoice(beginChoiceNo, endChoiceNo, "Your choice");

            switch (choiceNo) {
                case 1 -> {
                    displayFunctionName("Account Setting");
                    new ManageAccountUI(systemUser).changePassword();
                }
                case 2 -> // Modify Account Info
                    displayFunctionName(" Modify Account Details ");
                case 3 -> { // logout and exit the program
                    displayFunctionName(" Program Stopped ");
                    SingletonScanner.scanner.close();
                    System.exit(0);
                }
            }
            ConsoleUI.clearScreen();
        }
    }

    // clear screen
    public static void clearScreen() {
        System.out.printf("%n[Enter] to continue...");
        SingletonScanner.scanner.nextLine(); // pause, wait for user to continue
        for (int i = 0; i < screenHeight; ++i) {
            System.out.println();
        }
    }

    // display the program name and menu name
    public static void displaySystemName(String heading) {
        int field = headingWidth - heading.length();

        for (int i = 0; i < headingWidth; ++i) {
            System.out.print('#');
        }
        System.out.printf("%n%n");

        for (int i = 0; i < field / 2; ++i) { // display the heading in the center
            System.out.print(' ');
        }
        System.out.print(heading);
        for (int i = 0; i < field / 2; ++i) {
            System.out.print(' ');
        }
        System.out.printf("%n%n");

        for (int i = 0; i < headingWidth; ++i) {
            System.out.print('#');
        }
        System.out.printf("%n%n");
    }

    public static void displayFunctionName(String heading) {
        int field = headingWidth - heading.length();

        System.out.printf("%n");

        for (int i = 0; i < field / 2; ++i) { // display the heading in the center
            System.out.print('-');
        }
        System.out.print(heading);
        for (int i = 0; i < field / 2; ++i) {
            System.out.print('-');
        }
        System.out.printf("%n");
    }

    // display the menus for each unique user in this program

    public static void displayMenuForReceptionist() {
        System.out.println("         Menu             ");
        System.out.println(" 1. View Appointment      ");
        System.out.println(" 2. Search Appointment    ");
        System.out.println(" 3. Make Appointment      ");

        System.out.println(" 4. Update Appointment    ");
        System.out.println(" 5. Cancel Appointment    ");
        System.out.println(" 6. Record Attendance     ");

        System.out.println(" 7. Create Patient Profile");
        System.out.println(" 8. Manage Patient Profile");
        System.out.println(" 9. Search Patient        ");

        System.out.println("10. Manage Account        ");
        System.out.println("11. View Slots            ");

        System.out.println(" 0. Exit Application      ");
    }

    public static void displayMenuForDoctor() {
        System.out.println("         Menu             ");
        System.out.println(" 1. View Appointment      ");
        System.out.println(" 2. Search Appointment    ");
        System.out.println(" 3. Search Patient        ");
        System.out.println(" 4. Manage Account        ");
        System.out.println(" 0. Exit Application      ");
    }

    public static void displayMenuForPatient() {
        System.out.println("         Menu             ");
        System.out.println(" 1. View Appointment      ");
        System.out.println(" 2. Search Appointment    ");
        System.out.println(" 3. Manage Account        ");
        System.out.println(" 4. View Slots            ");
        System.out.println(" 0. Exit Application      ");
    }
}
