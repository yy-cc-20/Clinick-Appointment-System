package entity;

import java.util.ArrayList;

public class DataList implements IDataStore {
	private static ArrayList<Doctor> doctorList;
	private static ArrayList<Patient> patientList;
	private static ArrayList<Receptionist> receptionistList;
	
	public static void importDoctorList(ArrayList<Doctor> d) {
		doctorList = new ArrayList<>(d);
	}
	
	public static ArrayList<Doctor> getDoctorList() {
		if (doctorList == null)
			return new ArrayList<Doctor>();
		else 
			return doctorList;
	}
}
