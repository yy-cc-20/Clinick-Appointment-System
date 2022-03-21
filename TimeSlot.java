enum TimeSlot {

	// in file    | display as
	// "S1"       | "08:00AM - 08:30AM"
	// toString() | getTime()

	S1("08:00AM - 08:30AM"),
	S2("08:30AM - 09:00AM"),
	S3("09:00AM - 09:30AM"),
	S4("
	S5
	// to be continue
	S24("07:30PM - 08:00PM");

	private final String time;

	private TimeSlot(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}
}