package entity;

import boundary.ConsoleUI;

public enum TimeSlot {

	// in file | display as
	// "SLOT_1" | "0800AM"
	// toString() | getTime()

	// 1 slot = 30 mins
	SLOT_1("0800AM", 1),
	SLOT_2("0830AM", 2),
	SLOT_3("0900AM", 3),
	SLOT_4("0930AM", 4),
	SLOT_5("1000AM", 5),
	SLOT_6("1030AM", 6),
	SLOT_7("1100AM", 7),
	SLOT_8("1130AM", 8),
	SLOT_9("0200PM", 9),
	SLOT_10("0230PM", 10),
	SLOT_11("0300PM", 11),
	SLOT_12("0330PM", 12),
	SLOT_13("0400PM", 13),
	SLOT_14("0430PM", 14);

	private final String time;
	private final int no;

	TimeSlot(String time, int no) {
		this.time = time;
		this.no = no;
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
