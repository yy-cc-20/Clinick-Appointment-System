package controller;

import entity.Appointment;
import entity.DataList;

import java.util.List;

public class ManageAppointmentController {
    
//    public List<Appointment> searchAppointment(int i, String data) {
//        switch (i) {
//            case 1 -> {
//                return DataList.getInstance().getAppointmentList("filter", "id", data);
//            }
//            case 2 -> {
//                return DataList.getInstance().getAppointmentList("filter", "date", data);
//            }
//            case 3 -> {
//                return DataList.getInstance().getAppointmentList("filter", "attendance", data);
//            }
//            case 4 -> {
//                return DataList.getInstance().getAppointmentList("filter", "startSlot", data);
//            }
//            case 5 -> {
//                return DataList.getInstance().getAppointmentList("filter", "patientId", data);
//            }
//            case 6 -> {
//                return DataList.getInstance().getAppointmentList("filter", "allocationId", data);
//            }
//            case 7 -> {
//                return DataList.getInstance().getAppointmentList("filter", "attendance", data);
//            }
//            default -> {
//                return null;
//            }
//
//            // TODO : MORE SEARCH BYs
//            // allocation table => service name, branch name, doctor name
//            // patient name
//            // TODO : different data types (ie., WHERE column = 'data' / WHERE column = data)
//        }
//    }

    public void updateAppointmentTime(int appointmentId, String newDate, int newStartSlot) {
        // after viewing the available slots, wants to update the date and startSlot => allocationId changes 
        //DataList.getInstance().updateAppointmentTime(appointmentId, newDate, newStartSlot);
    }

    public void updateAppointmentAttendance(int appointmentId, String updatedAttendance) {
        DataList.getInstance().updateAppointmentAttendance(appointmentId, updatedAttendance);
    }

    public void cancelAppointment(int appointmentId) {
        DataList.getInstance().cancelAppointment(appointmentId);
    }

}
