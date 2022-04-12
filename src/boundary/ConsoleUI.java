package boundary;

import java.time.format.DateTimeFormatter;

// display message on the screen

public class ConsoleUI {
	public static final String CANCEL_KEY = "X"; // To be checked by case insensitive
	public static final String CANCEL_OPERATION = "[" + CANCEL_KEY + "] back to the previous page."; // To tell user how to stop the current method
	
    public static final DateTimeFormatter DATE_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Date format for displaying: 01-01-2022
    public static final DateTimeFormatter DATE_SQL_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Date format for SQL query

    // only static variable can be used in static method
    private static final int headingWidth = 50; // the number of characters
    private static int screenHeight = 22; // the number of lines

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
        int field = headingWidth - heading.length() - 2;

        System.out.printf("%n");

        for (int i = 0; i < field / 2; ++i) { // display the heading in the center
            System.out.print('-');
        }
        System.out.print(" " + heading + " ");
        for (int i = 0; i < field / 2; ++i) {
            System.out.print('-');
        }
        System.out.printf("%n");
    }
    
    public static void displayTableName(String tableName) {
        int field = headingWidth - tableName.length() - 2;

        System.out.printf("%n");

        for (int i = 0; i < field / 2; ++i) { // display the heading in the center
            System.out.print(' ');
        }
        System.out.print("<" + tableName + ">");
        for (int i = 0; i < field / 2; ++i) {
            System.out.print(' ');
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
