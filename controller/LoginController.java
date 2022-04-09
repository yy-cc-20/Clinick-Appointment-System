package controller;

import entity.*;

import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LoginController {
	private User currentUser;
	private int currentUserIndex;
		
	// security: lock the system after a few failed logins
	int failedLoginAttempt = 0;
	LocalDateTime lockTimeEnded;
	
	private static final int MAX_FAILED_LOGIN_ATTEMPT = 3;
	private static final int LOCK_TIME_LENGTH = 10; // in seconds
	
	public int failedLoginAttempt() {
		return failedLoginAttempt;
	}
	
	public LocalDateTime getLockTimeEnded() {
		return lockTimeEnded;
	}
	
	private boolean isLocked() {
		 if (lockTimeEnded == null)
			 return false;
		 else if (lockTimeEnded.isBefore(LocalDateTime.now()))
			 return false;
		 else 
			 return true;
	}
	
	private void lock() {
		Toolkit.getDefaultToolkit().beep(); // emit a beep sound
		lockTimeEnded = LocalDateTime.now().plusSeconds(LOCK_TIME_LENGTH);
		
		try {
			Thread.sleep(LOCK_TIME_LENGTH * 1000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
			 //Thread.currentThread().interrupt();
		}
		
		// unlock, if the user restart the program, the following statement will not be executed
		unLock();
		// reset failedLoginAttempt if login successfully or lock time expired
	}
	
	private void unLock() {
		failedLoginAttempt = 0;
	}
	
	private boolean loginSuccessfully() {
		for (int i = 0; i < DataList.getDoctorList().size(); ++i) {
			if (currentUser.equals(DataList.getDoctorList().get(i))) {
				currentUserIndex = i;
				unLock();
				return true;
			}
		}
		return false;
	}
}
