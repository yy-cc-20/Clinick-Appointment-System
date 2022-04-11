package database;

/** What this class does:
 * - connects to the database 
 * - call SetUpDatabase to create tables if tables not exist
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DatabaseConnection {

	private static Connection conn;
	private static Statement st;
	private static String url = "jdbc:mysql://localhost:3308/clinick-appointment-system";
	private static String username = "root";
	private static String password = "root";

	public DatabaseConnection() throws SQLException {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Error connecting to database.");
			System.out.println("Exiting...");
			e.printStackTrace();
			System.exit(1);
		}
		SetUpDatabase.setUpDatabaseIfNotExist();
	}

	public static Connection getConnection() throws SQLException {
		if (conn == null)
			new DatabaseConnection();
		return conn; // Already connect to database
	}
	
	public static void closeConnection() throws SQLException {
		conn.close();
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