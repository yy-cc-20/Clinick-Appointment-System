package presentation;

import java.util.List;

import applicationLogic.MakeAppointmentController;
import applicationLogic.ManageAppointmentController;
import domain.Allocation;
import domain.Appointment;
import domain.Attendance;

// 1. update appointment
// 2. cancel appointment
// 3. record attendance

public class ManageAppointmentUI {

    private MakeAppointmentUI makeAppointmentUI;
    private ManageAppointmentController controller;
    private MakeAppointmentController makeAppointmentController;

    public ManageAppointmentUI(){
        makeAppointmentUI = new MakeAppointmentUI();
        controller = new ManageAppointmentController();
        makeAppointmentController = new MakeAppointmentController();
    }

    // 1. update appointment's time slot
    // similar to make appointment
    public void updateAppointment() {
        Appointment selectedAppointment = searchAppointmentToModify();

        if (selectedAppointment == null) {
            return;
        }

        ViewSlotsUI viewSlotsUI = new ViewSlotsUI();
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
    private Appointment searchAppointmentToModify() {
        List<Appointment> appointments = makeAppointmentUI.searchAppointment();
        Appointment selectedAppointment = null;
        if (appointments.size() == 0) {
            return null;
        }
        boolean select = false;
        int appointmentId = ConsoleInput.askPositiveInt("Select Appointment ID");
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId() == appointmentId) {
                selectedAppointment = appointment;
                select = true;
            }
        }
        if (select){
            makeAppointmentUI.displayAppointmentDetails(selectedAppointment);
        }
        return selectedAppointment;
    }

    // 2. cancel an appointment
    public void cancelAppointment() {
        Appointment selectedAppointment = searchAppointmentToModify();
        if (selectedAppointment == null) {
            return;
        }
        if (ConsoleInput.askBoolean("Cancel appointment")) {
            controller.cancelAppointment(selectedAppointment.getAppointmentId());
            System.out.println("Appointment cancelled.");
        }
    }

    // 3. record the attendance
    public void recordAttendance() {
        Appointment selectedAppointment = searchAppointmentToModify();
        if (selectedAppointment == null) {
            return;
        }
        displayAttendanceChoice();
        Attendance attendance = ConsoleInput.askAttendance();
        controller.updateAppointmentAttendance(selectedAppointment.getAppointmentId(), attendance.toString());
        selectedAppointment.setAttendance(attendance);
        makeAppointmentUI.displayAppointmentDetails(selectedAppointment);
    }

    public static void displayAttendanceChoice() {
        System.out.printf("%nSelect attendance.%n%n");
        System.out.println("1. Attended");
        System.out.println("2. Absent");
        System.out.println("3. NAN");
    }
}
