package controller;

import java.util.ArrayList;
import java.util.List;

import entity.*;

public class ManagePatientController {

    public List<Patient> searchPatient(String patientIc) {
        List<Patient> patients = DataList.getInstance().getPatientList(null, "", "");
        List<Patient> searchList = new ArrayList<>();
        boolean found = false;
        for (Patient patient : patients) {
            if (patient.getIc().toLowerCase().contains(patientIc)) {
                searchList.add(patient);
                found = true;
            }
        }
        if (found) {
            return searchList;
        }
        return null;
    }

    public void addPatient(String name, String patientIc, String phone, String address, String password) {
        DataList.getInstance().addPatientFull(name, patientIc, phone, address, password);
    }

    public void updatePatientProfile(String phoneNo, String address, int patientId) {
        DataList.getInstance().updatePatient(phoneNo, address, patientId);
    }
}

