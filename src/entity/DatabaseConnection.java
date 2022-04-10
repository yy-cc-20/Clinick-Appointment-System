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

	public DatabaseConnection() throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found.");
			e.printStackTrace();
		}

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

	public void setUpDatabase() throws SQLException {
		try {
			st = conn.createStatement();
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