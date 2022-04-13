package boundary;

import java.time.LocalDate;
import java.util.List;

import entity.*;
import controller.MakeAppointmentController;

// 1. view appointment
// 2. search appointment
// 3. make appointment
// 4. view slots

public class MakeAppointmentUI {

    private final static MakeAppointmentController controller = new MakeAppointmentController();
    private final User theUser;

    public MakeAppointmentUI(User theUser) {
        this.theUser = theUser;
    }

    // 1. view the appointments
    public void viewAppointment() {
        List<Appointment> appointments = controller.getAllAppointments(theUser);
        displayAppointments(appointments);
    }

    private static void displayAppointmentHeading() {
        ConsoleUI.displayTableName("Appointments");
        System.out.println(
                "Appointment ID \t| Date \t| Time \t| Duration \t| Service \t| Branch \t| Patient ID \t" +
                        "| Patient \t| Doctor ID \t| Doctor \t| Attendance");
    }

    private static void displayAppointments(List<Appointment> appointmentsToDisplay) {
        if (appointmentsToDisplay.size() == 0) {
            System.out.println("No appointment to display.");
        } else {
            displayAppointmentHeading();
            for (Appointment theAppointment : appointmentsToDisplay) {
                displayAppointmentDetails(theAppointment);
            }
        }
    }

    public static void displayAppointmentDetails(Appointment anAppointment) {
        System.out.println(anAppointment.getAppointmentId() + " \t| " + anAppointment.getAppointmentDateString() + " \t| "
                // todo: get the time
                + anAppointment.getTime() + " \t| " + anAppointment.getDuration() + " \t| "
                + anAppointment.getAllocation().getService().getServiceName() + " \t| "
                + anAppointment.getAllocation().getBranch().getBranchName() + " \t| "
                + anAppointment.getPatient().getUserId() + " \t| " + anAppointment.getPatient().getUsername() + " \t| "
                + anAppointment.getAllocation().getDoctor().getUserId() + " \t| "
                + anAppointment.getAllocation().getDoctor().getUsername() + " \t| "
                + anAppointment.getAttendance());
    }

    // 2. search appointments
    public static List<Appointment> searchAppointment() {
        displayMenuForSearchingAppointment();

        int choice = ConsoleInput.askChoice(1, 7, "Your selection");
        String searchKeyword;
        // input validations for different fields
        switch (choice) {
            case 1 -> {
                int id = ConsoleInput.askPositiveInt("appointment ID");
                searchKeyword = String.valueOf(id);
            }
            case 2 -> {
                LocalDate date = ConsoleInput.askDate("appointment date");
                searchKeyword = date.format(ConsoleInput.DATE_INPUT_FORMATTER);
            }
            case 7 -> {
                Attendance attendance = Attendance.askAttendance();
                searchKeyword = attendance.toString();
            }
            default -> searchKeyword = ConsoleInput.askString("search keyword");
        }
        searchKeyword = searchKeyword.toLowerCase();
        List<Appointment> selectedAppointments = controller.searchAppointment(choice, searchKeyword);

        System.out.println("\nSearch Results: \n");
        displayAppointments(selectedAppointments);
        return selectedAppointments;
    }

    // 3. make an appointment
    // Steps:
    // search patient
    // view slots
    // select starting time slot
    // check slot availability to get allocation id
    // display appointment to book
    // ask confirmation to book
    // add appointment
    public void makeAppointment() {
        // search patient
        ManagePatientUI managePatientUI = new ManagePatientUI(theUser);
        List<Patient> searchedPatient = managePatientUI.searchPatient();
        Patient selectedPatient = managePatientUI.selectPatient(searchedPatient);
        if (selectedPatient == null) {
            System.out.println("Back to the menu");
            return;
        }

        // view slots
        ViewSlotsUI viewSlotsUI = ViewSlotsUI.getInstance();
        boolean contViewSlot = viewSlotsUI.viewSlots();
        if (!contViewSlot) {
            System.out.println("Back to the menu");
            return;
        }

        boolean allocated = false;
        int startSlot;
        int slotRequired = 0;

        // after viewing slots can get the service id, branch id, date and available doctors.
        // need to assign doctor to get the allocation id.
        // allocation id contains service id, branch id and doctor id.
        while (!allocated) {
            // select starting time slot
            startSlot = ConsoleInput.askChoice(1, 14, "Select a starting time slot");

            // check slot availability to get allocation id
            Allocation allocation = controller.assignAllocation(viewSlotsUI, startSlot);

            if (allocation != null) {
                slotRequired = allocation.getService().getTimeSlotRequired();
                allocated = true;
            }
            if (allocated) {
                System.out.println("Slot " + startSlot + "-" + ( startSlot + slotRequired ) + " selected.");
                String date = viewSlotsUI.getSelectedDate().format(ConsoleUI.DATE_SQL_FORMATTER);
                Appointment appointmentToBook = new Appointment(date, selectedPatient.getUserId(), allocation.getLinkId(), Attendance.NAN.toString(), startSlot);
                // display appointment to book
                displayAppointmentDetails(appointmentToBook);
                // ask confirmation to book
                if (ConsoleInput.askBoolean("Book appointment")) {
                    // add appointment
                    controller.addAppointment(appointmentToBook);
                    System.out.println("Appointment booked. Booking ID is " + appointmentToBook.getAppointmentId());
                    System.out.println();
                }
            } else {
                System.out.println("Slot unavailable. Required time slot is " + slotRequired);
                if (ConsoleInput.askBoolean("Go back to menu")) {
                    return;
                }
            }
        }
    }
    
    public static void displayMenuForSearchingAppointment() {
        System.out.println("Search by:           ");
        System.out.println(" 1. Appointment ID   ");
        System.out.println(" 2. Date             ");
        System.out.println(" 3. Service Name     ");
        System.out.println(" 4. Branch Name      ");
        System.out.println(" 5. Patient Name     ");
        System.out.println(" 6. Doctor Name      ");
        System.out.println(" 7. Attendance Record");
        System.out.println();
    }
}
