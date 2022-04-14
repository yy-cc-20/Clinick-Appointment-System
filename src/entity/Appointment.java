package entity;

import boundary.ConsoleUI;
import boundary.ViewSlotsUI;

import java.time.LocalDate;

public class Appointment {
    // If there is Patient as the data member of Appointment and List<Appointment> as the data member of the Patient
    // It is not easily to create the objects
    private int patientId;

    private int appointmentId;
    private LocalDate appointmentDate;
    private Allocation allocation;
    private Attendance attendance;
    private int startSlot;

    // Copy constructor: create a new object with exactly the same properties
    public Appointment(Appointment a) {
        this.appointmentId = a.appointmentId;
        this.appointmentDate = a.appointmentDate; // LocalDate is immutable, so can refer to the same object
        this.patientId = a.patientId;
        this.allocation = a.allocation;
        this.attendance = a.attendance;
        this.startSlot = a.startSlot;
    }

    public Appointment(int appointmentId, LocalDate ad, int p, Allocation a, Attendance at, int startSlot) {
        this.appointmentId = appointmentId;
        this.appointmentDate = ad;
        this.patientId = p;
        this.allocation = new Allocation(a);
        this.attendance = at;
        this.startSlot = startSlot;
    }

    public Appointment(LocalDate ad, int p, Allocation a, Attendance at, int startSlot) {
        this.appointmentDate = ad;
        this.patientId = p;
        this.allocation = new Allocation(a);
        this.attendance = at;
        this.startSlot = startSlot;
    }

    public Appointment() {
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

    public int getPatientId() {
        return patientId;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public String getTime() {
        String startTime = TimeSlot.getSlot(startSlot);
        String endTime = TimeSlot.getSlot(startSlot + allocation.getService().getTimeSlotRequired() - 1);
        return String.format("%s-%s", startTime, endTime);
    }

    public String getDuration() {
        int required = allocation.getService().getTimeSlotRequired();
        double time = ViewSlotsUI.timeSlotsToHour(required);
        return String.format("%.1f hr(s)", time);
    }

    public int getStartSlot() {
        return startSlot;
    }

    public Allocation getAllocation() {
        return allocation;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

}
