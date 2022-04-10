package entity;

import boundary.ConsoleUI;

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

	static void displayAttendanceChoice(){
		System.out.printf("%nSelect attendance.%n%n");
		System.out.println("1. Attended");
		System.out.println("2. Absent");
		System.out.println("3. NAN");
	}

	public static Attendance askAttendance(){
		displayAttendanceChoice();
		int choice = ConsoleUI.askEventNo(1, 3);

		return switch (choice) {
			case 1 -> Attendance.ATTENDED;
			case 2 -> Attendance.ABSENT;
			default -> Attendance.NAN; // case 3
		};
	}
}
