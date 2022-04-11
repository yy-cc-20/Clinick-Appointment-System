package controller;

import java.sql.*;
import database.DatabaseConnection;
import entity.*;

public class ManagePatientController {

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

//    	sql = "SELECT * FROM PATIENT WHERE patientIc = " + patientIc;
//    	try {
//    	     rs = st.executeQuery(sql);
//    	    while(rs.next()) {
//    	    	Patient patient = new Patient();
//                patient.setName(1, rs.getString(1));
//                patient.setUserId(2, rs.getString(2));
//                patient.setPhoneNo(3,rs.getString(3));
//                patient.setAddress(4,rs.getString(4));
//                patient.setIc(patientIc);
//    	    	}
//
//    	} catch (SQLException e) {
//    	    	e.printStackTrace();
//    	    }

        return new Patient();
    }

    public void addPatient(String name, String patientIc) {

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

