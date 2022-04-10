package boundary;

import entity.TimeSlot;

public class ViewSlotsUI {
	public static TimeSlot askTimeSlot(){
		displayTimeSlot();
		int choice = ConsoleInput.askChoice(1, 13, "Select time slot");

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
			default -> SLOT_14; // case 14
		};
	}	
	
	public static void displayTimeSlot(){
		System.out.println(" 1. 0800 AM");
		System.out.println(" 2. 0830 AM");
		System.out.println(" 3. 0900 AM");
		System.out.println(" 4. 0930 AM");
		System.out.println(" 5. 1000 AM");
		System.out.println(" 6. 1030 AM");
		System.out.println(" 7. 1100 AM");
		System.out.println(" 8. 1130 AM");
		System.out.println(" 9. 0200 PM");
		System.out.println("10. 0230 PM");
		System.out.println("11. 0300 PM");
		System.out.println("12. 0330 PM");
		System.out.println("13. 0400 PM");
		System.out.println("14. 0430 PM");
	}
}
