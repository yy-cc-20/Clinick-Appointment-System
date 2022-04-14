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
 * Can be used by MakeAppointmentUI & CancelAppointmentUI
 */

public class ViewSlotsUI {
    // TODO To have column size determined by the longest info in the column so to display the table neatly

    private final static ViewSlotsUI instance = new ViewSlotsUI();
    private final ViewSlotsController controller = ViewSlotsController.getInstance();
    
    // List
    private List<Service> services = DataList.getServiceList();
    
    // Filters
    private int serviceId;
    private int branchId;
    private LocalDate date;

    // Results
    private List<Branch> branchResults;
    private String serviceName;
    private int requiredSlots;
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

    /**
     * @return false if the user did not continue to view the available time slots
     * or no result was found
     */
    public boolean viewSlots() {
        int servicesFound;
        int branchesFound;
        while (true) {
            ConsoleUI.clearScreen();
            servicesFound = viewService();
            if (servicesFound <= 0)
                return false;

            // validate selected service's ID
            while (true) {
                if (!ConsoleInput.askBoolean("Select service"))
                    return false;
                serviceId = ConsoleInput.askPositiveInt("Service");
                if (validateSelectedServiceId(serviceId))
                    break;
                else
                    System.out.println("Invalid input.");
            }

            findServiceNameRequiredSlotsFromId();
            branchesFound = viewBranchFilteredByService();
            if (branchesFound <= 0) {
                if (ConsoleInput.askBoolean("Continue searching"))
                    continue;
                else
                    return false;
            }

            // validate selected branch's Id
            while (true) {
                if (!ConsoleInput.askBoolean("Select branch"))
                    if (ConsoleInput.askBoolean("Continue searching"))
                        continue;
                    else
                        return false;
                branchId = ConsoleInput.askPositiveInt("Branch");
                if (validateSelectedBranchId(branchId))
                    break;
                else
                    System.out.println("Invalid input.");
            }

            date = ConsoleInput.askDateNoEarlierThanToday("Date");
            viewTimeSlotFilteredByServiceBranchDate();
            if (!ConsoleInput.askBoolean("Continue searching")) {
                return true;
            }
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

    /**
     * @return number of row of results
     */
    public int viewService() {
        
        if (services.size() == 0) {
            System.out.println("No service found.");
            return services.size();
        }

        Service service;
        ConsoleUI.displayTableName("All Services");
        System.out.println();
        System.out.println("No \t| Service \t| Price (RM) \t| Description \t|");

        for (Service value : services) {
            service = value;
            System.out.printf("%d\t|%s\t|%.2f\t|%s\t|%n", service.getServiceId(),
                    service.getServiceName(),
                    service.getPrice(),
                    service.getDescription());
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

        ConsoleUI.displayTableName(serviceName);
        System.out.println();
        System.out.println("No \t| Branch Name \t|Telephone No \t| Branch Address \t|  ");

        for (Branch branchResult : branchResults) {
            System.out.println(branchResult.getBranchId() + " \t| "
                    + branchResult.getBranchName() + " \t| "
                    + branchResult.getTelNo() + " \t| "
                    + branchResult.getBranchAddress() + " \t| ");
        }
        return branchResults.size();
    }

    // Can be used by ChangeAppointmentUI
    public void viewTimeSlotFilteredByServiceBranchDate() {
        //System.out.println("ViewSlotsUI.viewTimeSlotFilteredByServiceBranchDate testing");

        availableDoctors = controller.getAvailableDoctors(serviceId, branchId, date, requiredSlots);
        // index: the time slot number
        // value: the slots available for that time
        System.out.println();
        System.out.println();
        ConsoleUI.displayTableName("Available Time Slots For " + serviceName);
        ConsoleUI.displayTableName("At " + findBranchNameFromId());
        ConsoleUI.displayTableName("On " + date.format(ConsoleUI.DATE_OUTPUT_FORMATTER));
        System.out.println();
        System.out.println("No \t| Start Time \t| Slots |");
        for (TimeSlot slot : TimeSlot.values()) {
            int i = slot.ordinal() + 1;
            System.out.println(i + " \t| "
                    + slot + " \t| "
                    + availableDoctors.get(slot.ordinal()).size() + " \t| ");
        }

        System.out.println();

        System.out.println("The estimated time required for this service is "
                + timeSlotsToHour(requiredSlots) + " hr(s).");
    }

    public static double timeSlotsToHour(int slots) {
        return slots / 2.0;
    }

    public String findBranchNameFromId() {
        for (Branch b : branchResults)
            if (b.getBranchId() == branchId)
                return b.getBranchName();
        return "Unknown Branch";
    }

    public void findServiceNameRequiredSlotsFromId() {
        for (Service s : services)
            if (s.getServiceId() == serviceId) {
                serviceName = s.getServiceName();
                requiredSlots = s.getTimeSlotRequired();
            }
    }

    // Check if the id is exists
    public boolean validateSelectedBranchId(int id) {
        for (Branch b : branchResults)
            if (b.getBranchId() == id)
                return true;
        return false;
    }

    // Check if the id is exists
    public boolean validateSelectedServiceId(int id) {
        for (Service s : services)
            if (s.getServiceId() == id)
                return true;
        return false;
    }

//    // ViewSlotsUI test
//    public static void main(String[] args) {
//        System.out.println(timeSlotsToHour(1));
//        System.out.println(timeSlotsToHour(2));
//        System.out.println(timeSlotsToHour(3));
//        System.out.println(timeSlotsToHour(4));
//        System.out.println(timeSlotsToHour(5));
//
//        ViewSlotsUI.getInstance().viewSlots();
//    }
}
