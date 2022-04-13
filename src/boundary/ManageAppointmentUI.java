package boundary;

import java.util.List;

import controller.MakeAppointmentController;
import controller.ManageAppointmentController;
import entity.Allocation;
import entity.Appointment;
import entity.Attendance;

public class ManageAppointmentUI {

    private final ManageAppointmentController controller = new ManageAppointmentController();
    private final MakeAppointmentController makeAppointmentController = new MakeAppointmentController();
    private static Appointment selectedAppointment;

    private void searchAppointmentToModify() {
        List<Appointment> appointments = MakeAppointmentUI.searchAppointment();
        int appointmentId = ConsoleInput.askPositiveInt("Appointment ID");
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId() == appointmentId) {
                selectedAppointment = appointment;
            }
        }
        MakeAppointmentUI.displayAppointmentDetails(selectedAppointment);
    }

    public void updateAppointment() {
        searchAppointmentToModify();

        ViewSlotsUI viewSlotsUI = ViewSlotsUI.getInstance();
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
                System.out.println("Slot " + startSlot + "-" + ( startSlot + slotRequired ) + " selected.");
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

    public void cancelAppointment() {
        searchAppointmentToModify();
        if (ConsoleInput.askBoolean("Cancel appointment")) {
            controller.cancelAppointment(selectedAppointment.getAppointmentId());
            System.out.println("Appointment cancelled.");
        }
    }

    public void recordAttendance() {
        List<Appointment> appointments = MakeAppointmentUI.searchAppointment();
        int appointmentId = ConsoleInput.askPositiveInt("Appointment ID");
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId() == appointmentId) {
                selectedAppointment = appointment;
            }
        }
        MakeAppointmentUI.displayAppointmentDetails(selectedAppointment);

        Attendance attendance = Attendance.askAttendance();
        controller.updateAppointmentAttendance(selectedAppointment.getAppointmentId(), attendance.toString());
        MakeAppointmentUI.displayAppointmentDetails(selectedAppointment);
    }

}
