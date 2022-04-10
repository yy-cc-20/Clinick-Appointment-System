package controller;

import entity.Allocation;
import entity.Appointment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MakeAppointmentController {
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>();
    }

    public List<Appointment> searchAppointment(int choice, String searchKeyword) {
        return new ArrayList<>();

    }

    public boolean checkSlotAvailability(int startSlot) {
        // sql query
        return true;
    }


    public void addAppointment(Appointment appointmentToBook) {
    }

    public List<Allocation> getAllAllocations() {
        return new ArrayList<>();
    }

    public void getAvailableTimeSlots(int choice, LocalDate date) {
    }
}
