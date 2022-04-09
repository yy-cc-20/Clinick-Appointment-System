package entity;

import java.util.ArrayList;

/* 
 * IDataStore dataList = DataList.getInstance(); // Already retrieved the data
 */

public class DataList implements IDataStore {
	private static IDataStore instance;
	private ArrayList<Doctor> doctorList;
	private ArrayList<Patient> patientList;
	private ArrayList<Receptionist> receptionistList;
	
	private DataList() {
		importDoctorList();
		importPatientList();
		importReceptionistList();
	} // Private constructor for singleton !!!
	
	public static IDataStore getInstance() {
		if (instance == null) {
			instance = new DataList();
		}
		return instance;
	}
	
	public void importDoctorList() {
		// Test data for login
		doctorList = new ArrayList<>();
		doctorList.add(new Doctor("1", "username", "password"));
	}
	
	public void importPatientList() {
		
	}
	
	public void importReceptionistList() {
		
	}
	
	public ArrayList<Doctor> getDoctorList() {
		if (doctorList == null)
			return new ArrayList<Doctor>();
		else 
			return doctorList;
	}
	
	public ArrayList<Patient> getPatientList() {
		if (patientList == null)
			return new ArrayList<Patient>();
		else 
			return patientList;
	}
	
	public ArrayList<Receptionist> getReceptionistList() {
		if (receptionistList == null)
			return new ArrayList<Receptionist>();
		else 
			return receptionistList;
	}
}
