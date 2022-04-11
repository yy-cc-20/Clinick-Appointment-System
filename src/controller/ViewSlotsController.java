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
			branchIds = resultSetToIntArr(rs);
			branchResults = getBranchesById(branchIds);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (branchResults == null)
			return new ArrayList<Branch>();
		return branchResults;
	}
	
	/** @return doctors available to provide a particular service at a particular branch for different times on a particular date */
	public static Doctor[][] getAvailableDoctors(int serviceId, int branchId, LocalDate date) {
		Doctor[][] availableDoctors = new Doctor[TimeSlot.values().length][];
		
		return availableDoctors;
	}
	
	// Return branch objects of the specified ids
	public static List<Branch> getBranchesById(List<Integer> ids) {
		List<Branch> branches = new ArrayList<>();
		
		return branches;
	}
	
	// Retrieve the integer from the ResultSet and return ArrayList<Integer>
	public static List<Integer> resultSetToIntArr(ResultSet rs) throws SQLException {
		List<Integer> ints = new ArrayList<>();
		while (rs.next()) {
			ints.add(rs.getInt(0));
		}
		return ints;
	}
}
