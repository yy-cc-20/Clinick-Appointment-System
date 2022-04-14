package entity;

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
}
