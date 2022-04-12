package controller;

import boundary.ConsoleUI;
import boundary.ViewSlotsUI;
import database.DatabaseConnection;
import entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class MakeAppointmentController {
    private final List<Appointment> appointments = DataList.getInstance().getAppointmentList();
    private final List<Allocation> allocations = DataList.getInstance().getAllocationList();

    public List<Appointment> getAllAppointments(User theUser) {
        System.out.println(theUser.getClass());
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
        } else
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
                    if (appointment.getAppointmentDateString().equals(searchKeyword)) {
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

    public Allocation assignAllocation(ViewSlotsUI viewSlotsUI) {
        List<List<Integer>> availableDoctors = viewSlotsUI.getAvailableDoctors();
        int branchId = viewSlotsUI.getSelectedBranchId();
        int serviceId = viewSlotsUI.getSelectedServiceId();
        // todo select a doctor

        int doctorId = 0;
        Allocation allocation = null;
        for (Allocation value : allocations) {
            if (value.getBranch().getBranchId() == branchId) {
                if (value.getService().getServiceId() == serviceId) {
                    if (value.getDoctor().getUserId() == doctorId) {
                        allocation = value;
                    }
                }
            }
        }
        return allocation;
    }

    public void addAppointment(Appointment appointmentToBook) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        conn.setAutoCommit(false);

        String date = appointmentToBook.getAppointmentDate().format(ConsoleUI.DATE_SQL_FORMATTER);
        String attendance = appointmentToBook.getAttendance().toString();
        int startSlot = appointmentToBook.getStartSlot();
        int patientId = appointmentToBook.getPatient().getUserId();
        int allocationId = appointmentToBook.getAllocation().getLinkId();

        st.executeUpdate("INSERT IGNORE INTO Appointment (date, attendance, startSlot, patientId, allocationId) " +
                "VALUES ('"+date+"','"+attendance+"','"+startSlot+"','"+patientId+"','"+allocationId+"')");
		conn.commit();
		conn.setAutoCommit(true);
    }
}
