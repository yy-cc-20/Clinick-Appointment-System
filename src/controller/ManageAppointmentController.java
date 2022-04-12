package controller;

import entity.Appointment;

import java.util.ArrayList;
import java.util.List;

public class ManageAppointmentController {
    //    Scanner input = new Scanner(System.in);
    public List<Appointment> searchAppointment(int i, String appointmentId) {
        return new ArrayList<>();
    }

    public List<Appointment> searchAppointment(ArrayList<Appointment> appointmentList) {
//        String adid;
//        boolean found = false;
//        do {
//            do {
//                System.out.print("Please enter a appointment id to search: ");
//                adid = input.nextLine();
//
//                if (adid.isEmpty()) {
//                    System.out.println("ERROR: Invalid input. Please enter again!");
//                }
//
//                for (int i = 0; i < appointmentList.size(); i++) {
//                    if (appointmentList.get(i).getAppointmentId().equals(adid)) {
//                        found = true;
//                    }
//                }
//
//                if (!found) {
//                    System.out.println("Appointment not found! Please enter again!");
//                }
//            } while (adid.isEmpty());
//        } while (!found);
//        System.out.println("ID\t\tName\t\t\tDate\t\tAllocation\t\t\t\t\t\t\t\t\t\t\tAttendance\t\t\tTime\n");
//
//        for (int i = 0; i < appointmentList.size(); i++) {
//            if (adid.equals(appointmentList.get(i).getAppointmentId())) {
//                System.out.println(appointmentList.get(i).getAppointmentId() + "\t\t" + appointmentList.get(i).getPatient() + "\t\t" + appointmentList.get(i).getAppointmentDate() + "\t\t" + appointmentList.get(i).getAllocation() + "\t\t" + appointmentList.get(i).getAttendance() + "\t\t" + appointmentList.get(i).getTime() + "\n");
//            }
//        }
        return new ArrayList<>();
    }

    public void cancelAppointment(Appointment selectedAppointment) {
//        boolean found = false;
//        do {
//            System.out.print("Please enter selected Appointment ID to cancel: ");
//            String id = input.nextLine();
//
//            for (int i = 0; i < selectedAppointment.size(); i++) {
//                if (selectedAppointment.get(i).getAppointmentId(i).equals(id)) {
//                    selectedAppointment.remove(i);
//                    found = true;
//                    System.out.println("Patient's Appointment cancelled!");
//                }
//            }
//
//            if (!found) {
//                System.out.println("Patient's Appointment not cancelled! Please enter again!\n");
//            }
//        } while (!found);
    }

    public void updateAppointment(ArrayList<Appointment> appointmentList) {
//        String id, date, allocation, attendance, startSlot;
//        boolean found = false;
//
//        do {
//            do {
//                System.out.print("Please enter an appointment ID to edit: ");
//                id = input.nextLine();
//
//                if (id.isEmpty()) {
//                    System.out.println("ERROR: Please enter valid appointment id!");
//                }
//
//                for (int i = 0; i < appointmentList.size(); i++) {
//                    if (id.equals(appointmentList.get(i).getAppointmentId())) {
//                        found = true;
//                    }
//                }
//
//                if (!found) {
//                    System.out.println("ERROR: Please enter valid appointment id!");
//                }
//            } while (id.isEmpty());
//        } while (!found);
//        do {
//            System.out.print("New appointment date: ");
//            date = input.nextLine();
//
//            if (date.isEmpty()) {
//                System.out.println("ERROR: Please enter valid appointmentDate!");
//            }
//
//        } while (date.isEmpty());
//
//        do {
//            System.out.print("New allocation: ");
//            allocation = input.nextLine();
//
//            if (allocation.isEmpty()) {
//                System.out.println("ERROR: Please enter a valid allocation!");
//            }
//
//        } while (allocation.isEmpty());
//
//        do {
//            System.out.print("New patient's attendance: ");
//            attendance = input.nextLine();
//
//            if (attendance.isEmpty()) {
//                System.out.println("ERROR: Please enter a valid patient's attendance!");
//            }
//
//        } while (attendance.isEmpty());
//
//        do {
//            System.out.print("New time slot: ");
//            startSlot = input.nextLine();
//
//            if (startSlot.isEmpty()) {
//                System.out.println("ERROR: Please enter a valid time slot!");
//            }
//
//        } while (startSlot.isEmpty());
//        System.out.println("Admin Record Modified!");
    }

    public void recordAttendance(ArrayList<Appointment> appointmentList) {
//        boolean found = false;
//        do {
//            System.out.print("Please enter selected Appointment ID to record: ");
//            String attendance = input.nextLine();
//
//            for (int i = 0; i < appointmentList.size(); i++) {
//                if (appointmentList.get(i).getAttendance().equals(attendance)) {
//                    appointmentList.retrieveAttendance(i);
//                    found = true;
//                    System.out.println("Patient's Attendance Recorded!");
//                }
//            }
//
//            if (!found) {
//                System.out.println("Patient's attendance not recorded! Please enter again!\n");
//            }
//        } while (!found);
    }


}
