package boundary;

import entity.*;
import boundary.*;

public class ClinickAppointmentSystem {
	public static void main(String[] args) {
		
		// instantiating the dataList object
		IDataStore dataList = new DataList();

		// instantiating the controllers
		LoginController loginController = new LoginController();
		MakeAppointmentController makeAppointmentController = new MakeAppointmentController();
		ManageAppointmentController manageAppointmentController = new ManageAppointmentController();
		ManageAccountController manageAccountController = new ManageAccountController();
		ManagePatientController managePatientController = new ManagePatientController();

		// setting the controllers to the dataList object
		loginController.setDataLists(dataLists);
		makeAppointmentController.setDataLists(dataLists);
		manageAppointmentController.setDataLists(dataLists);
		manageAccountController.setDataLists(dataLists);
		managePatientController.setDataLists(dataLists);

		// instantiating the user interfaces
		LoginUI loginInterface = new LoginUI();
		MakeAppointmentUI makeAppointmentInterface = new MakeAppointmentUI();
		ManageAppointmentUI manageAppointmentInterface = new ManageAppointmentUI();
		ManageAccountUI manageAccountInterface = new ManageAccountUI();
		ManagePatientUI managePatientInterface = new ManagePatientUI();

		// setting the user interfaces to their controllers
		loginInterface.setController(loginController);
		makeAppointmentInterface.setController(makeAppointmentController);
		manageAppointmentInterface.setController(manageAppointmentController);
		manageAccountInterface.setController(manageAccountController);
		managePatientInterface.setController(managePatientController);
		
		User systemUser = new LoginView().login();
		
		int eventNo; // the action that user wants to perform
		final int beginEventNo = 1; 
		final int endEventNo = 3;
		
		ConsoleUI.displaySystemName("System Name");
		
		// Retrieve data from database
		DataList.importDoctorList(null);
		// ...

		while (true) {
			ConsoleUI.displaySystemName("System Name");
			ConsoleUI.displayMenu(); // need to change the menu
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
