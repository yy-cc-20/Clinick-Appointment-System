package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.DatabaseConnection;

/*
 * IDataStore dataList = DataList.getInstance(); // Already retrieved the data
 */

public class DataList implements IDataStore {

	private static IDataStore instance;
	private List<Doctor> doctorList;
	private List<Patient> patientList;
	private List<Receptionist> receptionistList;
	private List<Appointment> appointmentList;
	private List<Allocation> allocationList;
	private List<Branch> branchList;
	private List<Service> serviceList;

	private Statement st;
	private ResultSet rs;

	// Private constructor for singleton
	private DataList() {
		try {
			st = DatabaseConnection.getConnection().createStatement();
			doctorList = new ArrayList<>();
			patientList = new ArrayList<>();
			receptionistList = new ArrayList<>();
			appointmentList = new ArrayList<>();
			allocationList = new ArrayList<>();
			branchList = new ArrayList<>();
			serviceList = new ArrayList<>();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static IDataStore getInstance() {
		if (instance == null) {
			instance = new DataList();
		}
		return instance;
	}

	// Return branch objects of the specified ids
	public List<Branch> getBranchesById(List<Integer> ids) {
		List<Branch> branchResults = new ArrayList<>();

		for (Integer id : ids)
			branchResults.add(getBranchList("filter", "id", id.toString()).get(0));

		return branchResults;

		// return getBranchList(null, "", "");
	}

	public List<Branch> getBranchList(String query, String column, String data) {
		try {
			// e.g., query = "filter" column = "id" data = "1"
			// e.g., query = "sort" column = "id" data = "asc"
			if (query == null)
				rs = st.executeQuery("SELECT * FROM branch ORDER BY id;");
			else if (query.equalsIgnoreCase("filter"))
				rs = st.executeQuery("SELECT * FROM branch WHERE " + column + " = " + data + ";");
			else if (query.equalsIgnoreCase("sort"))
				rs = st.executeQuery("SELECT * FROM branch ORDER BY " + column + " " + data + ";");

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String telNo = rs.getString("telNo");
				int receptionistId = rs.getInt("receptionistId");
				Branch branch = new Branch(id, name, address, Integer.toString(receptionistId), telNo);
				branchList.add(branch);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return branchList;
	}

	public List<Service> getServiceList() {
		try {
			rs = st.executeQuery("SELECT * FROM service ORDER BY id;");
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

	public List<Doctor> getDoctorList() {
		try {
			rs = st.executeQuery("SELECT * FROM doctor ORDER BY id;");
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

	public List<Allocation> getAllocationList() {
		try {
			rs = st.executeQuery("SELECT * FROM allocation ORDER BY id;");
			while (rs.next()) {
				int id = rs.getInt("id");
				int branchId = rs.getInt("branchId");
				int serviceId = rs.getInt("serviceId");
				int doctorId = rs.getInt("doctorId");
				Allocation allocation = new Allocation(id, branchId, serviceId, doctorId);
				allocationList.add(allocation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allocationList;
	}

	public List<Patient> getPatientList() {
		try {
			rs = st.executeQuery("SELECT * FROM patient ORDER BY id;");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String ic = rs.getString("ic");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				String password = rs.getString("password");
				Patient patient = new Patient(id, name, password, ic, phone, address);
				patientList.add(patient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patientList;
	}

	public List<Receptionist> getReceptionistList() {
		try {
			rs = st.executeQuery("SELECT * FROM receptionist ORDER BY id;");
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

	public List<Appointment> getAppointmentList() {
		try {
			rs = st.executeQuery("SELECT * FROM appointment ORDER BY id;");
			while (rs.next()) {
				int id = rs.getInt("id");
				Date date = rs.getDate("date");
				String attendance = rs.getString("attendance");
				int startSlot = rs.getInt("startSlot");
				int patientId = rs.getInt("patientId");
				int allocationId = rs.getInt("allocationId");
				Appointment appointment = new Appointment(id, date.toString(), Integer.toString(patientId),
						allocationId, attendance, startSlot);
				appointmentList.add(appointment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return appointmentList;
	}

}
