package controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

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
			instance = new ViewSlotsController();
		return instance;
	}
	
	/** @return branches that provide a particular service */ 
	public List<Branch> getBranchFilteredByService(int serviceId) {
		
		List<Integer> branchIds;
		List<Branch> branchResults = new ArrayList<Branch>(); // Avoid return null
				
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
		
		// 1.
		List<Integer> doctorsInCharge = getDoctorsInCharge(serviceId, branchId);
		
		// 2. Initializes the availableDoctorsId with all the doctors in charge regardless of their availabilities
		int[] doctorsInChargeArr = new int[doctorsInCharge.size()];
		doctorsInChargeArr = doctorsInCharge.stream().mapToInt(Integer::intValue).toArray().clone();
		//System.out.println(Arrays.toString(doctorsInChargeArr));
		
		int availableDoctors[][] = new int[TimeSlot.values().length][];
		for (int i = 0; i < availableDoctors.length; ++i) {
			availableDoctors[i] = new int[doctorsInCharge.size()];
			availableDoctors[i] = doctorsInChargeArr.clone();
		}

		//System.out.println("initial: " + Arrays.deepToString(availableDoctors));
		
		// 3. Remove the doctors in charge that have an appointment at a time slot from availableDoctorsId
		List<Integer> unavailableDoctorsId;
		for (TimeSlot startSlot : TimeSlot.values()) {
			unavailableDoctorsId = getDoctorsHaveAppointment(serviceId, branchId, date, startSlot.ordinal() + 1);
			
			for (int id : unavailableDoctorsId)
				for (int i = 0; i < requiredSlots; ++i) // The service might consume more than 1 slots, remove the doctor from the following slots also
					if (startSlot.ordinal() + i < availableDoctors.length)
						for (int j = 0; j < availableDoctors[0].length; ++j) // Find the unavailable doctor and remove it
							if (availableDoctors[startSlot.ordinal() + i][j] == id)
								availableDoctors[startSlot.ordinal() + i][j] = 0;
		}
		//System.out.println("after remove " + Arrays.deepToString(availableDoctors));
		
		// 4. Convert availableDoctor from array to arrayList
		availableDoctorsId = new ArrayList<List<Integer>>();
		List<Integer> temp;
		for (int[] aTime : availableDoctors) {
			temp = new ArrayList<Integer>();
			for (int doctorId : aTime)
				if (doctorId > 0)
					temp.add(doctorId);
			availableDoctorsId.add(temp);
			//System.out.println(Arrays.deepToString(temp.toArray()));
		}
		
		return availableDoctorsId;
		
		/* Using ArrayList to do the same thing 
		 * removeUnavailableDoctors() has a bug so did not use arraylist
		 * 
		// Initializes the availableDoctorsId with all the doctors in charge regardless of their availabilities
		for (int i = 0; i < TimeSlot.values().length; ++i) {
			availableDoctorsId.add(doctorsInCharge);
		}
		
		// Remove the doctors in charge that have an appointment at a time slot from availableDoctorsId
		for (TimeSlot startSlot : TimeSlot.values()) {
			unavailableDoctorsId = getDoctorsHaveAppointment(serviceId, branchId, date, startSlot.ordinal() + 1);
			
			for (int id : unavailableDoctorsId)
				removeUnavailableDoctors(startSlot.ordinal(), requiredSlots, id);
		}
		*/
		
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
		List<Integer> returnIds = new ArrayList<>();
		sql = "SELECT doctorId FROM Allocation WHERE serviceId = " + serviceId  + " AND branchId = " + branchId; 
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				returnIds.add(rs.getInt("doctorId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("ViewSlotsController.getDoctorsInCharge testing");
		return returnIds;
	}
	
	/** @param slot start from 1 */
	public List<Integer> getDoctorsHaveAppointment(int serviceId, int branchId, LocalDate date, int startSlot) {
		List<Integer> returnIds = new ArrayList<>();
		
		sql = "SELECT doctorId FROM Allocation INNER JOIN Appointment ON Allocation.id = Appointment.allocationId WHERE serviceId = ? AND branchId = ? AND date = ? AND startSlot = ?";
		try {
			PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql);
			pstmt.setInt(1, serviceId);
			pstmt.setInt(2, branchId);
			pstmt.setDate(3, java.sql.Date.valueOf(date)); // The only way to compare SQL date
			pstmt.setInt(4, startSlot);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				returnIds.add(rs.getInt("doctorId")); // The column index of MySQL starts from 1
				//System.out.println("unavailable doctorid: " + rs.getInt("doctorId") + " startSlot" + startSlot);
			}
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
		/* Problem: remove an element in a list, will result in all the element in the 2D list being removed
		 * 
		 * The code below shows this problem:
		 * 
		List<List<String>> someNo = new ArrayList<List<String>>();
		List<String> someNo2 = new ArrayList<>();
		someNo2.add("2");
		someNo2.add("3");
		someNo.add(someNo2);
		someNo.add(someNo2);
		someNo.add(someNo2);
		someNo.add(someNo2);
		someNo.add(someNo2);
		someNo.add(someNo2);
		
		System.out.println("Before: " + Arrays.deepToString(someNo.toArray()));
		System.out.println("First list: " + Arrays.deepToString(someNo.get(0).toArray()));
		someNo.get(0).remove(1);
		System.out.println("Remove second element from the first list: " + Arrays.deepToString(someNo.get(0).toArray()));
		System.out.println("After: " + Arrays.deepToString(someNo.toArray()));
		*/
	}

	// Retrieve the integer from the ResultSet and return List<Integer>
	public List<Integer> resultSetToIntArr(ResultSet rs) {
		List<Integer> ints = new ArrayList<>();
		try {
			while (rs.next()) {
				ints.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ints;
	}
	/*
	// ViewSlotsController test
	public static void main(String[] args) throws SQLException {

		// getDoctorInCharge test
		List<Integer> ids0 = ViewSlotsController.getInstance().getDoctorsInCharge(2, 1);
		System.out.println(Arrays.deepToString(ids0.toArray())); // Correct output is 2, 3
		System.out.println();
		
		// getDoctorsHaveAppointment test
		List<Integer> ids = ViewSlotsController.getInstance().getDoctorsHaveAppointment(2, 1, LocalDate.of(2022, 4, 25), 1);
		System.out.println(Arrays.deepToString(ids.toArray())); // Correct output is 2, 3
		System.out.println();
				
		// getAvailableDoctors test
		List<List<Integer>> ids2 = ViewSlotsController.getInstance().getAvailableDoctors(2, 1, LocalDate.of(2022, 4, 25), 3);
		System.out.println(Arrays.deepToString(ids2.toArray()));
	}*/
}
