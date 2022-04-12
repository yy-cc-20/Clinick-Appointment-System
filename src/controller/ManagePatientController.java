package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import entity.*;

public class ManagePatientController {
	private final List<Patient> patients = DataList.getInstance().getPatientList();
	private static ManagePatientController instance;
	private Statement st;
	private ResultSet rs;
	private String sql;

	public ManagePatientController() {
		try {
			st = DatabaseConnection.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ManagePatientController getInstance() {
		if (instance == null)
			new ManagePatientController();
		return instance;
	}

    public Patient searchPatient(String patientIc) {
		for (Patient patient : patients) {
			if (patient.getIc().toLowerCase().contains(patientIc)) {
				return patient;
			}
		}
        return null;
    }

    public void addPatient(String name, String patientIc, String phone, String address) {

//    	sql = "INSERT INTO PATIENT('NAME','USER_ID','PHONE_NO','ADDRESS','PATIENT_IC') VALUES ('" + name + "', '-', '-', '-', '" + patientIc +"');";
//    	try {
//    	     rs = st.executeQuery(sql);
//    	    	}
//
//    	} catch (SQLException e) {
//    	    	e.printStackTrace();
//    	    }
    }
}

