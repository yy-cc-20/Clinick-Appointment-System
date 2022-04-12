package controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import boundary.ConsoleUI;
import database.DatabaseConnection;
import java.time.LocalDate;
import entity.*;

public class ViewSlotsController {
	private static ViewSlotsController instance;
	private Statement st;
	private ResultSet rs;
	private String sql;
	
	// The availability of doctors in charge at different time
	private List<List<Integer>> availableDoctorsId = new ArrayList<List<Integer>>(); // Have to write as new ArrayList<List<Integer>>()
	// Row: time slot number starting from 0
	// Column: the ids of the available doctors at that time		
	
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
		branchResults = DataList.getInstance().getBranchesById(branchIds);
		return branchResults;
	}
	
	/** @return the id of doctors who are available to provide a particular service at a particular branch for different times on a particular date */
	public List<List<Integer>> getAvailableDoctors(int serviceId, int branchId, LocalDate date, int requiredSlots) {
		List<Integer> doctorsInCharge;
		List<Integer> unavailableDoctorsId;
		
		doctorsInCharge = getDoctorsInCharge(serviceId, branchId);
		
		// Initializes the availableDoctorsId with all the doctors in charge regardless of their availabilities
		for (int i = 0; i < TimeSlot.values().length; ++i) {
			availableDoctorsId.add(doctorsInCharge);
		}
		
		// Remove the doctors in charge that have an appointment at a time slot from availableDoctorsId
		for (TimeSlot startSlot : TimeSlot.values()) {
			unavailableDoctorsId = getDoctorsHaveAppointment(serviceId, branchId, date, startSlot.ordinal());
			
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
	
	/** @param slot start from 0 */
	public List<Integer> getDoctorsHaveAppointment(int serviceId, int branchId, LocalDate date, int slot) {
		List<Integer> returnIds = new ArrayList<>();
		slot += 1; // In database it starts from 1
		
		sql = "SELECT doctorId FROM Appointment NATURAL JOIN Allocation"
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
	
	/** @param startSlotOrdinal start from 0 */
	private void removeUnavailableDoctors(int startSlotOrdinal, int requiredSlots, int unavailableDoctorId) {
		for (int i = 0; i < requiredSlots; ++i) {
			if (startSlotOrdinal + i < availableDoctorsId.size())
				availableDoctorsId.get(startSlotOrdinal + i).remove(Integer.valueOf(unavailableDoctorId));
		}
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
