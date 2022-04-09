package boundary;

public class LoginView {
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
