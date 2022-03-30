enum TimeSlot {

	// in file    | display as
	// "SLOT_1"   | "0800AM"
	// toString() | getTime()
	
	// 1 slot = 30 mins
	SLOT_1("0800AM"),
	SLOT_2("0830AM"),
	SLOT_3("0900AM"),
	SLOT_4("
	SLOT_5
	// to be continue
	SLOT_13("0430PM");

	private final String time;

	private TimeSlot(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}
}
