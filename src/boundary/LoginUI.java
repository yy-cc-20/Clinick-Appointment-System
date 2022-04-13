package boundary;

import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import controller.LoginController;
import entity.*;

/* 
 * Suspend the user to login for 10 seconds after 3 failed login attempts
 * From the return user object can know the username, id, password and user type
 */

public class LoginUI {
	private final LoginController loginController = new LoginController();
	private boolean hasLogin;
	private int role;
	private int userid;
	private String password;
	private String username;
	
	// Use this method for login
	// Lock account and exit program if fail too many times
	public void login() {
		hasLogin = false;
		
		while (true) {
			// Get input from user ----------------
			displayLoginMenu();
			role = ConsoleInput.askChoice(1, 4, "Sign in as");
			
			if (role == 4) // Guest
				break;
						
			userid = ConsoleInput.askInt("User ID");
			System.out.print("Password> ");
			password = SingletonScanner.nextLine();
			
			// Interact with the controller -------------------
			username = loginController.loginSuccessfully(role, userid, password);
			if (!username.isBlank()) { // Successfully login
				System.out.printf("%nHello " + username + "!%n");
				hasLogin = true;
				break;
			} else { 
				// Login failed	
				if (loginController.getFailedLoginAttempt() < LoginController.MAX_FAILED_LOGIN_ATTEMPT) { 
					// Still have chances to login
					System.out.printf("Username or password is invalid. Remains %d chance(s).%n%n"
							, LoginController.MAX_FAILED_LOGIN_ATTEMPT - loginController.getFailedLoginAttempt());
				} else { 
					// Failed too many times
					lockAccount();
					loginController.resetFailedLoginAttempts();
				}
			}
		} // End while
	}
	
	public User getUser() {
		return switch (role) {
			case 1 -> new Receptionist(userid, username, password);
			case 2 -> new Doctor(userid, username, password);
			case 3 -> new Patient(userid, username, password);
			case 4 -> new User(); // Guest
			default -> throw new IllegalArgumentException();
		};
	}
	
	public boolean getHasLogin() {
		return hasLogin;
	}
	
	private void displayLoginMenu() {
		System.out.println();
		System.out.println("[1]Receptionist");
		System.out.println("[2]Doctor");
		System.out.println("[3]Patient");
		System.out.println("[4]Guest Mode");
	}
	
	// TODO a bug: If the user restart the program, the lockTimeEnded is loss, the account will not be locked
	private void lockAccount() {
		LocalDateTime lockTimeEnded = LocalDateTime.now().plusSeconds(LoginController.LOCK_TIME_LENGTH); // Untill when the account will be unlocked
		
		Toolkit.getDefaultToolkit().beep(); // Emit a beep sound
		System.out.print("Please try again after " +  ChronoUnit.SECONDS.between(LocalDateTime.now(), lockTimeEnded) + " second(s)."); // cannot act as a String data member, as the time interval is changing
		
		// If user keep pressing the keyboard while being suspended from login
		while (!lockTimeEnded.isBefore(LocalDateTime.now()) && SingletonScanner.hasNextLine()) {
			SingletonScanner.nextLine(); // Allow the user press the keyboard and response to it
			if (!lockTimeEnded.isBefore(LocalDateTime.now())) // Check again, to avoid display negative time interval when the suspended period is over
				System.out.print("Please try again after " +  ChronoUnit.SECONDS.between(LocalDateTime.now(), lockTimeEnded) + " second(s).");
		}
		System.out.println();
	}
	/*
	// LoginUI test
	public static void main(String[] args) {
		LoginUI loginUI = new LoginUI();
		loginUI.login();
		System.out.println(loginUI.getUser().getUserId());
	}*/
}
