package entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.Date;

import java.util.ArrayList;
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

    private Connection con;
    private Statement st;
    private ResultSet rs;

    // Private constructor for singleton
    private DataList() {
        try {
            con = DatabaseConnection.getConnection();
            st = con.createStatement();
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

    public List<Appointment> getAppointmentList(String query, String column, String data) {
        try {
            appointmentList = new ArrayList<>();
            // e.g., query = "filter" column = "id" data = "1"
            // e.g., query = "sort" column = "id" data = "asc"
            if (query == null)
                rs = st.executeQuery("SELECT * FROM appointment;");
            else if (query.equalsIgnoreCase("filter"))
                rs = st.executeQuery("SELECT * FROM appointment WHERE " + column + " = " + data + ";");
            else if (query.equalsIgnoreCase("sort"))
                rs = st.executeQuery("SELECT * FROM appointment ORDER BY " + column + " " + data + ";");

            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                String attendance = rs.getString("attendance");
                int startSlot = rs.getInt("startSlot");
                int patientId = rs.getInt("patientId");
                int allocationId = rs.getInt("allocationId");
                Appointment appointment = new Appointment(id, date.toString(), patientId,
                        allocationId, attendance, startSlot);
                appointmentList.add(appointment);
            }
			System.out.println("appointmentList " + appointmentList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    // Return branch objects of the specified ids
    public List<Branch> getBranchesById(List<Integer> ids) {
        List<Branch> branchResults = new ArrayList<>();

        for (Integer id : ids)
            branchResults.add(getBranchList("filter", "id", id.toString()).get(0));

        return branchResults;
    }

    public List<Branch> getBranchList(String query, String column, String data) {
        try {
            branchList = new ArrayList<>();
            if (query == null)
                rs = st.executeQuery("SELECT branch.id, branch.name, branch.address, branch.telNo, branch.receptionistId FROM branch, receptionist WHERE branch.receptionistId = receptionist.Id;");
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
                Branch branch = new Branch(id, name, address, receptionistId, telNo);
                branchList.add(branch);
            }
			System.out.println("branchList " + branchList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branchList;
    }

    public List<Service> getServiceList() {
        try {
            serviceList = new ArrayList<>();
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
//			System.out.println("serviceList " + serviceList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Doctor> getDoctorList() {
        try {
            doctorList = new ArrayList<>();
            rs = st.executeQuery("SELECT * FROM doctor ORDER BY id;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                Doctor doctor = new Doctor(id, name, password);
                doctorList.add(doctor);
            }
//			System.out.println("doctorList " + doctorList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctorList;
    }

    public List<Allocation> getAllocationList() {
        try {
            allocationList = new ArrayList<>();
            rs = st.executeQuery("SELECT * FROM allocation ORDER BY id;");
            // rs.next returns false on second iteration
            while (rs.next()) {
                int id = rs.getInt("id");
                int branchId = rs.getInt("branchId");
                int serviceId = rs.getInt("serviceId");
                int doctorId = rs.getInt("doctorId");
                Allocation allocation = new Allocation(id, branchId, serviceId, doctorId);
                allocationList.add(allocation);
            }
            System.out.println("allocationList " + allocationList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allocationList;
    }

    public List<Patient> getPatientList(String query, String column, String data) {
        try {
            patientList = new ArrayList<>();
            rs = st.executeQuery("SELECT * FROM patient ORDER BY id;");
            if (query == null)
                rs = st.executeQuery("SELECT * FROM patient ORDER BY id;");
            else if (query.equalsIgnoreCase("filter"))
                rs = st.executeQuery("SELECT * FROM patient WHERE " + column + " = " + data + ";");
            else if (query.equalsIgnoreCase("sort"))
                rs = st.executeQuery("SELECT * FROM patient ORDER BY " + column + " " + data + ";");

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
//			System.out.println("patientList " + patientList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientList;
    }

    public List<Receptionist> getReceptionistList() {
        try {
            receptionistList = new ArrayList<>();
            rs = st.executeQuery("SELECT * FROM receptionist ORDER BY id;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                Receptionist receptionist = new Receptionist(id, name, password);
                receptionistList.add(receptionist);
            }
//			System.out.println("receptionistList " + receptionistList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receptionistList;
    }

    public void addAppointment(String date, String attendance, int startSlot, int patientId, int allocationId) {
        try {
            st.executeUpdate("INSERT IGNORE INTO appointment (date, attendance, startSlot, patientId, allocationId) " +
                    "VALUES ('" + date + "','" + attendance + "','" + startSlot + "','" + patientId + "','"
                    + allocationId + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // provide the patient's basic information to add into database
    public void addPatientPartial(String name, String ic, String password) {
        try {
            st.executeUpdate("INSERT IGNORE INTO patient (name, ic, password) " +
                    "VALUES ('" + name + "','" + ic + "','" + password + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // provide the patient's full information to add into database
    public void addPatientFull(String name, String ic, String phone, String address, String password) {
        try {
            st.executeUpdate("INSERT IGNORE INTO patient (name, ic, phone, address, password) " +
                    "VALUES ('" + name + "','" + ic + "','" + phone + "','" + address + "','" + password + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // provide the appointmentId to update, with the new date and startSlot
    public void updateAppointmentTime(int id, String date, int startSlot) {
        String query = ( "UPDATE appointment SET date=?, startSlot=?, WHERE id=?" );
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(3, id);
            pstmt.setDate(1, new Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime()));
            pstmt.setInt(2, startSlot);
            pstmt.executeUpdate();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    // provide the appointmentId to update, with the updated attendance record
    public void updateAppointmentAttendance(int id, String attendance) {
        String query = ( "UPDATE appointment SET attendance=? WHERE id=?" );
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(2, id);
            pstmt.setString(1, attendance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // provide the appointmentId to update to cancel the appointment
    public void cancelAppointment(int id) {
        // TODO : cancel the appointment
        String query = ( "DELETE FROM appointment WHERE id=?" );
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
