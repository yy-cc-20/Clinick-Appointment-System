package boundary;

import controller.ManagePatientController;
import entity.Patient;

public class ManagePatientUI {

    private static ManagePatientController controller = new ManagePatientController();

    public static Patient searchPatient() {
        String patientIc = ConsoleInput.askString("patient IC");
        Patient selectedPatient = controller.searchPatient(patientIc);

        if (selectedPatient == null) {
            System.out.println("No patient with IC " + patientIc + " found.");
            if (ConsoleInput.askBoolean("Continue to create new patient profile"))
                createPatientProfile(patientIc);
        } else {
            System.out.println("Search Results:");
            displayPatient(selectedPatient);
        }
        return selectedPatient;
    }

    public static void displayPatient(Patient selectedPatient) {
        System.out.println("Patient ID \t| Patient \t| Phone No \t| Patient IC \t| Address");
        System.out.println(
                selectedPatient.getUserId() + " \t| " + selectedPatient.getUsername() + " \t| " + selectedPatient.getPhoneNo()
                        + " \t| " + selectedPatient.getIc() + " \t| " + selectedPatient.getAddress());
    }

    public static void createPatientProfile(String patientIc) {
        String name = ConsoleInput.askString("new patient name");

        controller.addPatient(name, patientIc);
        System.out.println("New patient ID generated.   ");
        System.out.println("New patient profile created.");

        Patient selectedPatient = controller.searchPatient(patientIc);
        displayPatient(selectedPatient);
    }

    public void managePatientProfile() {
        Patient selectedPatient = searchPatient();
        String phoneNo = ConsoleInput.askStringV2("new patient phone number (PRESS ENTER TO SKIP)");
        String address = ConsoleInput.askStringV2("new patient address (PRESS ENTER TO SKIP)");

        if (ConsoleInput.askBoolean("Confirm changes")) {
            if (phoneNo == null && address == null) {
                System.out.println("No changes has been made.");
            } else if (phoneNo != null && address == null) {
                selectedPatient.setPhoneNo(phoneNo);
                System.out.println("Phone number has been updated.");
            } else if (phoneNo == null && address != null) {
                selectedPatient.setAddress(address);
                System.out.println("Address has been updated.");
            } else if (phoneNo != null && address != null) {
                selectedPatient.setPhoneNo(phoneNo);
                selectedPatient.setAddress(address);
                System.out.println("Phone number and address has been updated.");
            }
        }
    }

}
