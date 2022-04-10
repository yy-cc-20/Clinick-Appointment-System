package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeSlot {
	private static final String pattern = "dd/MM/yyyy"; // date format
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
	private LocalDate date;
	private List<Slot> slotList;

	public TimeSlot(String date){
		this.date = LocalDate.parse(date, formatter);
//		this.slotList =
	}

	public List<Slot> getSlotList() {
		return slotList;
	}
}
