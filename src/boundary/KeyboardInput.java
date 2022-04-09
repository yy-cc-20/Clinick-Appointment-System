package boundary;

// scanner with error handling and conditions
// to ask for attribute of a specific data type
// @param is the text that will be shown in the user interface

public class KeyboardInput {
    private static final String stringError = "Sorry, cannot contain \";\".";
    private static final String errorMessage1 = "Sorry, cannot greater than 5 digits.";

    // @return 0 or positive int
    public static int askPositiveInt(String info) {
        int input;
        String errorMessage = "Please enter a positive integer.";

        while (true) {
            System.out.printf("%nEnter %s: ", info);
            try {
                input = Integer.parseInt(SingletonScanner.scanner.nextLine());
                if (input < 0) {
                    System.out.println(errorMessage);
                } else if (input > 99999) {
                    System.out.printf("%s%n%n", errorMessage1);
                } else {
                    return input;
                }
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    // @return 0 or positive double
    public static double askPositiveDouble(String info) {
        double input;
        String errorMessage = "Please enter a positive number.";

        while (true) {
            System.out.printf("%nEnter %s: ", info);
            try {
                input = Double.parseDouble(SingletonScanner.scanner.nextLine());
                if (input < 0) {
                    System.out.println(errorMessage);
                } else if (input > 99999) {
                    System.out.printf("%s%n%n", errorMessage1);
                } else {
                    return input;
                }
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    // @return "-" if input is null
    public static String askString(String info) {
        String input;

        while (true) {
            System.out.printf("%nEnter %s: ", info);
            input = SingletonScanner.scanner.nextLine();
            if (hasDelimiter(input)) {
                System.out.printf("%s%n", stringError);
            } else {
                return input.equals("") ? "-" : input;
            }
        }
    }
    
    // @return null if user enters ENTER key 
    public static String askStringV2(String info) {
        String input;

        while (true) {
            System.out.printf("%nEnter %s: ", info);
            input = SingletonScanner.scanner.nextLine();
            if (input.equals("\n"))
                return null;
        }
    }

    // used to ask the name cannot be null
    public static String askName(String info, int length) {
        String input;
        while (true) {
            System.out.printf("%nEnter %s: ", info);
            input = SingletonScanner.scanner.nextLine();
            if (hasDelimiter(input)) {
                System.out.printf("%s%n", stringError);
            } else if (input.equals("")) {
                System.out.println("PLease enter a name.");
            } else if (input.length() > length) {
                System.out.printf("Sorry, cannot contain more than %d characters for name.%n", length);
            } else {
                break;
            }
        }
        return input;
    }

    // used to ask the password
    public static String askPassword(String info, int length) {
        String input;
        while (true) {
            System.out.printf("%nEnter %s: ", info);
            input = SingletonScanner.scanner.nextLine();
            if (hasDelimiter(input)) {
                System.out.printf("%s%n", stringError);
            } else if (input.equals("")) {
                System.out.println("PLease enter a password.");
            } else if (input.length() > length) {
                System.out.printf("Sorry, cannot contain more than %d characters for password.%n", length);
            } else {
                break;
            }
        }
        return input;
    }

    // @return true if answer "yes"
    // @return false if answer "no"
    public static boolean askBoolean(String info) {
        String ans;
        while (true) {
            System.out.printf("%n%n%s%s", info, " (y/n)? ");
            ans = SingletonScanner.scanner.nextLine().toLowerCase();

            if (ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("yes")) {
                return true;
            } else if (ans.equalsIgnoreCase("n") || ans.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Please enter \"Y/y\" or \"N/n\".");
            }
        } // end of while
    }

    // input cannot contain delimiter
    // apply to String attribute
    // check if the String input contains delimiter used in the database
    public static boolean hasDelimiter(String text) {
        return text.contains(";");
    }
}
