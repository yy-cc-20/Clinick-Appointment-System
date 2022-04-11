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

	List<Service> services = DataList.getInstance().getServiceList();
	List<Branch> branches = DataList.getInstance().getBranchList();
	
	// Filters
	int serviceId;
	int branchId;
	LocalDate date;
	
	// Result
	Doctor[][] availableDoctors; 
	// index: the time slot
	// row: an array of the doctors available for that time
	
	// Exit if the user do not want to continue view the avilable time slots
	/** @return false if the user did not continue to view the available time slots */
	public boolean viewSlots() { 
		
		viewService();
		if (!ConsoleInput.askBoolean("Select branch"))
			return false;
		serviceId = ConsoleInput.askChoice(1, services.size(), "Service");
		
		
		viewBranchFilteredByService();
		if (!ConsoleInput.askBoolean("Select branch"))
			return false;
		branchId = ConsoleInput.askChoice(1, services.size(), "Select branch");
		
		if (!ConsoleInput.askBoolean("Select date"))
			return false;
		date = ConsoleInput.askDate("Date");
		
		viewTimeSlotFilteredByServiceBranchDate();
		return true;
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
	
	public Doctor[][] getAvailableDoctors() {
		return availableDoctors;
	}
    
	private void viewService() {
		Service service;
        ConsoleUI.displayTableName("All Services");
        System.out.println();
        System.out.println("No \t| Service \t| Price \\t| Description \t|");
        
        for (int i = 0; i < services.size(); i++) {
            service = services.get(i);
            System.out.println((i+1) + " \t| " + service.getServiceName() + " \t| " 
            		+ service.getPrice() + " \t| " 
                    + service.getDescription());
        }
	}

	private void viewBranchFilteredByService() {
		List<Branch> branchResult = ViewSlotsController.getBranchFilteredByService(serviceId);		
		
		ConsoleUI.displayTableName(services.get(serviceId).getServiceName());
        System.out.println();
        System.out.println("No \t| Branch Name \t|Telephone No \t| Branch Address \t|  ");
        
        for (int i = 0; i < branchResult.size(); i++) {
            System.out.println((i+1) + " \t| " 
            				+ branchResult.get(i).getBranchName() + " \t| " 
            				+ branchResult.get(i).getTelNo() +  " \t| "
            				+ branchResult.get(i).getBranchAddress() + " \t| ");
        }
	}
	
	private void viewTimeSlotFilteredByServiceBranchDate() {
		availableDoctors = ViewSlotsController.getAvailableDoctors(serviceId, branchId, date);
		// index: the time slot
		// value: the slots available for that time
		
		ConsoleUI.displayTableName(services.get(serviceId).getServiceName());
		ConsoleUI.displayTableName("at " + branches.get(branchId).getBranchName());
		ConsoleUI.displayTableName("on " + date.format(ConsoleUI.DATE_OUTPUT_FORMATTER));
		System.out.println();
		System.out.println("No \t| Start Time \t| Slots");
        for (TimeSlot slot : TimeSlot.values()) {
            int i = slot.ordinal() + 1;
            System.out.println(i + " \t| " 
            				+ slot + " \t| " 
            				+ availableDoctors[slot.ordinal()].length);
        }
	}
}
