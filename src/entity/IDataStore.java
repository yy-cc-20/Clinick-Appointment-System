package entity;

import java.util.List;

public interface IDataStore {
	// Use List interface instead of ArrayList, more flexible, reduce the dependency
	List<Doctor> getDoctorList();
	List<Patient> getPatientList();
	List<Receptionist> getReceptionistList();
	List<Appointment> getAppointmentList();
	List<Allocation> getAllocationList();
	List<Branch> getBranchList(String query, String column, String data);
	List<Service> getServiceList();
	List<Branch> getBranchesById(List<Integer> ids); // Return branch objects of the specified ids
}
