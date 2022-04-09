// display message on the screen

public class ConsoleUI {// UI: user interface
    // only static variable can be used in static method
    private static final int headingWidth = 50; // the number of characters
    private static final int screenHeight = 30; // the number of lines

    // clear screen
    public static void cls() {
        System.out.printf(Color.ANSI_CYAN_BRIGHT + "%n%nPress ENTER to continue..." + Color.ANSI_RESET);
        SingleObject.scanner.nextLine(); // pause, wait for user to continue
        for (int i = 0; i < screenHeight; ++i) {
            System.out.println();
        }
    }

    // display the program name
    public static void displayHeading(String heading) {
        int field = headingWidth - heading.length();
        System.out.println();
        for (int i = 0; i < headingWidth; ++i) {
            System.out.print(Color.ANSI_BLUE_BOLD_BRIGHT + "*");
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
            System.out.print('*');
        }
        System.out.printf("%n%n" + Color.ANSI_RESET);
    }

    // display the functions provided in this program
    public static void displayMainMenu() {
        System.out.println("1. View Stock         ");
        System.out.println("2. Add Stock          ");
        System.out.println("3. Search Food        ");
        System.out.println("4. Modify Food Details");
        System.out.println("5. Delete Food        ");
        System.out.println("6. Food Planner       ");
        System.out.println("7. Generate Report    ");
        System.out.println("8. Beneficiary        ");
        System.out.println(Color.ANSI_GRAY_UNDERLINED + "0. Logout and Return to Login Page" + Color.ANSI_RESET);
    }

    // display the login page menu
    public static void displayLoginMenu() {
        System.out.println("1. Login to System     ");
        System.out.println("2. Change User Name    ");
        System.out.println("3. Change Password     ");
        System.out.println("4. Register New Account");
        System.out.println(Color.ANSI_GRAY_UNDERLINED + "0. Exit." + Color.ANSI_RESET);
    }

    public static void displayPlannerMenu() {
        System.out.println("1. Update Expired Stock        ");
        System.out.println("2. Update and Check Stock Level");
        System.out.println("3. Use Ingredient              ");
        System.out.println("4. Donate Product              ");
        System.out.println("5. Sell Product                ");
        System.out.println("6. Giveaway Product            ");
        System.out.println("7. Discard Food                ");
        System.out.println(Color.ANSI_GRAY_UNDERLINED + "0. Back to Main Menu" + Color.ANSI_RESET);
    }

    public static void displayReportMenu() {
        System.out.println("1. Ingredient Report ");
        System.out.println("2. Product Report    ");
        System.out.println(Color.ANSI_GRAY_UNDERLINED + "0. Back to Main Menu" + Color.ANSI_RESET);
    }

    public static void displayReportTypeMenu() {
        System.out.println("1. Annual Report  ");
        System.out.println("2. Monthly Report ");
        System.out.println("3. Daily Report   ");
        System.out.println("4. Status Report  ");
        System.out.println(Color.ANSI_GRAY_UNDERLINED + "0. Back to Report Menu" + Color.ANSI_RESET);
    }

    public static void displayBeneficiaryMenu() {
        System.out.println("1. View Beneficiary List");
        System.out.println("2. Add Beneficiary      ");
        System.out.println("3. Search Beneficiary   ");
        System.out.println("4. Modify Beneficiary   ");
        System.out.println("5. Delete Beneficiary   ");
        System.out.println("6. View Donation List   ");
        System.out.println("7. Search Donation List ");
        System.out.println(Color.ANSI_GRAY_UNDERLINED + "0. Back to Main Menu" + Color.ANSI_RESET);
    }

    // let user chose which eventNo he/she wants to perform
    // assumption: the menu will be listed in numbered sentence
    // 				each number between the range has an eventNo
    public static int askEventNo(int beginEventNo, int endEventNo) {
        if (beginEventNo > endEventNo) {
            System.out.printf("%nBug at reuse.UI.askEventNo(int, int): the beginEventNo should not greater than the endEventNo.");
            System.exit(1);
        }

        int eventNo;
        final String errorMessage = "Sorry, input failed. Please enter the number of option you want to perform.\n";

        while (true) {
            try {
                System.out.printf(Color.ANSI_CYAN_BRIGHT + "%n%nPlease select> " + Color.ANSI_RESET);
                eventNo = Integer.parseInt(SingleObject.scanner.nextLine());

                if (eventNo >= beginEventNo && eventNo <= endEventNo) {
                    break;
                } else {
                    System.out.print(Color.ANSI_RED_BOLD + errorMessage + Color.ANSI_RESET);
                }
            } catch (NumberFormatException e) {
                System.out.print(Color.ANSI_RED_BOLD + errorMessage + Color.ANSI_RESET);
                // 1. Apologise, the application should accept the responsibility for the problem
                // 2. What happened (what went wrong / the problem, why / the cause)
                // 3. How to fix it (where to find the bug / the solution)
            }
        }
        return eventNo;
    }
}
