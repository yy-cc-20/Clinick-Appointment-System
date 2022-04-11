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
	public static int[][] getAvailableDoctors(int serviceId, int branchId, LocalDate date) {
/*		List<Integer> doctorIds; // The id of doctors who work at the particular branch and provide the particular service
		List<Doctor> doctors; // The doctors who work at the particular branch and provide the particular service
		List<Appointment> appointments = getAppointmentsByServiceBranchDate(serviceId, branchId, date);
		
		1. Finds the required number of time slots for the selected service
		SELECT timeSlotRequired FROM Service WHERE serviceId = serviceId
		
		2. Finds doctors that in charge of the selected service at the selected branch
		SELECT allocationId FROM Allocation WHERE serviceId = serviceId AND branchId = branchId (from the alocationId can know the doctor)
		SELECT doctorId FROM Allocation WHERE serviceId = serviceId AND branchId = branchId 
				
		List<Integer>doctorsInCharge = result;
		int[] doctorsInChargeArr = new int[doctorsInCharge.size()];
		doctorsInChargeArr = doctorsInCharge.toArray(doctorsInChargeArr);
		
		3. Initializes the availableDoctors[][] with all the doctors incharge
		int[][] availableDoctorIds = new int[TimeSlot.values().length][];
		for (int[] time : availableDoctors)
			time = doctorInchargeArr.clone();
		
		index: time slot number
		row: the id of the available doctors at that time
		
		4. remove the doctors in charge that have an appointment during a time slot from that slot of the availableDoctors[][]
		
		4.1 find the appointments for doctors in charge on the selected date
		for(int doctorId : doctorsInChargeArr)
			SELECT startSlot FROM Appointment NATURAL JOIN Allocation WHERE doctorId = doctorId AND date = date
		*/	
		int requiredSlots;
		
		// the availability of doctors in charge at different time
		List<List<Integer>> availableDoctorsId = new ArrayList<List<Integer>>(); // Have to write as new ArrayList<List<Integer>>()
		// row: time slot number
		// column: the id of the available doctors at that time
		
		// 1. Finds the required number of time slots for the selected service
		sql = "SELECT timeSlotRequired FROM Service WHERE serviceId = serviceId";
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		requiredSlots = rs.getInt(0);
		
		
		// 2. Finds doctors that in charge of the selected service at the selected branch
		sql = "SELECT doctorId FROM Allocation WHERE serviceId = serviceId AND branchId = branchId"; 
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		// 3. Initializes the availableDoctorsId with all the doctors in charge regardless of their availabilities
		List<Integer>doctorsInCharge = resultSetToIntArr(rs);
		for (int i = 0; i < TimeSlot.values().length; ++i) {
			availableDoctorsId.add(doctorsInCharge);
		}
		
		
		// 4. Remove the doctors in charge that have an appointment during a time slot from that slot of the availableDoctorsId
		for (TimeSlot startSlot : TimeSlot.values()) {
			sql = "SELECT doctorId FROM FROM Appointment NATURAL JOIN Allocation"
					+ " WHERE serviceId = " + serviceId 
					+ " AND branchId = " + branchId 
					+ " AND date = " + date.format(ConsoleUI.DATE_SQL_FORMATTER) 
					+ " AND startSlot = " + (startSlot.ordinal() + 1);
			try {
				rs = st.executeQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			removeFromAvailable(startSlot, numberOfRequiredSlots, doctorId);
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

	// Return branch objects of the specified ids
	public static List<Branch> getBranchesById(List<Integer> ids) { // TODO called by ViewSlotsController
		List<Branch> branches = new ArrayList<>();
		
		return branches;
	}
	
	// Retrieve the integer from the ResultSet and return List<Integer>
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
