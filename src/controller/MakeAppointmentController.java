package controller;

import entity.*;

import java.util.ArrayList;
import java.util.List;

public class MakeAppointmentController {
    // todo get arraylist from database
    private final List<Appointment> appointments = DataList.getInstance().getAppointmentList();

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
        List<Appointment> results = new ArrayList<>();

        switch (choice) {
            case 1 -> {
                int id = Integer.parseInt(searchKeyword);
                for (Appointment appointment : appointments) {
                    if (appointment.getAppointmentId() == id) {
                        results.add(appointment);
                    }
                }
            }
            case 2 -> {
                for (Appointment appointment : appointments) {
                    if (appointment.getAppointmentDate().equals(searchKeyword)) {
                        results.add(appointment);
                    }
                }
            }
            case 3 -> {
                for (Appointment appointment : appointments) {
                    if (appointment.getAllocation().getService().getServiceName().toLowerCase().contains(searchKeyword)) {
                        results.add(appointment);
                    }
                }
            }
            case 4 -> {
                for (Appointment appointment : appointments) {
                    if (appointment.getAllocation().getBranch().getBranchName().toLowerCase().contains(searchKeyword)) {
                        results.add(appointment);
                    }
                }
            }
            case 5 -> {
                for (Appointment appointment : appointments) {
                    if (appointment.getPatient().getUsername().toLowerCase().contains(searchKeyword)) {
                        results.add(appointment);
                    }
                }
            }
            case 6 -> {
                for (Appointment appointment : appointments) {
                    if (appointment.getAllocation().getDoctor().getUsername().toLowerCase().equals(searchKeyword)) {
                        results.add(appointment);
                    }
                }
            }
            case 7 -> {
                for (Appointment appointment : appointments) {
                    if (appointment.getAttendance().toString().toLowerCase().equals(searchKeyword)) {
                        results.add(appointment);
                    }
                }
            }
        }
        return results;
    }

    public boolean checkSlotAvailability(int startSlot) {
        // sql query
        return true;
    }


    public void addAppointment(Appointment appointmentToBook) {
    }
}
