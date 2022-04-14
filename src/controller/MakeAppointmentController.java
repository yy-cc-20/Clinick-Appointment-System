package controller;

import boundary.ConsoleUI;
import boundary.ViewSlotsUI;
import database.DatabaseConnection;
import entity.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MakeAppointmentController {
	private Statement st;
	
	public MakeAppointmentController() {
		try {
			st = DatabaseConnection.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    // get all the appointment based on the role
    public List<Appointment> getAllAppointments(User theUser) {
        List<Appointment> appointments = DataList.getAppointmentList();

        if (theUser instanceof Patient) {
            List<Appointment> patientAppointments = new ArrayList<>();
            for (Appointment appointment : appointments) {
                if (appointment.getPatientId() == theUser.getUserId()) {
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
        List<Appointment> appointments = DataList.getAppointmentList();
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
                    if (getPatientName(appointment.getPatientId()).toLowerCase().contains(searchKeyword)) {
                        results.add(appointment);
                    }
                }
            }
            case 6 -> {
                for (Appointment appointment : appointments) {
                    if (appointment.getAllocation().getDoctor().getUsername().toLowerCase().contains(searchKeyword)) {
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
    
    public String getPatientName(int id) {
    	return DataList.getPatient(id).getUsername();
    }
    
    // check the timeslot availability and assign the allocation
    public Allocation assignAllocation(ViewSlotsUI viewSlotsUI, int startSlot) {
        List<Allocation> allocations = DataList.getAllocationList();

        // find the service
        List<Service> services = DataList.getServiceList();
        Service service = new Service();
        for (Service item : services) {
            if (item.getServiceId() == viewSlotsUI.getSelectedServiceId()) {
                service = item;
            }
        }

        // check if the time slot selected is valid
        int slotRequired = service.getTimeSlotRequired();
        if (startSlot + slotRequired - 1 > 14 || startSlot + slotRequired - 1 > 8) {
            return null;
        }

        List<List<Integer>> availableDoctors = viewSlotsUI.getAvailableDoctors();
        int branchId = viewSlotsUI.getSelectedBranchId();
        int serviceId = viewSlotsUI.getSelectedServiceId();

        // always choose the first doctor to assign
        int doctorId = availableDoctors.get(startSlot - 1).get(0);
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

    public void addAppointment(Appointment appointmentToBook) {
        String date = appointmentToBook.getAppointmentDate().format(ConsoleUI.DATE_SQL_FORMATTER);
        String attendance = appointmentToBook.getAttendance().toString();
        int startSlot = appointmentToBook.getStartSlot();
        int patientId = appointmentToBook.getPatientId();
        int allocationId = appointmentToBook.getAllocation().getId();

        addAppointment(date, attendance, startSlot, patientId, allocationId);
    }

    public void addAppointment(String date, String attendance, int startSlot, int patientId, int allocationId) {
        try {
            st.executeUpdate("INSERT IGNORE INTO appointment (date, attendance, startSlot, patientId, allocationId) " +
                    "VALUES ('" + date + "','" + attendance + "','" + startSlot + "','" + patientId + "','"
                    + allocationId + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    public static void main(String[] args) {
    	// Test addAppointment
    	List<Integer> ids = ViewSlotsController.getInstance().getDoctorsHaveAppointment(2, 1, LocalDate.of(2022, 4, 25), 1);
		System.out.println(Arrays.deepToString(ids.toArray())); // Correct output is 2, 3
		System.out.println();
	
		new MakeAppointmentController().addAppointment(new Appointment(LocalDate.of(2022, 4, 25), 23, DataList.getAllocation(2), Attendance.NAN, 1));
		
		ids = ViewSlotsController.getInstance().getDoctorsHaveAppointment(2, 1, LocalDate.of(2022, 4, 25), 1);
		System.out.println(Arrays.deepToString(ids.toArray())); // Correct output is 2, 2, 3
		System.out.println();
    }*/
}
