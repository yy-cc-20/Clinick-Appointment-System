package boundary;

import java.util.List;

import controller.ManageAppointmentController;
import entity.Appointment;
import entity.Attendance;

public class ManageAppointmentUI {

    private final ManageAppointmentController controller = new ManageAppointmentController();
    private static Appointment selectedAppointment;

    public void searchAppointmentToModify() {
        String appointmentId = KeyboardInput.askString("appointment ID");
        List<Appointment> selectedAppointments = controller.searchAppointment(1, appointmentId);

        if (selectedAppointments.size() == 0) {
            System.out.println("No such appointment found.");
        } else {
            selectedAppointment = selectedAppointments.get(0);
            MakeAppointmentUI.displayAppointment(selectedAppointment);
        }
    }

    public void updateAppointment() {
        searchAppointmentToModify();

        if (KeyboardInput.askBoolean("Update appointment")) {
            System.out.println("Appointment updated.");
        }
    }

    public void cancelAppointment() {
        searchAppointmentToModify();
        if (KeyboardInput.askBoolean("Cancel appointment")) {
            controller.cancelAppointment(selectedAppointment);
            System.out.println("Appointment cancelled.");
        }
    }

    public void recordAttendance() {
        searchAppointmentToModify();
        // KeyboardInput.askAttendance;
        Attendance attendance = Attendance.askAttendance();
        selectedAppointment.setAttendance(attendance);
        MakeAppointmentUI.displayAppointment(selectedAppointment);
    }
    
}
