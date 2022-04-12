package boundary;

import java.util.ArrayList;
import java.util.List;

import controller.ManageAppointmentController;
import entity.Appointment;
import entity.Attendance;

public class ManageAppointmentUI {

    private final ManageAppointmentController controller = new ManageAppointmentController();
    private static Appointment selectedAppointment;

    public void updateAppointment() {
        List<Appointment> appointments = MakeAppointmentUI.searchAppointment();

        String newDate = ConsoleInput.askDateNoEarlierThanToday("new date").toString();
        // TODO : view the slots and select a newStartSlot
		if (ConsoleInput.askBoolean("Update appointment")) {
//			controller.updateAppointmentTime(selectedAppointment.getAppointmentId(), newDate, newStartSlot);
			System.out.println("Appointment updated.");
		}
    }

    public void cancelAppointment() {
        List<Appointment> appointments = MakeAppointmentUI.searchAppointment();
        
        if (ConsoleInput.askBoolean("Cancel appointment")) {
            controller.cancelAppointment(selectedAppointment.getAppointmentId());
            System.out.println("Appointment cancelled.");
        }
    }

    public void recordAttendance() {
        List<Appointment> appointments = MakeAppointmentUI.searchAppointment();

        Attendance attendance = Attendance.askAttendance();
        controller.updateAppointmentAttendance(selectedAppointment.getAppointmentId(), attendance.toString());
        MakeAppointmentUI.displayAppointmentDetails(selectedAppointment);
    }

}
