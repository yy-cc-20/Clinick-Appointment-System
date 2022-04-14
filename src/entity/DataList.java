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

public class DataList {

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
//            instance = new DataList();
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




    public void addAppointment(String date, String attendance, int startSlot, int patientId, int allocationId) {
        try {
            st.executeUpdate("INSERT IGNORE INTO appointment (date, attendance, startSlot, patientId, allocationId) " +
                    "VALUES ('" + date + "','" + attendance + "','" + startSlot + "','" + patientId + "','"
                    + allocationId + "')");
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

    public void updatePatient (String phoneNo, String address, int patientId){
        try {
            st.executeUpdate("UPDATE Patient SET phone='" + phoneNo + "', address='" + address + "' WHERE id='" + patientId + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // provide the appointmentId to update, with the new date and startSlot
    public void updateAppointmentTime(int id, String date, int startSlot) {
        String query = ( "UPDATE appointment SET date=?, startSlot=? WHERE id=?" );
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
        String query = ( "DELETE FROM appointment WHERE id=?" );
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
