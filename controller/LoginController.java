
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LoginController {
	private IDataStore dataList = DataList.getInstance();
		
	// security: lock the system after a few failed logins
	int failedLoginAttempt = 0; 
	public static final int MAX_FAILED_LOGIN_ATTEMPT = 3;
	public static final int LOCK_TIME_LENGTH = 10; // in seconds
	
	/**
	 * search for the list to find the same username and password
	 * TODO replace with sql
	 * @param role 1 for receptionist, 2 for doctor, 3 for patient
	 * @return userid, return empty string if login fail
	 */
	public String loginSuccessfully(int role, String userid, String password) {
		User user = new User(userid, password);
		
		switch (role) {
			case 1: 
					for (int i = 0; i < dataList.getReceptionistList().size(); ++i) {
						if (user.equals(dataList.getReceptionistList().get(i))) {
							resetFailedLoginAttempts();
							return dataList.getReceptionistList().get(i).getUsername();
						}
					}
					break;
			case 2: 
					for (int i = 0; i < dataList.getDoctorList().size(); ++i) {
						if (user.equals(dataList.getDoctorList().get(i))) {
							resetFailedLoginAttempts();
							return dataList.getDoctorList().get(i).getUsername();
						}
					}
					break;
			default: // case 3
					for (int i = 0; i < dataList.getPatientList().size(); ++i) {
						if (user.equals(dataList.getPatientList().get(i))) {
							resetFailedLoginAttempts();
							return dataList.getPatientList().get(i).getUsername();
						}
					}
		}
		
		failedLoginAttempt++;
		return "";
	}
	
	public int getFailedLoginAttempt() {
		return failedLoginAttempt;
	}
	
	public boolean isLocked() {
		 return lockTimeEnded.isBefore(LocalDateTime.now());
	}
	
	
	/**
	 * TODO a bug: if the user restart the program, the lockTimeEnded is loss, the account will not be locked
	 
	public void lock() {
		try {
			Thread.sleep(ChronoUnit.SECONDS.between(LocalDateTime.now(), lockTimeEnded) * 1000); 
		} catch (InterruptedException e) {
			e.printStackTrace();
			 //Thread.currentThread().interrupt();
		}
		resetFailedLoginAttempts(); // Lock time expired
	}*/
	
	// use this if login successfully or lock time expired
	private void resetFailedLoginAttempts() {
		failedLoginAttempt = 0;
	}
}
