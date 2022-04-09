package boundary;


// display message on the screen

public class ConsoleUI { // UI: user interface
	// only static variable can be used in static method
	private static final int headingWidth = 50; // the number of characters

	// clear screen
	public static void clearScreen() {
		System.out.printf("%n[Enter] to continue...");
		 SingletonScanner.scanner.nextLine(); // pause, wait for user to continue
		// the number of lines
		int screenHeight = 22;
		for (int i = 0; i < screenHeight; ++i) {
			 System.out.println();
		 }
	}

	// display the program name and menu name
	public static void displaySystemName(String heading) {
		int field = headingWidth - heading.length();

		for(int i = 0; i < headingWidth; ++i) {
			System.out.print('#');
		}
		System.out.printf("%n%n");

		for(int i = 0; i < field / 2; ++i) { // display the heading in the center
			System.out.print(' ');
		}
		System.out.print(heading);
		for(int i = 0; i < field / 2; ++i) {
			System.out.print(' ');
		}
		System.out.printf("%n%n");

		for(int i = 0; i < headingWidth; ++i) {
			System.out.print('#');
		}
		System.out.printf("%n%n");
	}

	public static void displayFunctionName(String heading) {
		int field = headingWidth - heading.length();

		System.out.printf("%n");

		for(int i = 0; i < field / 2; ++i) { // display the heading in the center
			System.out.print('-');
		}
		System.out.print(heading);
		for(int i = 0; i < field / 2; ++i) {
			System.out.print('-');
		}
		System.out.printf("%n%n");
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

	// let user chose which eventNo he/she wants to perform
	// assumption: the menu will be listed in numbered sentence each number between the range has an eventNo
	public static int askEventNo(int beginEventNo, int endEventNo) throws IllegalArgumentException {
		if (beginEventNo > endEventNo) {
			throw new IllegalArgumentException();
		}

		int eventNo;
		final String errorMessage = "Sorry, input failed. Please enter the number of choice you want.%n";

		while (true) {
			try {
				System.out.printf("%n> ");
				eventNo = Integer.parseInt(SingletonScanner.scanner.nextLine());

				if(eventNo >= beginEventNo && eventNo <= endEventNo) {
					break;
				} else {
					System.out.print(errorMessage);
				}
			}
			catch(NumberFormatException e) {
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
	public static char askEventNo2(char beginEventNo, char endEventNo) throws IllegalArgumentException{
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
				System.out.printf("%n> ");
				input = SingletonScanner.scanner.nextLine();
				if(input.length() == 1 && Character.isLetter(input.charAt(0))) {
					eventNo = input.charAt(0);
					isChar = true;
				}
				isInRange = eventNo >= beginEventNo && eventNo <= endEventNo; // write after the value is known
				if(!isInRange) {
					System.out.println("Sorry, input failed. Please enter the letter of the corresponding action you want to perform.");
				}
			} catch(NumberFormatException e) {
				System.out.println("Sorry, input failed. Please enter the letter of the corresponding action you want to perform.");
				// 1. Apologize, the application should accept the responsibility for the problem
				// 2. What happened (what went wrong / the problem, why / the cause)
				// 3. How to fix it (where to find the bug / the solution)
			}
		} while(!isChar || !isInRange);
		return eventNo;
	}
}
