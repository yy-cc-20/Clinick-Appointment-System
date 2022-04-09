public enum Attendance {
  	ATTENDED("Attended"),
	ABSENT("Absent"),
	NAN("NAN");

	private final String attendance;

	Attendance(String attendance) {
		this.attendance = attendance;
	}

	public String getAttendance() {
		return attendance;
	}
}
