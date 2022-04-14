package boundary;

import java.util.List;

import controller.MakeAppointmentController;
import controller.ManageAppointmentController;
import entity.Allocation;
import entity.Appointment;
import entity.Attendance;

// 1. update appointment
// 2. cancel appointment
// 3. record attendance

public class ManageAppointmentUI {

    private final ManageAppointmentController controller = new ManageAppointmentController();
    private final MakeAppointmentController makeAppointmentController = new MakeAppointmentController();
    private static Appointment selectedAppointment;

    // 1. update appointment's time slot
    // similar to make appointment
    public void updateAppointment() {
        searchAppointmentToModify();

        if(selectedAppointment == null){
            return;
        }

        ViewSlotsUI viewSlotsUI = ViewSlotsUI.getInstance();
        ConsoleUI.clearScreen();
        boolean contViewSlot = viewSlotsUI.viewSlots();

        if (!contViewSlot) {
            System.out.println("Back to the menu");
            return;
        }

        boolean allocated = false;
        int startSlot;
        int slotRequired = 0;

        // service id, branch id, date
        // assign doctor
        // allocation id where service id, branch id, and doctor id are the same
        while (!allocated) {
            startSlot = ConsoleInput.askChoice(1, 14, "Select a starting time slot");

            Allocation allocation = makeAppointmentController.assignAllocation(viewSlotsUI, startSlot);

            if (allocation != null) {
                slotRequired = allocation.getService().getTimeSlotRequired();
                allocated = true;
            }
            if (allocated) {
                System.out.println("Slot " + startSlot + "-" + ( startSlot + slotRequired - 1 ) + " selected.");
                String date = viewSlotsUI.getSelectedDate().format(ConsoleUI.DATE_SQL_FORMATTER);
                if (ConsoleInput.askBoolean("Update appointment")) {
                    controller.updateAppointmentTime(selectedAppointment.getAppointmentId(), date, startSlot);
                    System.out.println("Appointment updated.");
                }
            } else {
                System.out.println("Slot unavailable. Required time slot is " + slotRequired);
                if (ConsoleInput.askBoolean("Go back to menu")) {
                    return;
                }
            }
        }
    }

    // search and select an appointment to modify
    private void searchAppointmentToModify() {
        List<Appointment> appointments = MakeAppointmentUI.searchAppointment();
        if(appointments.size() == 0){
            return;
        }
        int appointmentId = ConsoleInput.askPositiveInt("Select Appointment ID");
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId() == appointmentId) {
                selectedAppointment = appointment;
            }
        }
        MakeAppointmentUI.displayAppointmentDetails(selectedAppointment);
    }

    // 2. cancel an appointment
    public void cancelAppointment() {
        searchAppointmentToModify();
        if(selectedAppointment == null){
            return;
        }
        if (ConsoleInput.askBoolean("Cancel appointment")) {
            controller.cancelAppointment(selectedAppointment.getAppointmentId());
            System.out.println("Appointment cancelled.");
        }
    }

    // 3. record the attendance
    public void recordAttendance() {
        searchAppointmentToModify();
        if(selectedAppointment == null){
            return;
        }
        displayAttendanceChoice();
        Attendance attendance = ConsoleInput.askAttendance();
        controller.updateAppointmentAttendance(selectedAppointment.getAppointmentId(), attendance.toString());
        selectedAppointment.setAttendance(attendance);
        MakeAppointmentUI.displayAppointmentDetails(selectedAppointment);
    }

    public static void displayAttendanceChoice() {
        System.out.printf("%nSelect attendance.%n%n");
        System.out.println("1. Attended");
        System.out.println("2. Absent");
        System.out.println("3. NAN");
    }
}
