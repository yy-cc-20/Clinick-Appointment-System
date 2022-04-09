package entity;

import java.util.ArrayList;

public interface IDataStore {
	void importDoctorList();
	void importPatientList();
	void importReceptionistList();
	ArrayList<Doctor> getDoctorList();
	ArrayList<Patient> getPatientList();
	ArrayList<Receptionist> getReceptionistList();
}
