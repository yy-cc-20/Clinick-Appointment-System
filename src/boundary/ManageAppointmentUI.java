package boundary;

import java.util.List;

import controller.ManageAppointmentController;
import entity.Appointment;
import entity.Attendance;

public class ManageAppointmentUI {

    private final ManageAppointmentController controller = new ManageAppointmentController();
    private static Appointment selectedAppointment;

	public void searchAppointmentToModify() {
		int appointmentId = ConsoleInput.askInt("appointment ID");
		List<Appointment> selectedAppointments = controller.searchAppointment(1, Integer.toString(appointmentId));

		if (selectedAppointments.size() == 0) {
			System.out.println("No such appointment found.");
		} else {
			selectedAppointment = selectedAppointments.get(0);
			MakeAppointmentUI.displayAppointmentDetails(selectedAppointment);
		}
	}

    public void updateAppointment() {
        searchAppointmentToModify();
		if (ConsoleInput.askBoolean("Update appointment")) {
			controller.updateAppointment(selectedAppointment);
			System.out.println("Appointment updated.");
		}
    }

    public void cancelAppointment() {
        searchAppointmentToModify();
        if (ConsoleInput.askBoolean("Cancel appointment")) {
            controller.cancelAppointment(selectedAppointment);
            System.out.println("Appointment cancelled.");
        }
    }

    public void recordAttendance() {
        searchAppointmentToModify();
        Attendance attendance = Attendance.askAttendance();
        selectedAppointment.setAttendance(attendance);
        MakeAppointmentUI.displayAppointmentDetails(selectedAppointment);
    }

}
