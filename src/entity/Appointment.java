package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private static final String pattern = "dd/MM/yyyy"; // date format
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    private String appointmentId;
    private LocalDate appointmentDate;
    private Attendance attendance;
    private TimeSlot[] timeSlot;

    public Appointment(String appointmentId, String appointmentDate, String attendance, String[] timeSlot) {
        this.appointmentId = appointmentId;
        this.appointmentDate = LocalDate.parse(appointmentDate, formatter);
//        this.attendance = attendance;
//        this.timeSlot = timeSlot;
    }
}
