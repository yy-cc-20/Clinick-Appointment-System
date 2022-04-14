package entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;

/*
 * This class read data from the database and create objects from it.
 * This class does not save the data to the database.
 * To get the latest data, use the getXXList() method to get the data in the database.
 */

// If implements IDataStore interface
// The static methods need to be written in IDataStore

public class DataList {
    // Do not know why need to use local variable instead of the data members for Statement and ResultSet object.
    // This is the only way can work.

    // List
    private static List<Patient> patientList;
    private static List<Appointment> appointmentList;
    private static List<Allocation> allocationList;
    private static List<Branch> branchList;
    private static List<Receptionist> receptionistList;
    private static List<Service> serviceList;
    private static List<Doctor> doctorList;

    public DataList(){}

    // SQL related
    private final Connection conn = DatabaseConnection.getConnection();

    public List<Doctor> getDoctorList() {
            doctorList = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM doctor ORDER BY id;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                Doctor doctor = new Doctor(id, name, password);
                doctorList.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctorList;
    }

    public List<Service> getServiceList() {
        serviceList = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM service ORDER BY id;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                int timeslotRequired = rs.getInt("timeSlotRequired");
                Service service = new Service(id, name, price, description, timeslotRequired);
                serviceList.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Receptionist> getReceptionistList() {
        receptionistList = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM receptionist ORDER BY id;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                Receptionist receptionist = new Receptionist(id, name, password);
                receptionistList.add(receptionist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receptionistList;
    }

    public List<Branch> getBranchList() {
        branchList = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM branch ORDER BY id;");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String telNo = rs.getString("telNo");
                Receptionist rep = getReceptionist(rs.getInt("receptionistId"));
                Branch branch = new Branch(id, name, address, rep, telNo);
                branchList.add(branch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branchList;
    }

    public List<Allocation> getAllocationList() {
        allocationList = new ArrayList<>();
        try {
            Statement st = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT doctorId, branchId, serviceId, id FROM allocation ORDER BY id;");
            while (rs.next()) {
                Service service = getService(rs.getInt("serviceId"));
                Doctor doctor = getDoctor(rs.getInt("doctorId"));
                Branch branch = getBranch(rs.getInt("branchId"));
                Allocation allocation = new Allocation(rs.getInt("id"), service, branch, doctor);
                allocationList.add(allocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allocationList;
    }

    public List<Appointment> getAppointmentList() {
        appointmentList = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM appointment ORDER BY id;");

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate date = rs.getDate("date").toLocalDate();
                Attendance attendance = attendanceStringToEnum(rs.getString("attendance"));
                int startSlot = rs.getInt("startSlot");
                int patientId = rs.getInt("patientId");
                Allocation allocation = getAllocation(rs.getInt("allocationId"));
                Appointment appointment = new Appointment(id, date, patientId, allocation, attendance, startSlot);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    public List<Patient> getPatientList() {
        patientList = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patient ORDER BY id;");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String ic = rs.getString("ic");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String password = rs.getString("password");
                List<Appointment> appointmentList = getAppointmentListByPatientId(id);
                Patient patient = new Patient(id, name, password, ic, phone, address, appointmentList);
                patientList.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientList;
    }

    /**
     * @return an empty object if id not found
     */
    // If you want to the latest data from the database, call getXXList() before calling these method
    public Doctor getDoctor(int id) {
        if (doctorList == null)
            getDoctorList(); // Initialize doctorList
        for (Doctor d : doctorList)
            if (d.getUserId() == id)
                return d;
        return new Doctor();
    }

    public Service getService(int id) {
        if (serviceList == null)
            getServiceList(); // Initialize serviceList
        for (Service s : serviceList)
            if (s.getServiceId() == id)
                return s;
        return new Service();
    }

    public Receptionist getReceptionist(int id) {
        if (receptionistList == null)
            getReceptionistList(); // Initialize receptionistList
        for (Receptionist r : receptionistList)
            if (r.getUserId() == id)
                return r;
        return new Receptionist();
    }

    public Branch getBranch(int id) {
        if (branchList == null)
            getBranchList(); // Initialize branchList
        for (Branch b : branchList)
            if (b.getBranchId() == id)
                return b;
        return new Branch();
    }

    public Allocation getAllocation(int id) {
        if (allocationList == null)
            getAllocationList(); // Initialize allocationList
        for (Allocation a : allocationList)
            if (a.getId() == id)
                return a;
        return new Allocation();
    }

    public Appointment getAppointment(int id) {
        if (appointmentList == null)
            getAppointmentList(); // Initialize appointmentList
        for (Appointment a : appointmentList)
            if (a.getAppointmentId() == id)
                return a;
        return new Appointment();
    }

    public Patient getPatient(int id) {
        if (patientList == null)
            getPatientList(); // Initialize patientL
        for (Patient p : patientList)
            if (p.getUserId() == id)
                return p;
        return new Patient();
    }

    public Attendance attendanceStringToEnum(String attendance) {
        if (attendance.equals("Attended")) {
            return Attendance.ATTENDED;
        } else if (attendance.equals("Absent")) {
            return Attendance.ABSENT;
        } else {
            return Attendance.NAN;
        }
    }

    public List<Appointment> getAppointmentListByPatientId(int id) {
        if (appointmentList == null)
            getAppointmentList(); // Initialize appointmentList
        List<Appointment> apptList = new ArrayList<>();
        for (Appointment a : appointmentList)
            if (a.getPatientId() == id)
                apptList.add(a);
        return apptList;
    }
	/*
	// DataList test
	// Before you start to do this class
	// Make sure the classes you use is working fine
	public static void main(String[] args) {
		List<Patient> patientList = DataList2.getPatientList();
		Patient patient = patientList.get(5);
		List<Appointment> appointmentList = patient.getAppointments();
		Appointment appointment = appointmentList.get(1);
		Allocation allocation = appointment.getAllocation();
		Service service = allocation.getService();
		Branch branch = allocation.getBranch();
		Doctor doctor = allocation.getDoctor();
		Receptionist receptionist = branch.getReceptionist();
		
		System.out.println(patient.getUserId()); // 6
		System.out.println(appointment.getAppointmentId()); // 21
		System.out.println(allocation.getLinkId()); // 2
		System.out.println(service.getServiceId()); // 2
		System.out.println(branch.getBranchId()); // 1
		System.out.println(doctor.getUserId()); // 2
		System.out.println(receptionist.getUserId()); // 1
	}*/
}
