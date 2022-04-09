/*package boundary;

import java.util.ArrayList;
import java.util.Scanner;
import controller.ManagePatientController;

public class ManagePatientUI{
	
	private final static int length = 30;
	
	public void createPatientProfile(){
		
		String name;
		String nameCreated;
		String phoneNum;
		String phoneNumCreated;
		String address;
		String addressCreated;
		String patientCreated;
		String icPassport;
		String icPassportCreated;
		String profile;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter new patient name: ");
		name = scanner.next();
		nameCreated = KeyboardInput.askName(name, length);
		
		System.out.println("Enter new patient phone number: ");
		phoneNum = scanner.next();
		phoneNumCreated = KeyboardInput.askString(phoneNum);
		
		System.out.println("Enter new patient IC / passport number: ");
		icPassport = scanner.next();
		icPassportCreated = KeyboardInput.askString(icPassport);
		
		System.out.println("Enter new patient address: ");
		address = scanner.next();
		addressCreated = KeyboardInput.askString(address);
		
		profile = ManagePatientController.profileCreatedSuccessfully(nameCreated, phoneNumCreated, icPassportCreated, addressCreated);
				
		System.out.println("New patient ID generated.\n");
		System.out.println("New patient profile created.");
	}
	
	public void managePatientProfile() {
		
	}
	
	public void searchPatient() {
	}
	
}

*/
