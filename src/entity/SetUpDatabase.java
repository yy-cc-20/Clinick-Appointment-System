package entity;

import java.sql.*;

/*
 * creates tables if not exist
 * insert the data into the tables
 */

public class SetUpDatabase {
	private static Statement st;
	
	public static void setUpDatabaseIfNotExist() throws SQLException {
		st = DatabaseConnection.getConnection().createStatement();
		
		// Recreate the table in the database in case the table structure has changed 
		st.executeUpdate("DROP TABLE IF EXISTS `doctor`;"); 
		// TODO 1. Drop other table here
		
		// Create table
		st.executeUpdate("CREATE TABLE IF NOT EXISTS `clinick-appointment-system`.`doctor` (`userid` INT NOT NULL AUTO_INCREMENT, " +
				"`username` VARCHAR(45) NOT NULL, `password` VARCHAR(45) NOT NULL, PRIMARY KEY (`userid`));");
		// TODO 2. Continue create other table
		
		// Insert data into the table
		insertDoctorTable();
		// TODO 3. Continue insert other table here
		
		//conn.commit(); // Cannot call commit when autocommit is true
	}
	
	private static void insertDoctorTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		
		// Good practice for autocommit statement
		conn.setAutoCommit(false); // So that multiple SQL statements can all run inside the same transaction
		st.addBatch("INSERT IGNORE INTO doctor (username, password) VALUES ('username', 'password');"); // "IGNORE" == insert into if not exists
		st.addBatch("INSERT IGNORE INTO doctor (username, password) VALUES ('testing', 'password');");
		st.executeBatch();
		// Should anything go wrong with any of the insert statements, the whole transaction
		// would be rolled back, so will not have inconsistent data in the database
		conn.commit();           
		conn.setAutoCommit(true); // Set back to default, so other part of the code will not be affected
	}
}
