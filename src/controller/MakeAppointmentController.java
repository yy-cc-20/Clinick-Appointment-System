package controller;

import entity.Allocation;
import entity.Appointment;
import entity.Service;

import java.time.LocalDate;
import java.util.List;

public class MakeAppointmentController {
    public List<Appointment> getAllAppointments() {
    }

    public List<Appointment> searchAppointment(int choice, String searchKeyword) {
    }

    public boolean checkSlotAvailability(int startSlot) {
        // sql query
    }
    

    public void addAppointment(Appointment appointmentToBook) {
    }

    public List<Allocation> getAllAllocations() {
    }

    public void getAvailableTimeSlots(int choice, LocalDate date) {
    }
}
