package entity;

import java.time.LocalDate;
import java.util.List;

public class TimeSlot {
	private LocalDate date;
	private List<Slot> slotList;

	public TimeSlot(){

	}

	public List<Slot> getSlotList() {
		return slotList;
	}
}
