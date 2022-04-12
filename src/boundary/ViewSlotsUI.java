package boundary;

import java.time.LocalDate;
import java.util.List;

import controller.ViewSlotsController;
import entity.*;

/*
 * Display available time slot filtered by service, branch and date.
 * Did not let the user select the desired time slot
 * Provide information of the selected service, selected branch, selected date and available time slots.
 * 
 * Can be used by MakeAppointmentUI
 */

public class ViewSlotsUI {
	// TODO To have column size determined by the longest info in the column so to display the table neatly
	
	private final static ViewSlotsUI instance = new ViewSlotsUI();
	private List<Service> services = DataList.getInstance().getServiceList();
	private ViewSlotsController controller = ViewSlotsController.getInstance();
	
	// Filters
	private int serviceId;
	private int branchId;
	private LocalDate date;
	
	// Result
	private List<Branch> branchResults;
	private List<List<Integer>> availableDoctors; 
	// index: the time slot
	// row: an array of the doctors available for that time
	
	private ViewSlotsUI() {
		
	}
	
	public static ViewSlotsUI getInstance() {
		return instance;
	}
	
	// Can be used by MakeAppointmentUI
	// Exit if the user do not want to continue view the available time slots
	/** @return false if the user did not continue to view the available time slots 
	 * 		or no result was found
	 * */
	public boolean viewSlots() { 
		int servicesFound;
		int branchesFound;
		while (true) {
			servicesFound = viewService();
			if (servicesFound <= 0) {
				return false;
			}			
			if (!ConsoleInput.askBoolean("Select service"))
				return false;
			serviceId = ConsoleInput.askPositiveInt("Service");
			
			
			branchesFound = viewBranchFilteredByService();
			if (branchesFound <= 0) {
				if (ConsoleInput.askBoolean("Continue searching"))
					continue;
				else
					return false;
			}			
			if (!ConsoleInput.askBoolean("Select branch"))
				if (ConsoleInput.askBoolean("Continue searching"))
					continue;
				else
					return false;
			branchId = ConsoleInput.askPositiveInt("Branch");

			
			date = ConsoleInput.askDateNoEarlierThanToday("Date");
			viewTimeSlotFilteredByServiceBranchDate();
			if (ConsoleInput.askBoolean("Continue searching"))
				continue;
			else
				return true;
		}
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
	
	public List<List<Integer>> getAvailableDoctors() {
		// index: the time slot
		// row: an array of the doctors available for that time
		return availableDoctors;
	}
    
	// Can be used by ChangeAppointmentUI
	/** @return number of row of results */
	public int viewService() {
		if (services.size() == 0) {
			System.out.println("No service found.");
			return services.size();
		}
			
		Service service;
        ConsoleUI.displayTableName("All Services");
        System.out.println();
        System.out.println("No \t| Service \t| Price (RM) \t| Description \t|");
        
        for (int i = 0; i < services.size(); i++) {
            service = services.get(i);
            System.out.println(service.getServiceId() + " \t| " 
            		+ service.getServiceName() + " \t| " 
            		+ service.getPrice() + " \t| " 
                    + service.getDescription() + " \t| ");
        }
        return services.size();
	}

	// Can be used by ChangeAppointmentUI
	public int viewBranchFilteredByService() {
		branchResults = controller.getBranchFilteredByService(serviceId);		
		
		if (branchResults.size() == 0) {
			System.out.println("No branch found.");
			return branchResults.size();
		}
		
		ConsoleUI.displayTableName(services.get(serviceId).getServiceName());
        System.out.println();
        System.out.println("No \t| Branch Name \t|Telephone No \t| Branch Address \t|  ");
        
        for (int i = 0; i < branchResults.size(); i++) {
            System.out.println(branchResults.get(i).getBranchId() + " \t| " 
            				+ branchResults.get(i).getBranchName() + " \t| " 
            				+ branchResults.get(i).getTelNo() +  " \t| "
            				+ branchResults.get(i).getBranchAddress() + " \t| ");
        }
        return branchResults.size();
	}
	
	// Can be used by ChangeAppointmentUI
	public void viewTimeSlotFilteredByServiceBranchDate() {
		System.out.println("ViewSlotsUI.viewTimeSlotFilteredByServiceBranchDate testing");
		try {
		availableDoctors = controller.getAvailableDoctors(serviceId, branchId, date, services.get(serviceId).getTimeSlotRequired());
		// index: the time slot number
		// value: the slots available for that time
		} catch(Exception e) {
			e.printStackTrace();
		}
		ConsoleUI.displayTableName("Available Time Slots For " + services.get(serviceId).getServiceName());
		ConsoleUI.displayTableName("at " + branchResults.get(branchId).getBranchName());
		ConsoleUI.displayTableName("on " + date.format(ConsoleUI.DATE_OUTPUT_FORMATTER));
		System.out.println();
		System.out.println("No \t| Start Time \t| Slots \t|");
        for (TimeSlot slot : TimeSlot.values()) {
            int i = slot.ordinal() + 1;
            System.out.println(i + " \t| " 
            				+ slot + " \t| " 
            				+ availableDoctors.get(slot.ordinal()).size() + " \t| " );
        }
	}
	
	// ViewSlotsUI test
	public static void main(String[] args) {
		ViewSlotsUI.getInstance().viewSlots();
	}
}
