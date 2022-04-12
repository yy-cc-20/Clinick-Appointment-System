package boundary;

import controller.ManagePatientController;
import entity.Patient;

public class ManagePatientUI {
    private static final ManagePatientController controller = new ManagePatientController();

    public Patient searchPatient() {
        String patientIc = ConsoleInput.askString("patient IC").toLowerCase();
        Patient selectedPatient = controller.searchPatient(patientIc);

        if (selectedPatient == null) {
            System.out.println("No patient with IC " + patientIc + " found.");
            if (ConsoleInput.askBoolean("Continue to create new patient profile"))
                selectedPatient = createPatientProfile();
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

    public Patient createPatientProfile() {
        String name = ConsoleInput.askString("New patient name");
        String phone = ConsoleInput.askString("New patient phone number");
        String ic = ConsoleInput.askString("New patient IC");
        String address = ConsoleInput.askString("New patient address");
        String password = ConsoleInput.askString("New password");

        controller.addPatient(name, ic, phone, address, password);
        System.out.println("New patient ID generated.   ");
        System.out.println("New patient profile created.");

        Patient selectedPatient = controller.searchPatient(ic);
        displayPatient(selectedPatient);
        return selectedPatient;
    }

    public void managePatientProfile() {
        Patient selectedPatient = searchPatient();
        String phoneNo = ConsoleInput.askStringV2("new patient phone number (PRESS ENTER TO SKIP)");
        String address = ConsoleInput.askStringV2("new patient address (PRESS ENTER TO SKIP)");

        int patientId = selectedPatient.getUserId();
        if (ConsoleInput.askBoolean("Confirm changes")) {
            if (phoneNo == null && address == null) {
                System.out.println("No changes has been made.");
            } else if (phoneNo != null && address == null) {
                controller.updatePatientProfile(phoneNo, selectedPatient.getAddress(), patientId);
                System.out.println("Phone number has been updated.");
            } else if (phoneNo == null) {
                controller.updatePatientProfile(selectedPatient.getPhoneNo(), address, patientId);
                System.out.println("Address has been updated.");
            } else {
                controller.updatePatientProfile(phoneNo, address, patientId);
                System.out.println("Phone number and address has been updated.");
            }
        }
    }
}
