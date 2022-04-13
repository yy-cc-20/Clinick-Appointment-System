package controller;

import entity.DataList;


public class ManageAppointmentController {

    public void updateAppointmentTime(int appointmentId, String newDate, int newStartSlot) {
        // after viewing the available slots, wants to update the date and startSlot => allocationId changes 
        DataList.getInstance().updateAppointmentTime(appointmentId, newDate, newStartSlot);
    }

    public void updateAppointmentAttendance(int appointmentId, String updatedAttendance) {
        DataList.getInstance().updateAppointmentAttendance(appointmentId, updatedAttendance);
    }

    public void cancelAppointment(int appointmentId) {
        DataList.getInstance().cancelAppointment(appointmentId);
    }

}
