package controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.SQLException;
import database.DatabaseConnection;
import entity.*;

public class ManagePatientController {
	private final List<Patient> patients = DataList.getInstance().getPatientList();
	private static ManagePatientController instance;
	private Statement st;

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
    	
    	List<Patient> patientList = new ArrayList<>();
    	patientList.add(new Patient(name, patientIc, phone, address));
    	
/*    	try {
    		st.executeUpdate("INSERT INTO PATIENT(name, patientIc, phone, address)" +
    			"VALUES ('"+name+"','"+patientIc+"', '"+phone+"','"+address+"')");
    	} catch (SQLException e) {
    		e.printStackTrace();
   	    }
*/
    }
}

