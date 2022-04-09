enum TimeSlot {

	// in file    | display as
	// "SLOT_1"   | "0800AM"
	// toString() | getTime()
	
	// 1 slot = 30 mins
	SLOT_1("0800AM"),
	SLOT_2("0830AM"),
	SLOT_3("0900AM");


	private final String time;

	private TimeSlot(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}
}
