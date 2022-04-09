package entity;

import java.util.List;

public interface IDataStore {
	void importDoctorList();
	void importPatientList();
	void importReceptionistList();
	List<Doctor> getDoctorList();
	List<Patient> getPatientList();
	List<Receptionist> getReceptionistList();
}
