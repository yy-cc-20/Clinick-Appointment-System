package boundary;

import java.sql.SQLException;

import entity.DataList;
import entity.DatabaseConnection;
import entity.IDataStore;
import entity.User;

public class ClinickAppointmentSystem {
	public static void main(String[] args) throws SQLException {
		// Retrieve data from database
		IDataStore dataList = DataList.getInstance(); // Already retrieved the data
		
		// instantiating the user interfaces
/*		MakeAppointmentUI makeAppointmentInterface = new MakeAppointmentUI();
		ManageAppointmentUI manageAppointmentInterface = new ManageAppointmentUI();
		ManageAccountUI manageAccountInterface = new ManageAccountUI();
		ManagePatientUI managePatientInterface = new ManagePatientUI();
	*/	
		// the test data is created in DataList
		// role: doctor
		// userid: 1
		// username: username
		// password: password
		
		ConsoleUI.displaySystemName("Clinic Booking System");
		User systemUser = new LoginUI().login(); // Suspend the user to login for 10 seconds after 3 failed login attempts
		// From systemUser, you can know the username, id, password, user type
		
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
				// Just to test the method, you may change the sequence of the code
				ConsoleUI.displayFunctionName("Account Setting");
				new ManageAccountUI().modifyAccount(systemUser);

				break;

			case 2: // Modify Account Info
				ConsoleUI.displayFunctionName(" Modify Account Details ");
				break;

			case 3: // logout and exit the program
				ConsoleUI.displayFunctionName(" Program Stopped ");

				SingletonScanner.scanner.close();
				DatabaseConnection.closeConnection();

				SingletonScanner.scanner.close();

				System.exit(0);
			}
			ConsoleUI.clearScreen();
		}
	}
}

