package entity;

import java.util.ArrayList;

public interface IDataStore {
	void importDoctorList(ArrayList<Doctor> d);
	void importPatientList(ArrayList<Patient> d);
	void importReceptionistList(ArrayList<Receptionist> d);
	ArrayList<Doctor> getDoctorList();
	ArrayList<Patient> getPatientList();
	ArrayList<Receptionist> getReceptionistList();
}
