package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import database.DatabaseConnection;

public class ManageAppointmentController {
	private static Connection conn = DatabaseConnection.getConnection();
	
    // after viewing the available slots, wants to update the date and startSlot => allocationId changes 
    // provide the appointmentId to update, with the new date and startSlot
    public void updateAppointmentTime(int id, String date, int startSlot) {
        String query = ( "UPDATE appointment SET date=?, startSlot=?, WHERE id=?" );
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
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
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
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
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
