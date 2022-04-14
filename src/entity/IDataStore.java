package entity;

import java.util.List;

public interface IDataStore {
    List<Doctor> getDoctorList();

    List<Service> getServiceList();

    List<Receptionist> getReceptionistList();

    List<Branch> getBranchList();

    List<Allocation> getAllocationList();

    List<Appointment> getAppointmentList();

    List<Patient> getPatientList();

    Doctor getDoctor(int id);

    Service getService(int id);

    Receptionist getReceptionist(int id);

    Branch getBranch(int id);

    Allocation getAllocation(int id);

    Appointment getAppointment(int id);

    Patient getPatient(int id);

    Attendance attendanceStringToEnum(String attendance);

    List<Appointment> getAppointmentListByPatientId(int id);
}
