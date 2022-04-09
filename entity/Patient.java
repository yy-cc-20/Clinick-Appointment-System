public class Patient extends User {
	private String ic;
	private String phoneNo;
	private String address;
	private Appointment[] appointments;

	public Patient(String userid, String username, String password) {
		super(userid, username, password);
	}
}
