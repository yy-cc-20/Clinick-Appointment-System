package entity;

import boundary.ConsoleUI;
import boundary.ViewSlotsUI;

import java.time.LocalDate;
import java.util.List;

public class Appointment {

    private int appointmentId;
    private LocalDate appointmentDate;
    private Patient patient;
    private Allocation allocation;
    private Attendance attendance;
    private int startSlot;

    public Appointment(int appointmentId, String appointmentDate, int patientId, int allocationId,
                       String attendance, int startSlot) {
        this.appointmentId = appointmentId;
        this.appointmentDate = LocalDate.parse(appointmentDate, ConsoleUI.DATE_SQL_FORMATTER);
        this.patient = findPatient(patientId);
        this.allocation = findAllocation(allocationId);
        this.attendance = retrieveAttendance(attendance);
        this.startSlot = startSlot;
    }

    public Appointment(String appointmentDate, int patientId, int allocationId, String attendance, int startSlot) {
        this.appointmentDate = LocalDate.parse(appointmentDate, ConsoleUI.DATE_SQL_FORMATTER);
        this.patient = findPatient(patientId);
        this.allocation = findAllocation(allocationId);
        this.attendance = retrieveAttendance(attendance);
        this.startSlot = startSlot;
    }

    public Appointment() {
    }

    private Patient findPatient(int patientId) {
        List<Patient> patients = DataList.getInstance().getPatientList(null, "", "");
        for (Patient value : patients) {
            if (value.getUserId() == patientId) {
                return value;
            }
        }
        return null;
    }

    private Allocation findAllocation(int appointmentId) {
        List<Allocation> allocations = DataList.getInstance().getAllocationList();
        for (Allocation value : allocations) {
            if (value.getLinkId() == appointmentId) {
                return value;
            }
        }
        return null;
    }

    private Attendance retrieveAttendance(String attendance) {
        if (attendance.equals("Attended")) {
            return Attendance.ATTENDED;
        } else if (attendance.equals("Absent")) {
            return Attendance.ABSENT;
        } else {
            return Attendance.NAN;
        }
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentDateString() {
        return appointmentDate.format(ConsoleUI.DATE_OUTPUT_FORMATTER);
    }

    public Patient getPatient() {
        return patient;
    }

    public Attendance getAttendance() {
        return attendance;
    }


    // todo
    public String getTime() {
        
        return "";
    }

    public String getDuration() {
        int required = allocation.getService().getTimeSlotRequired();
        double time = ViewSlotsUI.timeSlotsToHour(required);
        return String.format("%f hr(s)", time);
    }

    public int getStartSlot() {
        return startSlot;
    }

    public Allocation getAllocation() {
        return allocation;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }


    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }
}
