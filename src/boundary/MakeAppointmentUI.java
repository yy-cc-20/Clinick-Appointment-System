package src.boundary;

import java.util.List;

import src.entity.Service;
import src.entity.Patient;
import src.controller.MakeAppointmentController;
import src.entity.Appointment;
import src.entity.User;
import src.boundary.ManagePatientUI;
import src.entity.TimeSlot;

public class MakeAppointmentUI {

    private MakeAppointmentController controller = new MakeAppointmentController();

    public void viewAppointment() {
        displayAppointment(controller.getAllAppointments());
    }

    public static void displayAppointment(List<Appointment> theAppointments) {
        if (theAppointments.size() == 0) {
            System.out.println("No appointment to display.");
        } else {
            Appointment anAppointment;
            System.out.println(
                    "Appointment ID \t| Date \t| Time \t| Duration \t| Service \t| Branch \t| Patient ID \t| Patient \t| Doctor ID \t| Doctor \t| Attendance");
            for (int i = 0; i < theAppointments.size(); i++) {
                anAppointment = theAppointments.get(i);
                System.out.println(anAppointment.getId() + " \t| " + anAppointment.getDate() + " \t| "
                        + anAppointment.getTime() + " \t| " + anAppointment.getDuration() + " \t| "
                        + anAppointment.getAllocation().getService().getName() + " \t| "
                        + anAppointment.getAllocation().getBranch().getName() + " \t| "
                        + anAppointment.getPatient().getId() + " \t| " + anAppointment.getPatient().getName() + " \t| "
                        + anAppointment.getAllocation().getDoctor().getId() + " \t| "
                        + anAppointment.getAllocation().getDoctor().getName() + " \t| "
                        + anAppointment.getAttendance());
            }
        }
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
        displayAppointment(selectedAppointments);
    }

    public void makeAppointment() {
        Patient selectedPatient = ManagePatientUI.searchPatient();
        viewSlots();
        boolean slotAvailable = false;
        int startSlot;

        while (!slotAvailable) {
            startSlot = KeyboardInput.askPositiveInt("starting time slot");
            slotAvailable = controller.checkSlotAvailability(startSlot);

            if (slotAvailable) {
                System.out.println("Slot " + startSlot + "-" + (startSlot + timeSlotRequired) + " selected.");
                displayAppointment(appointmentToBooked);
                if (KeyboardInput.askBoolean("Book appointment")) {
                    controller.addAppointment(date, time, service, patientId, doctorId);
                    System.out.println("Appointment booked. Booking ID is " + appointmentId);
                    System.out.println();
                }
            } else {
                System.out.println("Slot unavailable. Required time slot is " + timeSlotRequired);
                if (KeyboardInput.askBoolean("Go back to menu")) {
                    return;
                }
            }
        }
    }

    public static void viewSlots() {
        displayServices();
        int choice = KeyboardInput.askPositiveInt("a service (1-15)");
        String date = KeyboardInput.askString("a date (DD/MM/YYYY)");
        controller.getAvailableTimeSlots(choice, date);

        System.out.println("Available time slots for service " + service);
        System.out.println();
        System.out.println("Slot No \t| Start Time \t| Status");
        for (TimeSlot slot : TimeSlot.values()) {
            int i = slot.ordinal() + 1;
            System.out.println(i + " \t| " + slot + " \t| " + status);
        }
    }

    public static void displayServices() {
        List<Service> theServices = controller.getAllServices();
        Service aService;

        System.out.println("Available services:");
        System.out.println();
        System.out.println(
                "No \t| Service \t| Service ID \t| Branch Address \t| Telephone No \t| Description \t| Price \t| Required Time Slot");
        for (int i = 0; i < theServices.size(); i++) {
            aService = theServices.get(i);
            System.out.println(i + " \t| " + aService.getName() + " \t| " + aService.getId() + " \t| "
                    + aService.getAllocation().getBranch().getAddress() + " \t| "
                    + aService.getAllocation().getBranch().getTelNo() + " \t| "
                    + aService.getDescription() + aService.getPrice() + aService.getTimeSlotRequired());
        }
    }

}
