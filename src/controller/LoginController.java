package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseConnection;
import entity.DataList;
import entity.IDataStore;
import entity.User;

public class LoginController {
	private IDataStore dataList = DataList.getInstance();
		
	// security: lock the system after a few failed logins
	int failedLoginAttempt = 0; 
	public static final int MAX_FAILED_LOGIN_ATTEMPT = 3;
	public static final int LOCK_TIME_LENGTH = 10; // in seconds
	
			
	/**
	 * @param role 1 for receptionist, 2 for doctor, 3 for patient
	 * @return username, return empty string if login fail
	 */
	public String loginSuccessfully(int role, int userid, String password) {
		String table;
		User user = new User(userid, "", password);
		
		switch (role) {
			case 1 -> table = "Receptionist";
			case 2 -> table = "Doctor";
			case 3 -> table = "Patient";
			default -> throw new IllegalArgumentException();
		}
		String sql = "SELECT username FROM " + table + " WHERE userid = " + userid + " AND password = " + password;
		
		try {
			Statement st = DatabaseConnection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) // Found user
				return rs.getString(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		failedLoginAttempt++;
		return "";
	}
	
	public int getFailedLoginAttempt() {
		return failedLoginAttempt;
	}
	
	// use this if login successfully or lock time expired
	public void resetFailedLoginAttempts() {
		failedLoginAttempt = 0;
	}
	
	/*
	public String loginSuccessfully(int role, int userid, String password) {
		User user = new User(userid, "", password);
		
		switch (role) {
			case 1: 
					for (int i = 0; i < dataList.getReceptionistList().size(); ++i) {
						if (user.equals(dataList.getReceptionistList().get(i))) {
							resetFailedLoginAttempts();
							return dataList.getReceptionistList().get(i).getUsername();
						}
					}
					break;
			case 2: 
					for (int i = 0; i < dataList.getDoctorList().size(); ++i) {
						if (user.equals(dataList.getDoctorList().get(i))) {
							resetFailedLoginAttempts();
							return dataList.getDoctorList().get(i).getUsername();
						}
					}
					break;
			default: // case 3
					for (int i = 0; i < dataList.getPatientList().size(); ++i) {
						if (user.equals(dataList.getPatientList().get(i))) {
							resetFailedLoginAttempts();
							return dataList.getPatientList().get(i).getUsername();
						}
					}
		}
		
		failedLoginAttempt++;
		return "";
	}
	*/
}
