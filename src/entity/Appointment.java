package entity;

import boundary.ConsoleUI;
import boundary.ViewSlotsUI;

import java.time.LocalDate;
import java.util.List;

public class Appointment {
    private int patientId;
    private int appointmentId;
    private LocalDate appointmentDate;
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

    private Patient findPatient(int patientId) {
        List<Patient> patients = DataList2.getPatientList();
        for (Patient value : patients) {
            if (value.getUserId() == patientId) {
                return value;
            }
        }
        return null;
    }

//    private Allocation findAllocation(int appointmentId) {
//        List<Allocation> allocations = DataList.getInstance().getAllocationList();
//        for (Allocation value : allocations) {
//            if (value.getLinkId() == appointmentId) {
//                return value;
//            }
//        }
//        return null;
//    }
//
//    private Attendance retrieveAttendance(String attendance) {
//        if (attendance.equals("Attended")) {
//            return Attendance.ATTENDED;
//        } else if (attendance.equals("Absent")) {
//            return Attendance.ABSENT;
//        } else {
//            return Attendance.NAN;
//        }
//    }

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
