package entity;

import java.util.List;

public interface IDataStore {
	// Use List interface instead of ArrayList, more flexible, reduce the dependency
	/*
	void importDoctorList();
	void importPatientList();
	void importReceptionistList();
	void importAppointmentList();
	void importAllocationList();
	void importBranchList();
	void importServiceList();
	*/
	List<Doctor> getDoctorList();
	List<Patient> getPatientList();
	List<Receptionist> getReceptionistList();
	List<Appointment> getAppointmentList(String query, String column, String data);
	List<Allocation> getAllocationList();
	List<Branch> getBranchList(String query, String column, String data);
	List<Service> getServiceList();
	List<Branch> getBranchesById(List<Integer> ids); // Return branch objects of the specified ids
	void updateAppointmentTable(String column, String data);
}
