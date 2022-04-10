package boundary;

import java.util.List;

import controller.ManageAppointmentController;
import entity.Appointment;

public class ManageAppointmentUI {

    private final ManageAppointmentController controller = new ManageAppointmentController();
    private List<Appointment> selectedAppointment;

    public void searchAppointmentToModify() {
        String appointmentId = KeyboardInput.askString("appointment ID");
        selectedAppointment = controller.searchAppointment(1, appointmentId);

        if (selectedAppointment.size() == 0) {
            System.out.println("No such appointment found.");
        } else {
            MakeAppointmentUI.displayAppointment(selectedAppointment);
        }
    }

    public void updateAppointment() {
        searchAppointmentToModify();
        // call viewSlots?
        // MakeAppointmentUI.viewSlots();

        if(KeyboardInput.askBoolean("Update appointment")){
            System.out.println("Appointment updated.");
        }
    }

    public void cancelAppointment() {
        searchAppointmentToModify();
        if(KeyboardInput.askBoolean("Cancel appointment")){
            controller.cancelAppointment(selectedAppointment);
            System.err.println("Appointment cancelled.");
        }
    }

    public void recordAttendance() {
        searchAppointmentToModify();
        // KeyboardInput.askAttendance;
        String attendance = KeyboardInput.askString("attendance record (Attended/Absent)");
        switch (attendance){
            case "Attended"
        }
        selectedAppointment.get(0).setAttendance(attendance);
        MakeAppointmentUI.displayAppointment(selectedAppointment);
    }
}
