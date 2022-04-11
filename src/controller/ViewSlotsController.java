package controller;

import java.util.ArrayList;
import java.util.List;

import boundary.ConsoleUI;
import database.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import entity.*;

public class ViewSlotsController {
	private static ViewSlotsController instance;
	private Statement st;
	private ResultSet rs;
	private String sql;
	
	// the availability of doctors in charge at different time
	private List<List<Integer>> availableDoctorsId = new ArrayList<List<Integer>>(); // Have to write as new ArrayList<List<Integer>>()
	// row: time slot number
	// column: the id of the available doctors at that time		
	
	private ViewSlotsController() {
		try {
			st = DatabaseConnection.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ViewSlotsController getInstance() {
		if (instance == null)
			new ViewSlotsController();
		return instance;
	}
	
	/** @return branches that provide a particular service */
	public List<Branch> getBranchFilteredByService(int serviceId) {
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
	
	/** @return the id of doctors who are available to provide a particular service at a particular branch for different times on a particular date */
	public List<List<Integer>> getAvailableDoctors(int serviceId, int branchId, LocalDate date, int requiredSlots) {
		
		
		
		// 1. Finds doctors that in charge of the selected service at the selected branch
		List<Integer> doctorsInCharge = getDoctorsInCharge(serviceId, branchId);
		
		// 2. Initializes the availableDoctorsId with all the doctors in charge regardless of their availabilities
		for (int i = 0; i < TimeSlot.values().length; ++i) {
			availableDoctorsId.add(doctorsInCharge);
		}
		
		// 3. Remove the doctors in charge that have an appointment during a time slot from that slot of the availableDoctorsId
		List<Integer> unavailableDoctorsId;
		
		for (TimeSlot startSlot : TimeSlot.values()) {
			unavailableDoctorsId = getDoctorsHaveAppointment(serviceId, branchId, date, startSlot.ordinal() + 1);
			
			for (int id : unavailableDoctorsId)
				removeUnavailableDoctors(startSlot.ordinal(), requiredSlots, id);
		}
		
		return availableDoctorsId;
		
		/* If every service only consumes 1 time slot
		The doctors available during a time slot will be:
		
		SELECT DISTINCT doctorId FROM Allocation WHERE serviceId = aServiceId AND branchId = aBranchId
		minus
		SELECT doctorId FROM FROM Appointment NATURAL JOIN Allocation 
			WHERE serviceId = aServiceId 
				AND branchId = aBranchId 
				AND date = date 
				AND startSlot = (slot.ordinal() + 1)
				
		*/
	}

	public List<Integer> getDoctorsInCharge(int serviceId, int branchId) {
		sql = "SELECT doctorId FROM Allocation WHERE serviceId = " + serviceId  + " AND branchId = " + branchId; 
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSetToIntArr(rs);
	}
	
	/** @param slot start from 1 */
	public List<Integer> getDoctorsHaveAppointment(int serviceId, int branchId, LocalDate date, int slot) {
		List<Integer> returnIds = new ArrayList<>();
		
		sql = "SELECT doctorId FROM FROM Appointment NATURAL JOIN Allocation"
				+ " WHERE serviceId = " + serviceId 
				+ " AND branchId = " + branchId 
				+ " AND date = " + date.format(ConsoleUI.DATE_SQL_FORMATTER) 
				+ " AND startSlot = " + slot;
		try {
			rs = st.executeQuery(sql);
			while (rs.next())
				returnIds.add(rs.getInt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnIds;
	}
	
	/** @param slot start from 0 */
	private void removeUnavailableDoctors(int startSlotOrdinal, int requiredSlots, int unavailableDoctorId) {
		for (int i = 0; i < requiredSlots; ++i) {
			if (startSlotOrdinal + i < availableDoctorsId.size())
				availableDoctorsId.get(startSlotOrdinal + i).remove(unavailableDoctorId);
		}
	}
	
	// Return branch objects of the specified ids
	public List<Branch> getBranchesById(List<Integer> ids) { // TODO called by ViewSlotsController
		List<Branch> branches = new ArrayList<>();
		
		return branches;
	}
	
	// Retrieve the integer from the ResultSet and return List<Integer>
	public List<Integer> resultSetToIntArr(ResultSet rs) {
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
