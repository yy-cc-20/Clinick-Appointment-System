package boundary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import controller.LoginController;
import entity.User;

public class LoginView {
	private LoginController loginController = new LoginController();
	private
	
	// lock account and exit program if fail too many times
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
		} 
		
		while (true) {
			currentUser.askUsername();
			currentUser.askPassword();
			if (loginSuccessfully()) {
				System.out.printf("%nHello " + currentUser.getUsername() + "!%n");
				failedLoginAttempt = 0;
				return currentUser;
			} else {
				++failedLoginAttempt;
				
				if (MAX_FAILED_LOGIN_ATTEMPT > failedLoginAttempt) {
					System.out.printf("Username or password is invalid. Remains %d chance(s).%n%n", MAX_FAILED_LOGIN_ATTEMPT - failedLoginAttempt);
				} else {
					displayLockMessage();
					lock();
				}
			}
		}
	}
	
	private void displayLockMessage() {
		System.out.print("Please try again after " +  ChronoUnit.SECONDS.between(LocalDateTime.now(), lockTimeEnded) + " second(s).\r");
		// cannot act as a String data member, as the time interval is changing
	}
	
	void askUsername() {
		System.out.print("> Username ");
		username = Singleton.keyboard.nextLine();
	}
	
	void askPassword() {
		System.out.print("> Password ");
		password = Singleton.keyboard.nextLine();
	}
}
