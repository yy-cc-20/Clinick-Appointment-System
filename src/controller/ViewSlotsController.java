package controller;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import entity.*;

public class ViewSlotsController {
	private static ViewSlotsController instance;
	private static Statement st;
	private static ResultSet rs;
	private static String sql;
	
	private ViewSlotsController() {
		try {
			st = DatabaseConnection.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ViewSlotsController getInstance() {
		if (instance == null)
			new ViewSlotsController();
		return instance;
	}
	
	/** @return branches that provide a particular service */
	public static List<Branch> getBranchFilteredByService(int serviceId) {
		List<Integer> branchIds;
		List<Branch> branchResults;
		
		sql = "SELECT DISTINCT branchId FROM Allocation WHERE serviceId = " + serviceId;
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		branchIds = resultSetToIntArr(rs);
		branchResults = getBranchesById(branchIds);
		return branchResults;
	}
	
	/** @return doctors available to provide a particular service at a particular branch for different times on a particular date */
	public static Doctor[][] getAvailableDoctors(int serviceId, int branchId, LocalDate date) {
		List<Integer> doctorIds; // The id of doctors who work at the particular branch and provide the particular service
		List<Doctor> doctors; // The doctors who work at the particular branch and provide the particular service
		List<Appointment> appointments = getAppointmentsByServiceBranchDate(serviceId, branchId, date);
		
		Doctor[][] availableDoctors = new Doctor[TimeSlot.values().length][]; // The number of doctors available at different time of a particular day
		// index: the time slot no
		// value: the slots available for that time
		
		sql = "SELECT DISTINCT doctorId FROM Allocation WHERE serviceId = " + serviceId + " AND branchId = " + branchId;
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		doctorIds = resultSetToIntArr(rs);
		doctors = getDoctorsById(doctorIds);
		
		
		return availableDoctors;
	}
	
	public static List<Appointment> getAppointmentsByServiceBranchDate(int serviceId, int branchId, LocalDate date) {
		List<Appointment> appointments;
		
		return appointments;
	}
	
	// Return doctor objects of the specified ids
	public static List<Doctor> getDoctorsById(List<Integer> ids) { // TODO called by ViewSlotsController
		List<Doctor> doctors = new ArrayList<>();
		
		return doctors;
	}
	
	// Return branch objects of the specified ids
	public static List<Branch> getBranchesById(List<Integer> ids) { // TODO called by ViewSlotsController
		List<Branch> branches = new ArrayList<>();
		
		return branches;
	}
	
	// Retrieve the integer from the ResultSet and return ArrayList<Integer>
	public static List<Integer> resultSetToIntArr(ResultSet rs) {
		List<Integer> ints = new ArrayList<>();
		try {
			while (rs.next()) {
				ints.add(rs.getInt(0));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ints;
	}
}
