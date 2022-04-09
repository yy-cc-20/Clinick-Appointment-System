package boundary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import entity.*;
import controller.LoginController;

public class LoginView {
	private LoginController loginController = new LoginController();
		
	// lock account and exit program if fail too many times
	public User login() {
		int role;
		String username;
		String password;
		String userid;
		
		if (loginController.isLocked()) {
			displayLockMessage();
			loginController.continueLock();
		} 
		
		while (true) {
			System.out.println("Login as");
			System.out.println("[1]Receptionist");
			System.out.println("[2]Doctor");
			System.out.println("[3]Patient");
			role = ConsoleUI.askEventNo(1, 3);
			
			System.out.print("> Username ");
			username = KeyboardInput.scanner.nextLine();
			
			System.out.print("> Password ");
			password = KeyboardInput.scanner.nextLine();
			
			userid = loginController.login(role, username, password);
			if (!userid.isBlank()) {
				System.out.printf("%nHello " + username + "!%n");
				switch (role) {
					case 1: return new Receptionist(userid, username, password);
					case 2: return new Doctor(userid, username, password);
					default: // case 3
						return new Patient(userid, username, password);
				}
				
			} else {				
				if (LoginController.MAX_FAILED_LOGIN_ATTEMPT > loginController.getFailedLoginAttempt()) {
					System.out.printf("Username or password is invalid. Remains %d chance(s).%n%n", LoginController.MAX_FAILED_LOGIN_ATTEMPT - loginController.getFailedLoginAttempt());
				} else {
					displayLockMessage();
					loginController.lock();
				}
			}
		} // end while
	}
	
	private void displayLockMessage() {
		System.out.print("Please try again after " +  ChronoUnit.SECONDS.between(LocalDateTime.now(), loginController.getLockTimeEnded()) + " second(s).\r");
		// cannot act as a String data member, as the time interval is changing
	}
}
