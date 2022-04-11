package controller;

import entity.*;

import java.util.ArrayList;
import java.util.List;

public class MakeAppointmentController {
    // todo get arraylist from database
    private List<Appointment> appointments = DataList.getInstance().getAppointmentList();

    public List<Appointment> getAllAppointments(User theUser) {
        if (theUser instanceof Patient) {
            List<Appointment> patientAppointments = new ArrayList<>();
            for (Appointment appointment : appointments) {
                if (appointment.getPatient().getUserId() == theUser.getUserId()) {
                    patientAppointments.add(appointment);
                }
            }
            return patientAppointments;
        } else if (theUser instanceof Doctor) {
            List<Appointment> doctorAppointments = new ArrayList<>();
            for (Appointment appointment : appointments) {
                if (appointment.getAllocation().getDoctor().getUserId() == theUser.getUserId()) {
                    doctorAppointments.add(appointment);
                }
            }
            return doctorAppointments;
        }

        return appointments;
    }

    public List<Appointment> searchAppointment(int choice, String searchKeyword) {
        System.out.println("Search by:           ");
        System.out.println(" 1. Appointment ID   ");
        System.out.println(" 2. Date             ");
        System.out.println(" 3. Service Name     ");
        System.out.println(" 4. Branch           ");
        System.out.println(" 5. Patient Name     ");
        System.out.println(" 6. Doctor Name      ");
        System.out.println(" 7. Attendance Record");

        List<Appointment> results = new ArrayList<>();

        switch (choice) {
            case 1 -> {
                try {
                    int id = Integer.parseInt(searchKeyword);
                    for (Appointment appointment : appointments) {
                        if (appointment.getAppointmentId() == id) {
                            results.add(appointment);
                        }
                    }
                    return results;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Appointment ID");
                    return new ArrayList<>();
                }
            }
            case 2 -> {
                for (Appointment appointment : appointments) {
                    if (appointment.getAppointmentDate().equals(searchKeyword)) {
                        results.add(appointment);
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public boolean checkSlotAvailability(int startSlot) {
        // sql query
        return true;
    }


    public void addAppointment(Appointment appointmentToBook) {
    }
}
