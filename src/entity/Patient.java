package entity;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private String ic;
    private String phoneNo;
    private String address;
    private List<Appointment> appointmentList;

    // Copy constructor: create a new object with exactly the same properties
    public Patient(Patient p) {
        super(p.userId, p.username, p.password);
        this.ic = p.ic;
        this.phoneNo = p.phoneNo;
        this.address = p.address;
        this.appointmentList = new ArrayList<Appointment>(p.appointmentList);
    }
    
    public Patient(int userid, String username, String password, String ic, String phoneNo, String address, List<Appointment> a) {
        super(userid, username, password);
        this.ic = ic;
        this.phoneNo = phoneNo;
        this.address = address;
        appointmentList = new ArrayList<Appointment>(a);
    }
    
    public Patient(int userid, String username, String password) {
        super(userid, username, password);
    }

    public Patient() {
    }

    public String getIc() {
        return ic;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public List<Appointment> getAppointments() {
        return appointmentList;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointmentList = new ArrayList<Appointment>(appointments);
    }
}
