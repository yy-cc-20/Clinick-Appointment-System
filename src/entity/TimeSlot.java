package entity;

public enum TimeSlot {

	// in file | display as
	// "SLOT_1" | "0800AM"
	// toString() | getTime()

	// 1 slot = 30 mins
	SLOT_1("0800AM", 1),
	SLOT_2("0830AM", 2),
	SLOT_3("0900AM", 3),
	SLOT_4("0930AM", 4),
	SLOT_5("1000AM", 5),
	SLOT_6("1030AM", 6),
	SLOT_7("1100AM", 7),
	SLOT_8("1130AM", 8),
	SLOT_9("0200PM", 9),
	SLOT_10("0230PM", 10),
	SLOT_11("0300PM", 11),
	SLOT_12("0330PM", 12),
	SLOT_13("0400PM", 13),
	SLOT_14("0430PM", 14);

	private final String time;
	private final int no;

	TimeSlot(String time, int no) {
		this.time = time;
		this.no = no;
	}

	// get the timeslot in String
	public String toString() {
		return time;
	}
}
