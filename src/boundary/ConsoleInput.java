package boundary;

// Get console input with error handling and restriction
// To ask for input of a specific data type
// @param info the text that will be shown in the user interface

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConsoleInput {
    private static final String STRING_ERROR = "Sorry, cannot contain \";\".";
    private static final String ERROR_MESSAGE1 = "Sorry, cannot greater than 5 digits.";
    
    public static final DateTimeFormatter DATE_INPUT_FORMATTER = DateTimeFormatter.ofPattern("d-M-yyyy"); // Input date format, "1-1-2022" and "01-01-2022" are both acceptable
    // There is another format for output in ConsoleUI
    
    // Ask user the date, set the valid date
    public static LocalDate askDate(String info) {
        String stringDate;
        while (true) {
            System.out.print(info + " (dd/mm/yyyy) > ");
            try {
                stringDate = SingletonScanner.scanner.nextLine();
                return LocalDate.parse(stringDate, DATE_INPUT_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.printf("%n%s%n", "Sorry, please enter a valid date.");
            }
        }
    }
    
    // Ask user the date, set the valid date
    // Only accept date after today
    public static LocalDate askDateNoEarlierThanToday(String info) {
        String stringDate;
        LocalDate date;
        while (true) {
            System.out.print("%n" + info + " (dd/mm/yyyy) > "); // the "%n" in dateName will not take effect if you use %s
            try {
                stringDate = SingletonScanner.scanner.nextLine();
                date = LocalDate.parse(stringDate, DATE_INPUT_FORMATTER);
                if (date.isAfter(LocalDate.now().minusDays(1)))
                	return date;
                else
                	System.out.printf("%n%s%n", "Sorry, please select a date no earlier than today.");
            } catch (DateTimeParseException e) {
                System.out.printf("%n%s%n", "Sorry, please enter a valid date.");
            }
        }
    }
    
    // let user chose which eventNo he/she wants to perform
    // assumption: the menu will be listed in numbered sentence each number between the range has an eventNo
    public static int askChoice(int beginChoiceNo, int endChoiceNo, String info) throws IllegalArgumentException {
        if (beginChoiceNo > endChoiceNo) {
            throw new IllegalArgumentException();
        }

        int eventNo;
        final String errorMessage = "Sorry, input failed. Please enter the number of choice you want.%n";

        while (true) {
            try {
                System.out.printf("%n%s> ", info);
                eventNo = Integer.parseInt(SingletonScanner.scanner.nextLine());

                if (eventNo >= beginChoiceNo && eventNo <= endChoiceNo) {
                    break;
                } else {
                    System.out.print(errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.print(errorMessage);
                // 1. Apologise, the application should accept the responsibility for the problem
                // 2. What happened (what went wrong / the problem, why / the cause)
                // 3. How to fix it (where to find the bug / the solution)
            }
        }
        return eventNo;
    }

    // let user chose which eventNo he/she wants to perform
    // return the valid input
    // assumption: the menu will be listed in sentence
    // 				index is alphabet
    // 				each alphabet between the range has an eventNo
    public static char askChoice2(char beginEventNo, char endEventNo, String info) throws IllegalArgumentException {
        char eventNo = '?';
        String input = "";
        boolean isChar = false;
        boolean isInRange = eventNo >= beginEventNo && eventNo <= endEventNo; // to form readable code

        if (beginEventNo > endEventNo) {
            throw new IllegalArgumentException();
        }

        do {
            try {
                isChar = false;
                System.out.printf("%n%s> ", info);
                input = SingletonScanner.scanner.nextLine();
                if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                    eventNo = input.charAt(0);
                    isChar = true;
                }
                isInRange = eventNo >= beginEventNo && eventNo <= endEventNo; // write after the value is known
                if (!isInRange) {
                    System.out.println("Sorry, input failed. Please enter the letter of the corresponding action you want to perform.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Sorry, input failed. Please enter the letter of the corresponding action you want to perform.");
                // 1. Apologize, the application should accept the responsibility for the problem
                // 2. What happened (what went wrong / the problem, why / the cause)
                // 3. How to fix it (where to find the bug / the solution)
            }
        } while (!isChar || !isInRange);
        return eventNo;
    }
    
    // @return 0 or positive int
    public static int askPositiveInt(String info) {
        int input;
        String errorMessage = "Please enter a positive integer.";

        while (true) {
            System.out.printf("%n%s> ", info);
            try {
                input = Integer.parseInt(SingletonScanner.scanner.nextLine());
                if (input < 0) {
                    System.out.println(errorMessage);
                } else if (input > 99999) {
                    System.out.printf("%s%n%n", ERROR_MESSAGE1);
                } else {
                    return input;
                }
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }
    
    /** @return int */
    public static int askInt(String info) {
        int input;
        String errorMessage = "Please enter a number.";

        while (true) {
            System.out.printf("%s> ", info);
            try {
                input = Integer.parseInt(SingletonScanner.scanner.nextLine());
                return input;
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
            System.out.printf("%n%s> ", info);
            try {
                input = Double.parseDouble(SingletonScanner.scanner.nextLine());
                if (input < 0) {
                    System.out.println(errorMessage);
                } else if (input > 99999) {
                    System.out.printf("%s%n%n", ERROR_MESSAGE1);
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
            System.out.printf("%n%s> ", info);
            input = SingletonScanner.scanner.nextLine();
            if (hasDelimiter(input)) {
                System.out.printf("%s%n", STRING_ERROR);
            } else {
                return input.equals("") ? "-" : input;
            }
        }
    }
    
    // @return null if user enters ENTER key 
    public static String askStringV2(String info) {
        String input;

        while (true) {
            System.out.printf("%n%s> ", info);
            input = SingletonScanner.scanner.nextLine();
            if (input.equals("\n"))
                return null;
        }
    }

    // used to ask the name cannot be null
    public static String askShortString(String info, int length) {
        String input;
        while (true) {
            System.out.printf("%n%s> ", info);
            input = SingletonScanner.scanner.nextLine();
            if (hasDelimiter(input)) {
                System.out.printf("%s%n", STRING_ERROR);
            } else if (input.equals("")) {
                System.out.println("PLease enter again.");
            } else if (input.length() > length) {
                System.out.printf("Sorry, cannot contain more than %d characters.%n", length);
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
            System.out.printf("%n%n%s%s ", info, " [y/n]?>");
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
