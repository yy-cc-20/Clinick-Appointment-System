package boundary;

import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import controller.LoginController;
import entity.*;

public class LoginView {
	private LoginController loginController = new LoginController();
	
	// Use this method for login
	// lock account and exit program if fail too many times
	public User login() {
		int role;
		String userid;
		String password;
		String username;
		
		while (true) {
			// Get input from user
			System.out.println("Login as");
			System.out.println("[1]Receptionist");
			System.out.println("[2]Doctor");
			System.out.println("[3]Patient");
			role = ConsoleUI.askEventNo(1, 3);
			
			System.out.print("> User ID ");
			userid = SingletonScanner.scanner.nextLine();
			
			System.out.print("> Password ");
			password = SingletonScanner.scanner.nextLine();
			
			// Interact with the controller
			username = loginController.loginSuccessfully(role, userid, password);
			if (!username.isBlank()) { // Successfully login
				System.out.printf("%nHello " + username + "!%n");
				switch (role) {
					case 1: return new Receptionist(userid, username, password);
					case 2: return new Doctor(userid, username, password);
					default: return new Patient(userid, username, password); // case 3
				}
			} else { // Login failed	
				if (LoginController.MAX_FAILED_LOGIN_ATTEMPT > loginController.getFailedLoginAttempt()) { // Still have chance to login
					System.out.printf("Username or password is invalid. Remains %d chance(s).%n%n", LoginController.MAX_FAILED_LOGIN_ATTEMPT - loginController.getFailedLoginAttempt());
				} else { // Failed too many times
					lockAccount();
					loginController.resetFailedLoginAttempts();
				}
			}
		} // end while
	}
	
	// TODO a bug: if the user restart the program, the lockTimeEnded is loss, the account will not be locked
	private void lockAccount() {
		LocalDateTime lockTimeEnded = LocalDateTime.now().plusSeconds(LoginController.LOCK_TIME_LENGTH); // Untill when the account will be unlocked
		
		Toolkit.getDefaultToolkit().beep(); // emit a beep sound
		System.out.print("Please try again after " +  ChronoUnit.SECONDS.between(LocalDateTime.now(), lockTimeEnded) + " second(s)."); // cannot act as a String data member, as the time interval is changing
		
		// If user keep pressing the keyboard while being suspended from login
		while (!lockTimeEnded.isBefore(LocalDateTime.now()) && SingletonScanner.scanner.hasNextLine()) {
			SingletonScanner.scanner.nextLine(); // Allow the user press the keyboard and response to it
			if (!lockTimeEnded.isBefore(LocalDateTime.now())) // Check again, to avoid display negative time interval when the suspended period is over
				System.out.print("Please try again after " +  ChronoUnit.SECONDS.between(LocalDateTime.now(), lockTimeEnded) + " second(s).");
		}
		System.out.println();
	}
}
