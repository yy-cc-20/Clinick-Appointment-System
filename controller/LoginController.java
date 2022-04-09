package controller;

public class LoginController {
	
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
		userDatabase.saveFailedLoginAttemptAndLockTimeEnded();
		displayLockMessage();
		
		try {
			Thread.sleep(LOCK_TIME_LENGTH * 1000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
			 //Thread.currentThread().interrupt();
		}
		
		// unlock, if the user restart the program, the following statement will not be executed
		failedLoginAttempt = 0;
		userDatabase.saveFailedLoginAttemptAndLockTimeEnded();
		/* reset failedLoginAttempt if
		 * login successfully or
		 * lock time expired
		 */
	}
	
	private boolean loginSuccessfully() {
		for (int i = 0; i < userList.size(); ++i) {
			if (currentUser.equals(userList.get(i))) {
				currentUserIndex = i;
				return true;
			}
		}
		return false;
	}
	
	// lock account and exit program if fail too many times
		// the user still has to login after sign up an account
		// if the user forgot the account name or the password, no mechanism to change the password
		private User login() {
			if (isLocked()) {
				displayLockMessage();
				try {
					Thread.sleep(ChronoUnit.SECONDS.between(LocalDateTime.now(), lockTimeEnded) * 1000); 
				} catch (InterruptedException e) {
					e.printStackTrace();
					 //Thread.currentThread().interrupt();
				}
				failedLoginAttempt = 0;
				userDatabase.saveFailedLoginAttemptAndLockTimeEnded();
			} 
			
			while (true) {
				currentUser.askUsername();
				currentUser.askPassword();
				if (loginSuccessfully()) {
					System.out.printf("%nHello " + currentUser.getUsername() + "!%n");
					failedLoginAttempt = 0;
					userDatabase.saveFailedLoginAttemptAndLockTimeEnded();
					return currentUser;
				} else {
					++failedLoginAttempt;
					userDatabase.saveFailedLoginAttemptAndLockTimeEnded();
					
					if(MAX_FAILED_LOGIN_ATTEMPT > failedLoginAttempt) {
						System.out.printf("Username or password is invalid. Remains %d chance(s).%n%n", MAX_FAILED_LOGIN_ATTEMPT - failedLoginAttempt);
					} else {
						lock();
					}
				}
			}
		}
}
