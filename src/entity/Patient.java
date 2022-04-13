package entity;

public class Patient extends User {
    private String ic;
    private String phoneNo;
    private String address;

    public Patient(int userid, String username, String password) {
        super(userid, username, password);
    }

    public Patient(int userid, String username, String password, String ic, String phoneNo, String address) {
        super(userid, username, password);
        this.ic = ic;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public Patient(String name, String patientIc, String phone, String address) {
        super(name);
        this.ic = patientIc;
        this.phoneNo = phone;
        this.address = address;
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

    public void setIc(String ic) {
        this.ic = ic;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAppointments(Appointment[] appointments) {
        this.appointments = appointments;
    }
}
