package boundary;

import java.time.LocalDate;
import java.util.List;

import entity.*;

/*
 * Display available time slot filtered by service, branch and date.
 * Did not let the user select the desired time slot
 * Provide information of the selected service, selected branch, selected date and available time slots.
 */

public class ViewSlotsUI {
	// TODO calculate the column width according to the length of the info in order to display the table more neatly
	
	// All information
	List<Service> services;
	List<Branch> branches;
	List<Doctor> doctors;
	List<Appointment> appointments;
	List<Allocation> allocations;
	
	// Filter
	int serviceId;
	int branchId;
	LocalDate date;
	
	// Result
	int[] availableTimeSlots; 
	// index: the time slot
	// value: the seats available for that time
	
	public ViewSlotsUI() {
		/*
		 * ([X] to back to the previous page)
			select service
			select branch
			select date
		 */
		viewService();
		System.out.println(ConsoleUI.CANCEL_OPERATION);
		serviceId = ConsoleInput.askChoice(1, services.size(), "Select service");
		
		System.out.printf("%n>" + services.get(serviceId).getName() + "<%n");
		viewBranchFilteredByService();
		System.out.println(ConsoleUI.CANCEL_OPERATION);
		branchId = ConsoleInput.askChoice(1, services.size(), "Select branch");
		
	}
	
	public int getSelectedServiceId() {
		return serviceId;
	}
	
	public int getSelectedBranchId() {
		return branchId;
	}
	
    

	public static void viewService() {
		ArrayList<Service> services
		+----+---------+-------+-------------+
		| No | Service | Price | Description |
		+----+---------+-------+-------------+
		
		List<Allocation> allocations = controller.getAllAllocations();
        // todo filter allocations
        Allocation allocation;

        System.out.println("Available services:");
        System.out.println();
        System.out.println(
                "No \t| Service \t| Service ID \t| Branch Address \t| Telephone No \t| Description \t| Price \t| Required Time Slot");
        for (int i = 0; i < allocations.size(); i++) {
            allocation = allocations.get(i);
            System.out.println((i+1) + " \t| " + allocation.getService().getServiceName() + " \t| " + allocation.getService().getServiceId() + " \t| "
                    + allocation.getBranch().getBranchAddress() + " \t| "
                    + allocation.getBranch().getTelNo() + " \t| "
                    + allocation.getService().getDescription() + allocation.getService().getPrice() + allocation.getService().getTimeSlotRequired());
        }

	}

	public static void viewBranchFilteredByService(int serviceId, ArrayList<Branch> branches, ArrayList<Allocation> allocations) {

		> service name <
		+----+--------+---------+---------+-------------+
		| No | Branch | Tel. No | Address | Description |
		+----+--------+---------+---------+-------------+
	}
	
	public static void viewTimeSlotFilteredByServiceBranchDate(int serviceId, int branchId, LocalDate date, ArrayList<Appointment> appointments) {
		availableTimeSlots = new int[14];
		
		 displayServices();
	        int choice = ConsoleInput.askPositiveInt("a service (1-15)");
	        LocalDate date = ConsoleInput.askDate("a date (DD/MM/YYYY)");
	        controller.getAvailableTimeSlots(choice, date);

	        System.out.println("Available time slots for service " + service.getServiceName());
	        System.out.println();
	        // todo: check the timeslot status
	        System.out.println("Slot No \t| Start Time \t| Status");
//	        for (TimeSlot slot : TimeSlot.values()) {
//	            int i = slot.ordinal() + 1;
//	            System.out.println(i + " \t| " + slot + " \t| " + status);
//	        }
	}
}
