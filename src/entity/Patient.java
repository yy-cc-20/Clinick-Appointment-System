package entity;

public class Patient extends User {
    private String ic;
    private String phoneNo;
    private String address;
    private Appointment[] appointments;

    public Patient(int userid, String username, String password) {
        super(userid, username, password);
    }

    public Patient(int userid, String username, String password, String ic, String phoneNo, String address,
                   Appointment[] appointments) {
        super(userid, username, password);
        this.ic = ic;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public Patient(){}

    public String getIc() {
        return ic;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public Appointment[] getAppointments() {
        return appointments;
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
