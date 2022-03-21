enum TimeSlot {

	// in file    | display as
	// "S1"       | "08:00AM"
	// toString() | getTime()

	S1("0800AM"),
	S2("0830AM"),
	S3("0900AM"),
	S4("
	S5
	// to be continue
	S24("07:30PM");

	private final String time;

	private TimeSlot(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}
}
