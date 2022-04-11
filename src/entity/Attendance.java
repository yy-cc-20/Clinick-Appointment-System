package entity;

import boundary.ConsoleInput;

public enum Attendance {
  	ATTENDED("Attended"),
	ABSENT("Absent"),
	NAN("NAN");

	private final String attendance;

	Attendance(String attendance) {
		this.attendance = attendance;
	}

	// get the attendance in String
	public String toString() {
		return attendance;
	}

	// todo need to move this in to boundary class?
	static void displayAttendanceChoice(){
		System.out.printf("%nSelect attendance.%n%n");
		System.out.println("1. Attended");
		System.out.println("2. Absent");
		System.out.println("3. NAN");
	}

	public static Attendance askAttendance(){
		displayAttendanceChoice();
		int choice = ConsoleInput.askChoice(1, 3, "Select attendance");

		return switch (choice) {
			case 1 -> ATTENDED;
			case 2 -> ABSENT;
			default -> NAN; // case 3
		};
	}
}
