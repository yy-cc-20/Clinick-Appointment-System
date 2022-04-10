package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Appointment {
    private static final String pattern = "dd/MM/yyyy"; // date format
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    private String appointmentId;
    private LocalDate appointmentDate;
    private Attendance attendance;
    private ArrayList<TimeSlot> timeSlot;

    public Appointment(String appointmentId, String appointmentDate, String attendance, String[] timeSlot) {
        this.appointmentId = appointmentId;
        this.appointmentDate = LocalDate.parse(appointmentDate, formatter);
        this.attendance = retrieveAttendance(attendance);
        this.timeSlot = retrieveTimeSlot(timeSlot);
    }

    public Attendance retrieveAttendance(String attendance) {
        if(attendance.equals("Attended")){
            return Attendance.ATTENDED;
        } else if(attendance.equals("Absent")){
            return Attendance.ABSENT;
        } else{
            return Attendance.NAN;
        }
    }

    public ArrayList<TimeSlot> retrieveTimeSlot(String[] timeSlot) {
        ArrayList<TimeSlot> timeSlots = new ArrayList<>();

        for (String s : timeSlot) {
            timeSlots.add(getTimeSlot(s));
        }

        return timeSlots;
    }

    public TimeSlot getTimeSlot(String timeSlot){
        return switch (timeSlot) {
            case "SLOT_1" -> TimeSlot.SLOT_1;
            case "SLOT_2" -> TimeSlot.SLOT_2;
            case "SLOT_3" -> TimeSlot.SLOT_3;
            case "SLOT_4" -> TimeSlot.SLOT_4;
            case "SLOT_5" -> TimeSlot.SLOT_5;
            case "SLOT_6" -> TimeSlot.SLOT_6;
            case "SLOT_7" -> TimeSlot.SLOT_7;
            case "SLOT_8" -> TimeSlot.SLOT_8;
            case "SLOT_9" -> TimeSlot.SLOT_9;
            case "SLOT_10" -> TimeSlot.SLOT_10;
            case "SLOT_11" -> TimeSlot.SLOT_11;
            case "SLOT_12" -> TimeSlot.SLOT_12;
            case "SLOT_13" -> TimeSlot.SLOT_13;
            default -> TimeSlot.SLOT_14;
        };
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public ArrayList<TimeSlot> getTimeSlot() {
        return timeSlot;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public void setTimeSlot(ArrayList<TimeSlot> timeSlot) {
        this.timeSlot = timeSlot;
    }
}