package entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;

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
    private List<Service> serviceList;

    private Statement st;

    private DataList() {
        try {
            st = DatabaseConnection.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // Private constructor for singleton

    public static IDataStore getInstance() {
        if (instance == null) {
            instance = new DataList();
        }
        return instance;
    }

    public List<Doctor> getDoctorList() { // TODO retrieve from database
        // Test data for login
        doctorList = new ArrayList<>();
        doctorList.add(new Doctor(1, "username", "password"));

        //if (doctorList == null)
        //	return new ArrayList<Doctor>();
        //else
        return doctorList;
    }

    public List<Branch> getBranchList() { // TODO retrieve from database
        if (branchList == null)
            return new ArrayList<Branch>();
        else
            return branchList;
    }

    public List<Service> getServiceList() { // TODO retrieve from database
        if (serviceList == null)
            return new ArrayList<Service>();
        else
            return serviceList;
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
	
/*
 * 	public void importDoctorList() {

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
	
	public List<String> getResultArray(ResultSet rs, ResultSet size) throws SQLException {
		List<String> rsArray = new ArrayList<>();
		try {
			while (rs.next()) {
				String data = "";
				for (int i = 1; i < size.getInt(0); i++) {
					data += rs.getString(i) + " ";
				}
				rsArray.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rsArray;
	}

	public void fetchAllSets(String tableName) throws SQLException {
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM " + tableName + ";");
			ResultSet size = st.executeQuery("SELECT COUNT(*) FROM " + tableName + ";");
			getResultArray(rs, size);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void fetchFilteredSets(String tableName, String columnToFilter, String filter) throws SQLException {
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM " + tableName + " WHERE " + columnToFilter + " = " + filter + ";");
			ResultSet size = st.executeQuery("SELECT COUNT(*) FROM " + tableName + " WHERE " + columnToFilter + " = " + filter + ";");
			getResultArray(rs, size);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void fetchSortedSets(String tableName, String columnToSort, String order) throws SQLException {
		try {
			// order can be asc or desc
			ResultSet rs = st.executeQuery("SELECT * FROM " + tableName + " ORDER BY " + columnToSort + " " + order + ";");
			ResultSet size = st.executeQuery("SELECT COUNT(*) FROM " + tableName + " ORDER BY " + columnToSort + ";");
			getResultArray(rs, size);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateToTable(String tableName, String columnToUpdate, String columnToSearch, String valueToSearch,	String newValue) throws SQLException {
		try {
			st.executeUpdate("UPDATE " + tableName + " SET " + columnToUpdate + " = " + newValue + " WHERE " + columnToSearch + " = " + valueToSearch + ";");
			// PreparedStatement pst = conn.prepareStatement("INSERT INTO Appointment (id, date, service) VALUES (?, ?, ?)");
			// pst.setInt(1, 50);
			// pst.setString(2, "date");
			// pst.setString(2, "service");
			// pst.executeUpdate(); // no args
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteFromTable(String tableName, String columnToSearch, String valueToSearch) throws SQLException {
		try {
			st.executeUpdate("DELETE FROM " + tableName + " WHERE " + columnToSearch + " = " + valueToSearch + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
*/
}
