package presentation;

import java.time.LocalDate;
import java.util.List;

import applicationLogic.ViewSlotsController;
import domain.*;

/*
 * Display available time slot filtered by service, branch and date.
 * Did not let the user select the desired time slot
 * Provide information of the selected service, selected branch, selected date and available time slots.
 *
 * Can be used by MakeAppointmentUI & CancelAppointmentUI
 */

public class ViewSlotsUI {
    // TODO To have column size determined by the longest info in the column so to display the table neatly

//    private static ViewSlotsUI instance = new ViewSlotsUI();
    private ViewSlotsController controller;

    // Filters
    private int serviceId;
    private int branchId;
    private LocalDate date;

    // Results
//    private List<Branch> branchResults;
    private String serviceName;
    private int requiredSlots;
//    private List<List<Integer>> availableDoctors;

    // index: the time slot
    // row: an array of the doctors available for that time

    public ViewSlotsUI() {
        controller = new ViewSlotsController();
    }


    // Can be used by MakeAppointmentUI
    // Exit if the user do not want to continue view the available time slots

    /**
     * @return false if the user did not continue to view the available time slots
     * or no result was found
     * @return true if the user wants to select a time slot
     */
    

    public boolean viewSlots() {
        int servicesFound;
        int branchesFound;
        DataList dataList = new DataList();
        List<Service> services = dataList.getServiceList();
        List<Branch> branches = dataList.getBranchList();

        while (true) {

            servicesFound = viewService();
            if (servicesFound <= 0)
                return false;

            // validate selected service's ID
            while (true) {
                if (!ConsoleInput.askBoolean("Select a service to continue"))
                    return false;
                serviceId = ConsoleInput.askPositiveInt("Service ID");
                if (controller.validateSelectedServiceId(services, serviceId)){
                    Service service = controller.findServiceFromId(services, serviceId);
                    serviceName =service.getServiceName();
                    requiredSlots = service.getTimeSlotRequired();
                    branchesFound = viewBranchFilteredByService().size();
                    break;
                }
                else
                	System.out.println("Invalid input.");
            }

            if (branchesFound <= 0) {
                if (ConsoleInput.askBoolean("Continue searching"))
                    continue;
                else
                    return false;
            }

            // validate selected branch's Id
            while (true) {
                if (!ConsoleInput.askBoolean("Select a branch to continue"))
                    if (ConsoleInput.askBoolean("Continue searching"))
                        continue;
                    else
                        return false;
                branchId = ConsoleInput.askPositiveInt("Branch ID");

                if (controller.validateSelectedBranchId(branches, branchId))
                    break;
                else
                    System.out.println("Invalid input.");
            }

            System.out.println("\n\nEnter a date to view the available time slot.");
            date = ConsoleInput.askDateNoEarlierThanToday("Booking date");
            viewTimeSlotFilteredByServiceBranchDate();
            if (!ConsoleInput.askBoolean("Continue viewing services and time slots"))
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

    public int getRequiredSlots() {
    	return requiredSlots;
    }
    
    public List<List<Integer>> getAvailableDoctors() {
        return controller.getAvailableDoctors(serviceId, branchId, date, requiredSlots);
    }

    // Can be used by ChangeAppointmentUI

    /**
     * @return number of row of results
     */
    public int viewService() {
        DataList dataList = new DataList();
        List<Service> services = dataList.getServiceList();

        if (services.size() == 0) {
            System.out.println("No service found.");
            return services.size();
        }

        Service service;
        ConsoleUI.displayTableName("All Services");
        System.out.println();
        System.out.printf("%s\t\t| %-40s| %-15s |%n", "ID", "Service", "Price");

        for (Service value : services) {
            service = value;
            System.out.printf("%d\t\t| %-40s| RM%-15.2f| \n\t%s |%n%n", service.getServiceId(),
                    service.getServiceName(),
                    service.getPrice(),
                    service.getDescription());
        }
        return services.size();
    }

    // Can be used by ChangeAppointmentUI
    public List<Branch> viewBranchFilteredByService() {
        List<Branch> branchResults = controller.getBranchFilteredByService(serviceId);

        if (branchResults.size() == 0) {
            System.out.println("No branch found.");
            return branchResults;
        }

        ConsoleUI.displayTableName(serviceName);
        System.out.println();
        System.out.printf("%s\t\t| %-50s| %-18s |%n", "ID", "Branch Name", "Telephone No");


        for (Branch branchResult : branchResults) {
            System.out.printf("%d\t\t| %-50s| %-18s | \n\t\t  %s |%n%n", branchResult.getBranchId(),
                    branchResult.getBranchName(),
                    branchResult.getTelNo(),
                    branchResult.getBranchAddress());
        }
        return branchResults;
    }

    // Can be used by ChangeAppointmentUI
    public void viewTimeSlotFilteredByServiceBranchDate() {
        List<List<Integer>> availableDoctors = controller.getAvailableDoctors(serviceId, branchId, date, requiredSlots);
        List<Branch> branchResults = controller.getBranchFilteredByService(serviceId);
        // index: the time slot number
        // value: the slots available for that time
        System.out.println();
        System.out.println();
        ConsoleUI.displayTableName("Available Time Slots for " + serviceName);
        ConsoleUI.displayTableName("At " + controller.findBranchNameFromId(branchResults, branchId));
        ConsoleUI.displayTableName("On " + date.format(ConsoleUI.DATE_OUTPUT_FORMATTER));
        System.out.println();
        System.out.println("No \t| Start Time \t\t| Slots |");
        for (TimeSlot slot : TimeSlot.values()) {
            int i = slot.ordinal() + 1;
            System.out.println(i + " \t| "
                    + slot + " \t\t| "
                    + availableDoctors.get(slot.ordinal()).size() + " \t| ");
        }

        System.out.println();

        System.out.println("The estimated time required for this service is "
                + timeSlotsToHour(requiredSlots) + " hr(s).");
    }

    public static double timeSlotsToHour(int slots) {
        return slots / 2.0;
    }

//    // ViewSlotsUI test
//    public static void main(String[] args) {
//        ViewSlotsUI
//        .getInstance()
//        .viewSlots();
//    }
}
