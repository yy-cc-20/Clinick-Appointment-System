package boundary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import controller.LoginController;
import entity.User;

public class LoginView {
	private LoginController loginController = new LoginController();
	private
	
	// lock account and exit program if fail too many times
	public User login() {
		if (loginController.isLocked()) {
			displayLockMessage();
			try {
				Thread.sleep(ChronoUnit.SECONDS.between(LocalDateTime.now(), loginController.lockTimeEnded) * 1000); 
			} catch (InterruptedException e) {
				e.printStackTrace();
				 //Thread.currentThread().interrupt();
			}
			loginController.failedLoginAttempt = 0;
		} 
		
		while (true) {
			System.out.print("> Username ");
			username = KeyboardInput.scanner.nextLine();
			
			System.out.print("> Password ");
			password = KeyboardInput.scanner.nextLine();
			
			if (loginSuccessfully(username, password)) {
				System.out.printf("%nHello " + currentUser.getUsername() + "!%n");
				return currentUser;
			} else {
				++loginController.failedLoginAttempt;
				
				if (loginController.MAX_FAILED_LOGIN_ATTEMPT > loginController.failedLoginAttempt) {
					System.out.printf("Username or password is invalid. Remains %d chance(s).%n%n", loginController.MAX_FAILED_LOGIN_ATTEMPT - loginController.failedLoginAttempt);
				} else {
					displayLockMessage();
					loginController.lock();
				}
			}
		}
	}
	
	private void displayLockMessage() {
		System.out.print("Please try again after " +  ChronoUnit.SECONDS.between(LocalDateTime.now(), loginController.getLockTimeEnded()) + " second(s).\r");
		// cannot act as a String data member, as the time interval is changing
	}
}
