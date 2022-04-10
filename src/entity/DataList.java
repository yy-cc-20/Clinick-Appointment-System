package entity;

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
	private List<Appointment> appointmentList;
	private List<Allocation> allocationList;
	private List<Branch> branchList;
	private List<Service>  serviceList;

	
	private DataList() {
		importDoctorList();
		importPatientList();
		importReceptionistList();
		importAppointmentList();
		importAllocationList();
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
		doctorList.add(new Doctor(1, "username", "password"));
	}
	
	public void importPatientList() {
		
	}
	
	public void importReceptionistList() {
		
	}

	public void importAllocationList() {
	}

	public void importBranchList() {

	}

	public void importServiceList() {

	}

	public void importAppointmentList() {
	}

	public void importTimeSlotList() {
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

	public List<Appointment> getAppointmentList() {
		if (appointmentList == null)
			return new ArrayList<Appointment>();
		else
			return appointmentList;
	}

	public List<Allocation> getAllocationList() {
		if (allocationList == null)
			return new ArrayList<Allocation>();
		else
			return allocationList;
	}

	public List<Branch> getBranchList() {
		if (branchList == null)
		return new ArrayList<Branch>();
		else
		return branchList;
	}

	public List<Service> getServiceList() {
		if (serviceList == null)
			return new ArrayList<Service>();
		else
			return serviceList;
	}
}
