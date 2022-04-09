public class Patient extends User {
	private String ic;
	private String phoneNo;
	private String address;
	private Appointment[] appointments;

	public Patient(String userid, String username, String password) {
		super(userid, username, password);
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

	public Appointment[] getAppointments() {
		return appointments;
	}
}
