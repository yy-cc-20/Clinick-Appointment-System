package entity;

import boundary.ConsoleInput;
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

    public static Attendance askAttendance() {
        ConsoleUI.displayAttendanceChoice();
        int choice = ConsoleInput.askChoice(1, 3, "Select attendance");

        return switch (choice) {
            case 1 -> ATTENDED;
            case 2 -> ABSENT;
            default -> NAN; // case 3
        };
    }
}
