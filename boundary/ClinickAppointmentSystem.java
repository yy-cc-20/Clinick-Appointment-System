package boundary;

import entity.*;
import boundary.*;

public class ClinickAppointmentSystem {
	public static void main(String[] args) {
		// Retrieve data from database
		IDataStore dataList = DataList.getInstance(); // Already retrieved the data
		
		// the test data is created in DataList
		// role: doctor
		// username: username
		// password: password
		
		ConsoleUI.displaySystemName("Clinic Booking System");
		User systemUser = new LoginView().login(); // From the returned object you can know the username, id, password, user type
		ConsoleUI.clearScreen();
							
		int eventNo; // the action that user wants to perform
		final int beginEventNo = 1; 
		final int endEventNo = 3;
			
		while (true) {
			ConsoleUI.displaySystemName("System Name");
			//ConsoleUI.displayMenu(); // need to change the menu
			eventNo = ConsoleUI.askEventNo(beginEventNo, endEventNo);
			
			switch (eventNo) {
			case 1:
				ConsoleUI.displayFunctionName(" Add XX ");
				break;
				
			case 2: // Modify Account Info
				ConsoleUI.displayFunctionName(" Modify Account Details ");
				break;
				
			case 3: // logout and exit the program
				ConsoleUI.displayFunctionName(" Program Stopped ");
				KeyboardInput.scanner.close();
				System.exit(0);
			}
			ConsoleUI.clearScreen();
		}
	}
}
