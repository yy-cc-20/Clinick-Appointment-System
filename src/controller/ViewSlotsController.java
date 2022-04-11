package controller;

import java.util.ArrayList;

import database.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import entity.*;

public class ViewSlotsController {
	private static Connection conn = DatabaseConnection.getConnection();
	private static PreparedStatement ps;
	
	/** @return branches that provide a particular service */
	public static ArrayList<Branch> getBranchFilteredByService(int serviceId) {
		
	}
	
	/** @return doctors available to provide a particular service at a particular branch for different times on a particular date */
	public static Doctor[][] getAvailableDoctors(int serviceId, int branchId, LocalDate date) {
		
	}
}
