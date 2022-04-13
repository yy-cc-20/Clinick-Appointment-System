package entity;

import boundary.ConsoleUI;

import java.time.LocalDate;
import java.util.List;

public class Appointment {
    //private static final List<Patient> patients = DataList.getInstance().getPatientList();
    //private static final List<Allocation> allocations = DataList.getInstance().getAllocationList();

    private int patientId;
    private int appointmentId;
    private LocalDate appointmentDate;
    //private Patient patient; 
    // If there is Patient as the data member of Appointment and List<Appointment> as the data member of the Patient
    // It is not easily to create the objects
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
    
    public Appointment(int appointmentId, String appointmentDate, int patientId, int allocationId,
                       String attendance, int startSlot) {
        this.appointmentId = appointmentId;
        this.appointmentDate = LocalDate.parse(appointmentDate, ConsoleUI.DATE_SQL_FORMATTER);
        this.patientId = patientId;
        this.allocation = DataList2.createAllocationObject(allocationId);
        this.attendance = DataList2.attendanceStringToEnum(attendance);
        this.startSlot = startSlot;
    }

    public Appointment(String appointmentDate, int patientId, int allocationId, String attendance, int startSlot) {
        this.appointmentDate = LocalDate.parse(appointmentDate, ConsoleUI.DATE_SQL_FORMATTER);
        this.patientId = patientId;
        this.allocation = DataList2.createAllocationObject(allocationId);
        this.attendance = DataList2.attendanceStringToEnum(attendance);
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


    // todo
    public String getTime() {

        return "";
    }

    public String getDuration() {
        int required = allocation.getService().getTimeSlotRequired();
        return switch (required) {
            case 1 -> "30 mins";
            case 2 -> "1 hour";
            case 3 -> "1 hour 30 mins";
            case 4 -> "2 hours";
            case 5 -> "2 hours 30 mins";
            default -> throw new IllegalStateException("Unexpected value: " + required);
        };
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
