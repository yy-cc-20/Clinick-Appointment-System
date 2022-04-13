package controller;

import boundary.ConsoleUI;
import boundary.ViewSlotsUI;
import entity.*;

import java.util.ArrayList;
import java.util.List;

public class MakeAppointmentController {
    private final List<Appointment> appointments = DataList.getInstance().getAppointmentList(null, "", "");
    private final List<Allocation> allocations = DataList.getInstance().getAllocationList();
    private final List<Service> services = DataList.getInstance().getServiceList();

    public List<Appointment> getAllAppointments(User theUser) {
        String id = Integer.toString(theUser.getUserId());

        if (theUser instanceof Patient) {
            return DataList.getInstance().getAppointmentList("filter", "patientId", id);
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
            // todo
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

    public Allocation assignAllocation(ViewSlotsUI viewSlotsUI, int startSlot) {
        Service service = new Service();
        for (Service item : services) {
            if (item.getServiceId() == viewSlotsUI.getSelectedServiceId()) {
                service = item;
            }
        }
        int slotRequired = service.getTimeSlotRequired();
        if (slotRequired + slotRequired - 1 > 14) {
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
        int patientId = appointmentToBook.getPatient().getUserId();
        int allocationId = appointmentToBook.getAllocation().getLinkId();

        DataList.getInstance().addAppointment(date, attendance, startSlot, patientId, allocationId);
    }
}
