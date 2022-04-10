package boundary;

import java.util.List;

import entity.*;
import controller.MakeAppointmentController;

// view appointment
// search appointment
// make appointment
// view slots

public class MakeAppointmentUI {

    private final MakeAppointmentController controller = new MakeAppointmentController();
    private Appointment appointmentToBook;

    // view the appointments
    public void viewAppointment() {
        List<Appointment> theAppointments = controller.getAllAppointments();
        if (theAppointments.size() == 0) {
            System.out.println("No appointment to display.");
        }
        else {
            System.out.println(
                    "Appointment ID \t| Date \t| Time \t| Duration \t| Service \t| Branch \t| Patient ID \t" +
                            "| Patient \t| Doctor ID \t| Doctor \t| Attendance");
            for (Appointment theAppointment : theAppointments) {
                displayAppointment(theAppointment);
            }

        }
    }

    public static void displayAppointment(Appointment anAppointment) {
        System.out.println(anAppointment.getAppointmentId() + " \t| " + anAppointment.getAppointmentDate() + " \t| "
                // todo: get the time
                + anAppointment.getTime() + " \t| " + anAppointment.getDuration() + " \t| "
                + anAppointment.getAllocation().getService().getServiceName() + " \t| "
                + anAppointment.getAllocation().getBranch().getBranchName() + " \t| "
                + anAppointment.getPatient().getUserId() + " \t| " + anAppointment.getPatient().getUsername() + " \t| "
                + anAppointment.getAllocation().getDoctor().getUserId() + " \t| "
                + anAppointment.getAllocation().getDoctor().getUsername() + " \t| "
                + anAppointment.getAttendance());
    }

    public void searchAppointment() {
        System.out.println("Search by:           ");
        System.out.println(" 1. Appointment ID   ");
        System.out.println(" 2. Date             ");
        System.out.println(" 3. Service Name     ");
        System.out.println(" 4. Branch           ");
        System.out.println(" 5. Patient Name     ");
        System.out.println(" 6. Doctor Name      ");
        System.out.println(" 7. Attendance Record");
        System.out.println();

        int choice = KeyboardInput.askPositiveInt("your selection");
        String searchKeyword = KeyboardInput.askString("search keyword");
        List<Appointment> selectedAppointments = controller.searchAppointment(choice, searchKeyword);

        System.out.println("Search Results: ");
        System.out.println();
        for (Appointment theAppointment : selectedAppointments) {
            displayAppointment(theAppointment);
        }
    }

    public void makeAppointment() {
        Patient selectedPatient = ManagePatientUI.searchPatient();
        Service service = new Service();
        viewSlots(service);
        boolean slotAvailable = false;
        int startSlot;

        while (!slotAvailable) {
            TimeSlot.displayTimeSlot();
            startSlot = KeyboardInput.askChoice(1, 14);
            slotAvailable = controller.checkSlotAvailability(startSlot);

            if (slotAvailable) {
                System.out.println("Slot " + startSlot + "-" + (startSlot + service.getTimeSlotRequired()) + " selected.");
                appointmentToBook = new Appointment(appointmentId, appointmentDate, patientId, allocationId,
                        attendance, timeSlot);
                displayAppointment(appointmentToBook);
                if (KeyboardInput.askBoolean("Book appointment")) {
                    controller.addAppointment(appointmentToBook);
                    System.out.println("Appointment booked. Booking ID is " + appointmentToBook.getAppointmentId());
                    System.out.println();
                }
            } else {
                System.out.println("Slot unavailable. Required time slot is " + service.getTimeSlotRequired());
                if (KeyboardInput.askBoolean("Go back to menu")) {
                    return;
                }
            }
        }
    }

    public void viewSlots(Service service) {
        displayServices();
        int choice = KeyboardInput.askPositiveInt("a service (1-15)");
        String date = KeyboardInput.askString("a date (DD/MM/YYYY)");
        controller.getAvailableTimeSlots(choice, date);

        System.out.println("Available time slots for service " + service.getServiceName());
        System.out.println();
        // todo: check the timeslot status
        System.out.println("Slot No \t| Start Time \t| Status");
        for (TimeSlot slot : TimeSlot.values()) {
            int i = slot.ordinal() + 1;
            System.out.println(i + " \t| " + slot + " \t| " + status);
        }
    }

    public void displayServices() {
        List<Allocation> allocations = controller.getAllAllocations();
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

}
