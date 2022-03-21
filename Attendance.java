enum Attendance {

  	//enum fields
  	ATTENDED("ATTENDED"),ABSENTED("ABSENTED"),NAN("-");
	private final String attendance;
  
  	//constructor
	private Attendance(String attendance) {
		this.attendance = attendance;
	}
  
	//toString
	public String getAttendance() {
		return attendance;
	}
}
