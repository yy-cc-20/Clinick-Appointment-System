package controller;

import java.sql.Connection;
import java.util.List;
import java.sql.Statement;
import java.sql.SQLException;

import database.DatabaseConnection;
import entity.*;

public class ManagePatientController {
	private final List<Patient> patients = DataList.getInstance().getPatientList(null, "", "");

    public Patient searchPatient(String patientIc) {
		for (Patient patient : patients) {
			if (patient.getIc().toLowerCase().contains(patientIc)) {
				return patient;
			}
		}
        return null;
    }

    public void addPatient(String name, String patientIc, String phone, String address, String password) {
		try{
			Connection conn = DatabaseConnection.getConnection();
			Statement st = conn.createStatement();
			conn.setAutoCommit(false);

			st.executeUpdate("INSERT IGNORE INTO Patient (name, ic, phone, address, password) " +
					"VALUES ('"+name+"','"+patientIc+"','"+phone+"','"+address+"','"+password+"')");
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException sqlException){
			System.out.println("Error: SQL Exception!");
		}

    }

	public void updatePatientProfile(String phoneNo, String address, int patientIc) {
		try{
			Connection conn = DatabaseConnection.getConnection();
			Statement st = conn.createStatement();
			conn.setAutoCommit(false);

			st.executeUpdate("UPDATE Patient SET phone='"+phoneNo+"', address='"+address+"' WHERE id='"+patientIc+"'");
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException sqlException){
			System.out.println("Error: SQL Exception!");
		}
	}
}

