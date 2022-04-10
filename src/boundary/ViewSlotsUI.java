package boundary;

import java.time.LocalDate;
import java.util.List;

import controller.ViewSlotsController;
import entity.*;

/*
 * Display available time slot filtered by service, branch and date.
 * Did not let the user select the desired time slot
 * Provide information of the selected service, selected branch, selected date and available time slots.
 */

public class ViewSlotsUI {
	// TODO calculate the column width according to the length of the info in order to display the table more neatly
	private final ViewSlotsController controller = new ViewSlotsController();	
	
	List<Service> services;
	List<Branch> branches;
	
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
		System.out.println(ConsoleUI.CANCEL_OPERATION);
		
		viewService();
		serviceId = ConsoleInput.askChoice(1, services.size(), "Select service");
		
		viewBranchFilteredByService();
		branchId = ConsoleInput.askChoice(1, services.size(), "Select branch");
		
		date = ConsoleInput.askDate("Date");
		
		viewTimeSlotFilteredByServiceBranchDate();
	}
	
	public int getSelectedServiceId() {
		return serviceId;
	}
	
	public int getSelectedBranchId() {
		return branchId;
	}
	
	public LocalDate getSelectedDate() {
		return date;
	}
	
	public int[] getAvailableTimeSlots() {
		return availableTimeSlots;
	}
    
	private void viewService() {
		+----+---------+-------+-------------+
		| No | Service | Price | Description |
		+----+---------+-------+-------------+
		
		
        System.out.println("Available services:");
        System.out.println();
        System.out.println("No \t| Service \t| Price \\t| Description \t| Estimated Time (hr)");
        
        for (int i = 0; i < allocations.size(); i++) {
            allocation = allocations.get(i);
            System.out.println((i+1) + " \t| " + allocation.getService().getServiceName() + " \t| " + allocation.getService().getServiceId() + " \t| "
                    + allocation.getBranch().getBranchAddress() + " \t| "
                    + allocation.getBranch().getTelNo() + " \t| "
                    + allocation.getService().getDescription() + allocation.getService().getPrice() + allocation.getService().getTimeSlotRequired());
        }
	}

	private void viewBranchFilteredByService() {
		List<Branch> branchResult = controller.getBranchFilteredByService(serviceId);		
		
		System.out.printf("%n>" + services.get(serviceId).getName() + "<%n");
        System.out.println();
        System.out.println("No \t| Branch Name \t|Telephone No \t| Branch Address \t|  ");
        
        for (int i = 0; i < allocations.size(); i++) {
            allocation = allocations.get(i);
            System.out.println((i+1) + " \t| " + allocation.getService().getServiceName() + " \t| " + allocation.getService().getServiceId() + " \t| "
                    + allocation.getBranch().getBranchAddress() + " \t| "
                    + allocation.getBranch().getTelNo() + " \t| "
                    + allocation.getService().getDescription() + allocation.getService().getPrice() + allocation.getService().getTimeSlotRequired());
        }
	}
	
	private void viewTimeSlotFilteredByServiceBranchDate() {
		availableTimeSlots = controller.getAvailableTimeSlot(serviceId, branchId, date);
		// index: the time slot
		// value: the slots available for that time

		System.out.println(">" + services.get(serviceId).getName() + " AT " + branches.get(branchId).getBranchName() + " ON " + date.format(ConsoleUI.DATE_OUTPUT_FORMATTER));
		System.out.println("No \t| Start Time \t| Slots");
        for (TimeSlot slot : TimeSlot.values()) {
            int i = slot.ordinal() + 1;
            System.out.println(i + " \t| " + slot + " \t| " + availableTimeSlots[slot.ordinal()]);
        }
	}
}
