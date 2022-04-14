package controller;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import entity.*;

public class ManagePatientController {
	private Statement st;
	
	public ManagePatientController() {
		try {
			st = DatabaseConnection.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    public List<Patient> searchPatient(String patientIc) {
        List<Patient> patients = DataList.getPatientList();
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

    // provide the patient's full information to add into database
    public void addPatient(String name, String ic, String phone, String address, String password) {
        try {
            st.executeUpdate("INSERT IGNORE INTO patient (name, ic, phone, address, password) " +
                    "VALUES ('" + name + "','" + ic + "','" + phone + "','" + address + "','" + password + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePatientProfile (String phoneNo, String address, int patientId){
        try {
            st.executeUpdate("UPDATE Patient SET phone='" + phoneNo + "', address='" + address + "' WHERE id='" + patientId + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

