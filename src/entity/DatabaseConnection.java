/** What this class does:
 * - connects to the database 
 * - automatically creates tables if not exist (Just use this class, then no need to write extra code to create tables)
 * - automatically insert the data into the tables.
 * 
 * This class is using MySQL database.
 * @setup have mysql connector downlowded
 * @setup open mysql workbench, create a schema (database) call Clinick-Appointment-System
 * @setup add external JARs file (mysql-connector-java-8.0.28.jar) to the build path.
 * 
 * This class is using the singleton design pattern. 
 * There is only one object created for the Connection class in the system.
 * 
 * How to use this class:
 * 1. Connection conn = DatabaseConnection.getConnection();
 * 2. Statement st = conn.createStatement();
 * 3. PreparedStatement pstmt = conn.prepareStatement("SQL query here");
 * 4. st.executeUpdate("SQl query here");
 * 
 */

package entity;

import java.sql.*;

public class DatabaseConnection {
	private static Connection conn; // Singleton
	private static Statement st;

	private DatabaseConnection() throws SQLException { // private constructor for singleton !!!
		
		// Create Connection
		try {
			// Database name: Clinick-Appointment-System
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/clinick-appointment-system", "root", "root");
			st = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Error connecting to database.");
			System.out.println("Exiting...");
			e.printStackTrace();
			System.exit(1);
		}
		
		setUpDatabaseIfNotExist();
	} 

	public static Connection getConnection() throws SQLException {
		if (conn == null)
			new DatabaseConnection();
		return conn; // Already connect to database
	}
	
	public static void closeConnection() throws SQLException {
		conn.close();
	}
	
	private void setUpDatabaseIfNotExist() throws SQLException {
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
	
	private void insertDoctorTable() throws SQLException {
		// Good practice for autocommit statement
		conn.setAutoCommit(false); // So that multiple SQL statements can all run inside the same transaction
		st.addBatch("INSERT INTO doctor (username, password) VALUES ('username', 'password');");
		st.addBatch("INSERT INTO doctor (username, password) VALUES ('testing', 'password');");
		st.executeBatch();
		// Should anything go wrong with any of the insert statements, the whole transaction
		// would be rolled back, so will not have inconsistent data in the database
		conn.commit();           
		conn.setAutoCommit(true); // Set back to default, so other part of the code will not be affected
	}
	
	// DatabaseConnectionTest
	public static void main(String[] args) throws SQLException {
		Statement st = DatabaseConnection.getConnection().createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM doctor;");
		if (rs != null) {
			while (rs.next()) { // Move the cursor to the next row, return false if empty
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
			}
		}
	}
}

/* Notes on how to use JDBC
 * 
 * 0. Exception throws: SQLException, ClassNotFoundException
 * 1. import java.sql.*;
 * 2. Load and register the driver
 * 		2.0 Download MySQL
 * 		youtube tutorial: https://www.youtube.com/watch?v=BOUMR85B-V0
 		- download: https://dev.mysql.com/downloads/file/?id=510038
 		- open mysql workbench, create a schema (database) call Clinick-Appointment-System
 * 
 * 		2.1 Download the driver class
 * 		Database | Download 
 * 		MySQL    | mysql-connector-java-8.0.28.jar (https://jar-download.com/artifacts/mysql/mysql-connector-java/8.0.28)
 * 
 * 		-> build path -> external JAR file -> add mysql-connector-java-6.0.6.jar to class path
 * 
 * 		2.2 Load and register the driver [no need, the driver class will be added automatically in the newer version]
 * 		//String mysqlDriver = "com.mysql.jdbc.Driver";
 * 		//Class.forName(mysqlDriver);
 * 
 * 3. Create connection
 * 		String databaseURL = "jdbc:mysql://localhost/Clinick-Appointment-System";
 * 		Connection conn = DriverManager.getConnection(databaseURL);
 * 
 * 		if (connection == null) {
 * 			System.out.println("Error connecting to database.");
 * 			System.out.println("Exiting...");
 * 			System.exit(1);
 * 		}
 * 
 * 4. Create a statement
 * 		Statement st = conn.createStatement();
 * 		String sql = "INSERT INTO Doctor(id, username, password) VALUE (?, ?, ?)";
 * 		
 *		// Bind parameter
 * 		PreparedStatement pstmt = connection.prepareStatement(sql);
 * 		pstmt.setInt(1, 4);
 * 		pstmt.setString(2, "Ali");
 * 		pstmt.setString(3, "123");
 * 
 * 5. Execute the query
 * 		String sql = "&^%(^";
 * 		ResultSet rs = st.executeQuery(sql);
 * 
 * 		pstmt.executeUpdate();
 * 		
 * 6. Process the result
 * 		if (rs != null) {
 * 			while (rs.next()) { // Move the cursor to the next row, return false if empty
 * 				rs.getInt(1);
 * 				rs.getString(2);
 * 				rs.getString(3);
 * 			}
 * 		}
 * 
 * 7. Close connection
 * 		conn.close();
 */