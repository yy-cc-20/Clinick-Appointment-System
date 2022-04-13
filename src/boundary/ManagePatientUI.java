package boundary;

import controller.ManagePatientController;
import entity.DataList;
import entity.Patient;
import entity.Receptionist;
import entity.User;

import java.util.ArrayList;
import java.util.List;

public class ManagePatientUI {
    private static final ManagePatientController controller = new ManagePatientController();
    private User systemUser;

    public ManagePatientUI(User systemUser) {
        this.systemUser = systemUser;
    }

    public List<Patient> searchPatient() {
        String patientIc = ConsoleInput.askString("patient IC").toLowerCase();
        List<Patient> searchedPatients = controller.searchPatient(patientIc);

        if (searchedPatients == null) {
            System.out.println("No patient with IC " + patientIc + " found.");
            if (ConsoleInput.askBoolean("Continue to create new patient profile"))
                searchedPatients = new ArrayList<>();
            searchedPatients.add(createPatientProfile());
        } else {
            System.out.println("Search Results:");
            for (Patient selectedPatient : searchedPatients) {
                displayPatient(selectedPatient);
            }

        }
        return searchedPatients;
    }

    public Patient selectPatient(List<Patient> searchedPatients) {
        int patientId = ConsoleInput.askPositiveInt("Select a patient ID");
        Patient thePatient = null;
        for (Patient searchedPatient : searchedPatients) {
            if (searchedPatient.getUserId() == patientId) {
                thePatient = searchedPatient;
                System.out.println("Selected Patient:");
                displayPatient(thePatient);
            }
        }
        return thePatient;
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

        Patient selectedPatient = controller.searchPatient(ic).get(0);
        displayPatient(selectedPatient);
        return selectedPatient;
    }

    public void managePatientProfile() {
        int patientId = 0;
        String oriAddress = "";
        String oriPhoneNo = "";
        if (systemUser instanceof Receptionist) {
            List<Patient> searchedPatients = searchPatient();
            Patient selectedPatient = selectPatient(searchedPatients);
            if (selectedPatient == null) {
                System.out.println("No patient selected.");
                return;
            }
            patientId = selectedPatient.getUserId();
            oriAddress = selectedPatient.getAddress();
            oriPhoneNo = selectedPatient.getPhoneNo();
        } else if (systemUser instanceof Patient) {
            patientId = systemUser.getUserId();
            oriAddress = ( (Patient) systemUser ).getAddress();
            oriPhoneNo = ( (Patient) systemUser ).getPhoneNo();
        }

        String phoneNo = ConsoleInput.askStringV2("new patient phone number (PRESS ENTER TO SKIP)");
        String address = ConsoleInput.askStringV2("new patient address (PRESS ENTER TO SKIP)");

        if (ConsoleInput.askBoolean("Confirm changes")) {
            if (phoneNo == null && address == null) {
                System.out.println("No changes has been made.");
            } else if (phoneNo != null && address == null) {
                controller.updatePatientProfile(phoneNo, oriAddress, patientId);
                System.out.println("Phone number has been updated.");
            } else if (phoneNo == null) {
                controller.updatePatientProfile(oriPhoneNo, address, patientId);
                System.out.println("Address has been updated.");
            } else {
                controller.updatePatientProfile(phoneNo, address, patientId);
                System.out.println("Phone number and address has been updated.");
            }
        }
        Patient modified = DataList.getInstance().getPatientList("filter", "id", Integer.toString(patientId)).get(0);
        displayPatient(modified);
    }
}
