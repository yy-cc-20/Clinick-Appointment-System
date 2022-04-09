package boundary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import controller.LoginController;
import entity.User;

public class LoginView {
	private LoginController loginController = new LoginController();
		
	// lock account and exit program if fail too many times
	public User login() {
		if (loginController.isLocked()) {
			displayLockMessage();
			try {
				Thread.sleep(ChronoUnit.SECONDS.between(LocalDateTime.now(), loginController.getLockTimeEnded()) * 1000); 
			} catch (InterruptedException e) {
				e.printStackTrace();
				 //Thread.currentThread().interrupt();
			}
			loginController.unlock();
		} 
		
		while (true) {
			System.out.print("> Username ");
			String username = KeyboardInput.scanner.nextLine();
			
			System.out.print("> Password ");
			String password = KeyboardInput.scanner.nextLine();
			
			if (loginController.loginSuccessfully(username, password)) {
				System.out.printf("%nHello " + username + "!%n");
				return currentUser;
			} else {
				++loginController.failedLoginAttempt;
				
				if (loginController.MAX_FAILED_LOGIN_ATTEMPT > loginController.getFailedLoginAttempt()) {
					System.out.printf("Username or password is invalid. Remains %d chance(s).%n%n", loginController.MAX_FAILED_LOGIN_ATTEMPT - loginController.getFailedLoginAttempt());
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
