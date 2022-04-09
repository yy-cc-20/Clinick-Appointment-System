package entity;

import entity.Doctor;

import java.util.ArrayList;
import java.util.List;

/* 
 * IDataStore dataList = DataList.getInstance(); // Already retrieved the data
 */

public class DataList implements IDataStore {
	private static IDataStore instance;
	private List<Doctor> doctorList; // Use List interface instead of ArrayList, more flexible, reduce the dependency
	private List<Patient> patientList;
	private List<Receptionist> receptionistList;
	
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
	
	public List<Doctor> getDoctorList() {
		if (doctorList == null)
			return new ArrayList<Doctor>();
		else 
			return doctorList;
	}
	
	public List<Patient> getPatientList() {
		if (patientList == null)
			return new ArrayList<Patient>();
		else 
			return patientList;
	}
	
	public List<Receptionist> getReceptionistList() {
		if (receptionistList == null)
			return new ArrayList<Receptionist>();
		else 
			return receptionistList;
	}
}
