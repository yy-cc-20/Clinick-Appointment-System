package controller;

import entity.*;

import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LoginController {
	private IDataStore dataList = DataList.getInstance();
		
	// security: lock the system after a few failed logins
	int failedLoginAttempt = 0; 
	LocalDateTime lockTimeEnded;
	public static final int MAX_FAILED_LOGIN_ATTEMPT = 3;
	private static final int LOCK_TIME_LENGTH = 10; // in seconds
	
	/**
	 * search for the list to find the same username and password
	 * TODO replace by sql
	 * @param role 1 for receptionist, 2 for doctor, 3 for patient
	 * @return userid, return empty string if login fail
	 */
	public String login(int role, String username, String password) {
		User user = new User(username, password);
		
		switch (role) {
			case 1: 
					for (int i = 0; i < dataList.getReceptionistList().size(); ++i) {
						if (user.equals(dataList.getReceptionistList().get(i))) {
							unlock();
							return dataList.getReceptionistList().get(i).getId();
						}
					}
					break;
			case 2: 
					for (int i = 0; i < dataList.getDoctorList().size(); ++i) {
						if (user.equals(dataList.getDoctorList().get(i))) {
							unlock();
							return dataList.getDoctorList().get(i).getId();
						}
					}
					break;
			default: // case 3
					for (int i = 0; i < dataList.getPatientList().size(); ++i) {
						if (user.equals(dataList.getPatientList().get(i))) {
							unlock();
							return dataList.getPatientList().get(i).getId();
						}
					}
		}
		
		failedLoginAttempt++;
		return "";
	}
	
	public int getFailedLoginAttempt() {
		return failedLoginAttempt;
	}
	
	public LocalDateTime getLockTimeEnded() {
		return lockTimeEnded;
	}
	
	public boolean isLocked() {
		 if (lockTimeEnded == null)
			 return false;
		 else if (lockTimeEnded.isBefore(LocalDateTime.now()))
			 return false;
		 else 
			 return true;
	}
	
	public void setLockTimeEnded() {
		lockTimeEnded = LocalDateTime.now().plusSeconds(LOCK_TIME_LENGTH);
	}

	// if the user restart the program, the lockTimeEnded is loss, the account will not be locked
	public void lock() {
		try {
			Thread.sleep(ChronoUnit.SECONDS.between(LocalDateTime.now(), lockTimeEnded) * 1000); 
		} catch (InterruptedException e) {
			e.printStackTrace();
			 //Thread.currentThread().interrupt();
		}
		unlock(); // Lock time expired
	}
	
	// use this if login successfully or lock time expired
	private void unlock() {
		failedLoginAttempt = 0;
		lockTimeEnded = null;
	}
}
