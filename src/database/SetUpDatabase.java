package database;

import java.sql.*;

/*
 * creates tables if not exist
 * insert the data into the tables
 */

public class SetUpDatabase {
	private static Statement st;
	private 
	
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
	
	public void setUpDatabase() throws SQLException {
		try {
			st.executeUpdate("CREATE TABLE Service (serviceId VARCHAR(25), serviceName VARCHAR(25), price double, description VARCHAR(250), timeSlotRequired int, PRIMARY KEY (serviceId))");
			//st.executeUpdate("CREATE TABLE Branch (branchId VARCHAR(25), branchName VARCHAR(25), branchAddress VARCHAR(250), receptionistId VARCHAR(25), telNo VARCHAR(25), PRIMARY KEY (branchId), FOREIGN KEY (receptionistId) REFERENCES Receptionist(id))");

			st.executeUpdate("INSERT INTO Service VALUES ('S0001', 'Heart Screening', 1799, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Resting ECG, Echocardiogram, Stress Test, Chest X-Ray, Ultrasound of Abdomen & Pelvis', 3");
			//st.executeUpdate("INSERT INTO Branch VALUES ()");
			//st.executeUpdate("INSERT INTO Branch VALUES ()");
			st.executeUpdate("UPDATE Appointment SET attendance = 'Attended' WHERE id = 1111");
			st.executeUpdate("DELETE FROM Appointment WHERE id = 1111");
			PreparedStatement pst = conn.prepareStatement("INSERT INTO Appointment (id, date, service) VALUES (?, ?, ?)");
			pst.setInt(1, 50);
			pst.setString(2, "date");
			pst.setString(2, "service");
			pst.executeUpdate(); // no args

			
			
			ResultSet serviceResult = st.executeQuery("SELECT * FROM Service");
			ResultSet branchResult = st.executeQuery("SELECT * FROM Branch");
			
			while(serviceResult.next()) {
				String data = "";
				for(int i = 1; i < 6; i++) {
					data += serviceResult.getString(i) + " ";
				}
				System.out.println(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
