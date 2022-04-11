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

    // todo: create getInstance method for UIs? or add static to use the method
    // todo: sql query or for loop statements
    private final MakeAppointmentController controller = new MakeAppointmentController();
    private Appointment appointmentToBook;
    private final User theUser;

    public MakeAppointmentUI(User theUser) {
        this.theUser = theUser;
    }

    // 1. view the appointments
    public void viewAppointment() {
        List<Appointment> appointments = controller.getAllAppointments(theUser);
        displayAppointments(appointments);
    }

    private void displayAppointmentHeading() {
        ConsoleUI.displayTableName("Appointments");
        System.out.println(
                "Appointment ID \t| Date \t| Time \t| Duration \t| Service \t| Branch \t| Patient ID \t" +
                        "| Patient \t| Doctor ID \t| Doctor \t| Attendance");
    }

    private void displayAppointments(List<Appointment> appointmentsToDisplay) {
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

    // 2. search appointments
    public void searchAppointment() {
        System.out.println("Search by:           ");
        System.out.println(" 1. Appointment ID   ");
        System.out.println(" 2. Date             ");
        System.out.println(" 3. Service Name     ");
        System.out.println(" 4. Branch Name      ");
        System.out.println(" 5. Patient Name     ");
        System.out.println(" 6. Doctor Name      ");
        System.out.println(" 7. Attendance Record");
        System.out.println();

        int choice = ConsoleInput.askChoice(1, 7, "Your selection");
        String searchKeyword = "";
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
            default -> {
                searchKeyword = ConsoleInput.askString("search keyword");
            }
        }
        searchKeyword = searchKeyword.toLowerCase();
        List<Appointment> selectedAppointments = controller.searchAppointment(choice, searchKeyword);

        System.out.println("\nSearch Results: \n");
        displayAppointments(selectedAppointments);
    }

    // 3. make an appointment
    public void makeAppointment() {
        Patient selectedPatient = ManagePatientUI.searchPatient();
        Service service = new Service();
//        viewSlots(service);
        boolean slotAvailable = false;
        int startSlot;
        // todo
        // service id, branch id, date
        // assign doctor
        // allocation id where service id, branch id, and doctor id are the same
        while (!slotAvailable) {
            TimeSlot.displaySlots();
            startSlot = ConsoleInput.askChoice(1, 14, "Select a starting time slot");
            slotAvailable = controller.checkSlotAvailability(startSlot);

            if (slotAvailable) {
                System.out.println("Slot " + startSlot + "-" + ( startSlot + service.getTimeSlotRequired() ) + " selected.");
                LocalDate today = LocalDate.now();
//                appointmentToBook = new Appointment(appointmentId, today, patientId, allocationId,
//                        Attendance.NAN, startSlot);
                displayAppointmentDetails(appointmentToBook);
                if (ConsoleInput.askBoolean("Book appointment")) {
                    controller.addAppointment(appointmentToBook);
                    System.out.println("Appointment booked. Booking ID is " + appointmentToBook.getAppointmentId());
                    System.out.println();
                }
            } else {
                System.out.println("Slot unavailable. Required time slot is " + service.getTimeSlotRequired());
                if (ConsoleInput.askBoolean("Go back to menu")) {
                    return;
                }
            }
        }
    }

//	public static TimeSlot askTimeSlot() {
//		displayTimeSlot();
//		int choice = ConsoleInput.askChoice(1, 13, "Select starting time slot");
//
//		return switch (choice) {
//			case 1 -> SLOT_1;
//			case 2 -> SLOT_2;
//			case 3 -> SLOT_3;
//			case 4 -> SLOT_4;
//			case 5 -> SLOT_5;
//			case 6 -> SLOT_6;
//			case 7 -> SLOT_7;
//			case 8 -> SLOT_8;
//			case 9 -> SLOT_9;
//			case 10 -> SLOT_10;
//			case 11 -> SLOT_11;
//			case 12 -> SLOT_12;
//			case 13 -> SLOT_13;
//			default -> SLOT_14; // case 14
//		};
//	}


}
