enum Attendance {

	private final String attendance;
	
  	//enum fields
  	ATTENDED("ATTENDED"),ABSENTED("ABSENTED"),NAN("-");
  
  	//constructor
	private Attendance(String attendance) {
		this.attendance = attendance; }
  
	//toString
	public String getAttendance() {
		return attendance; }
}
