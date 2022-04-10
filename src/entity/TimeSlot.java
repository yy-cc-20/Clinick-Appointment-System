package entity;

import boundary.ConsoleUI;

public enum TimeSlot {

	// in file | display as
	// "SLOT_1" | "0800AM"
	// toString() | getTime()

	// 1 slot = 30 mins
	SLOT_1("0800AM"),
	SLOT_2("0830AM"),
	SLOT_3("0900AM"),
	SLOT_4("0930AM"),
	SLOT_5("1000AM"),
	SLOT_6("1030AM"),
	SLOT_7("1100AM"),
	SLOT_8("1130AM"),
	SLOT_9("0200PM"),
	SLOT_10("0230PM"),
	SLOT_11("0300PM"),
	SLOT_12("0330PM"),
	SLOT_13("0400PM"),
	SLOT_14("0430PM");

	private final String time;

	TimeSlot(String time) {
		this.time = time;
	}

	// get the timeslot in String
	public String toString() {
		return time;
	}

	public static TimeSlot askTimeSlot(){
		displayTimeSlot();
		int choice = ConsoleUI.askEventNo(1, 13);

		return switch (choice) {
			case 1 -> SLOT_1;
			case 2 -> SLOT_2;
			case 3 -> SLOT_3;
			case 4 -> SLOT_4;
			case 5 -> SLOT_5;
			case 6 -> SLOT_6;
			case 7 -> SLOT_7;
			case 8 -> SLOT_8;
			case 9 -> SLOT_9;
			case 10 -> SLOT_10;
			case 11 -> SLOT_11;
			case 12 -> SLOT_12;
			case 13 -> SLOT_13;
			default -> SLOT_14; // case 3
		};
	}

	public static void displayTimeSlot(){
		System.out.printf("%nSelect time slot.%n%n");
		System.out.println("1. 0800AM");
		System.out.println("2. 0830AM");
		System.out.println("3. 0900AM");
		System.out.println("4. 0930AM");
		System.out.println("5. 1000AM");
		System.out.println("6. 1000AM");
		System.out.println("7. 1100AM");
		System.out.println("8. 1130AM");
		System.out.println("9. 0200PM");
		System.out.println("10. 0230PM");
		System.out.println("11. 0300PM");
		System.out.println("12. 0330PM");
		System.out.println("13. 0400PM");
		System.out.println("14. 0430PM");
	}
	
}
