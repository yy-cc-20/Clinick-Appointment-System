package controller;

import java.util.ArrayList;

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
	
	/** @return branches that provide a particular service 
	 * @throws SQLException */
	public ArrayList<Branch> getBranchFilteredByService(int serviceId) throws SQLException {
		
		sql = "SELECT DISTINCT branchId FROM Allocation WHERE serviceId = " + serviceId;
		rs = st.executeQuery(sql);
		
		ArrayList<Integer> branchIds = resultSetToIntArr(rs);
		ArrayList<Branch> branchResults = getBranchesById(branchIds);
		
		return branchResults;
	}
	
	/** @return doctors available to provide a particular service at a particular branch for different times on a particular date */
	public Doctor[][] getAvailableDoctors(int serviceId, int branchId, LocalDate date) {
		
	}
	
	// Return branch objects of the specified ids
	public static ArrayList<Branch> getBranchesById(ArrayList<Integer> ids) {
		ArrayList<Branch> branches = new ArrayList<>();
	}
	
	// Retrieve the integer from the ResultSet and return ArrayList<Integer>
	public static ArrayList<Integer> resultSetToIntArr(ResultSet rs) throws SQLException {
		ArrayList<Integer> ints = new ArrayList<>();
		while (rs.next()) {
			ints.add(rs.getInt(0));
		}
		return ints;
	}
}
