package entity;

import java.util.List;

public interface IDataStore {
    // Use List interface instead of ArrayList, more flexible, reduce the dependency
    static List<Doctor> getDoctorList() {
		return null;}

    List<Patient> getPatientList();

    List<Receptionist> getReceptionistList();
    
    List<Appointment> getAppointmentList();
    
    List<Branch> getBranchList();
    
    List<Allocation> getAllocationList();
    
    List<Service> getServiceList();

    //List<Appointment> getAppointmentList(String query, String column, String data);

    //List<Branch> getBranchList(String query, String column, String data);

    //void addAppointment(String date, String attendance, int startSlot, int patientId, int allocationId);

    //void addPatientPartial(String name, String ic, String password);

    //void addPatientFull(String name, String ic, String phone, String address, String password);

    //void updateAppointmentTime(int appointmentId, String newDate, int newStartSlot);

<<<<<<< HEAD
    //void updateAppointmentAttendance(int appointmentId, String updatedAttendance);

    //void cancelAppointment(int appointmentId);

	Service createServiceObject(int id);
=======
    void addPatientFull(String name, String ic, String phone, String address, String password);

    void updatePatient(String phone, String address, int patientId);

    void updateAppointmentTime(int appointmentId, String newDate, int newStartSlot);
>>>>>>> 7e97ca07d6a18380e990e62afe8ee0d0233d2ad0

	Branch createBranchObject(int id);

	Receptionist createReceptionistObject(int id);
	
	Doctor createDoctorObject(int id);
	
	Patient createPatientObject(int id);
	
	Appointment createAppointmentObject(int id);
	
	Allocation createAllocationObject(int id);
}
