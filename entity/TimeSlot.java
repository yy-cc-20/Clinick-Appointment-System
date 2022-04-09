
enum TimeSlot {

	// in file    | display as
	// "SLOT_1"   | "0800AM"
	// toString() | getTime()
	
	// 1 slot = 30 mins
	SLOT_1("0800AM"),
	SLOT_2("0830AM"),
	SLOT_3("0900AM"),
	SLOT_4("0930AM"),
	SLOT_5("1000AM"),
	SLOT_6("1030AM"),
	SLOT_7("1100AM"),
	SLOT_8("1130AM"),
	SLOT_9("0200PM"),
	SLOT_10("0230AM"),
	SLOT_11("0300PM"),
	SLOT_12("0330PM"),
	SLOT_13("0400PM"),
	SLOT_14("0430PM");
	

	private final String time;

	private TimeSlot(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}
}
