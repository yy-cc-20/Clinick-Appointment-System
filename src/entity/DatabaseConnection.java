
/* Notes on using JDBC
 * 
 * 0. Exception throws: SQLException, ClassNotFoundException
 * 1. import java.sql.*;
 * 2. Load and register the driver
 * 		2.1 Download the driver class
 * 		Database | Driver class			 | Download 
 * 		MySQL    | com.mysql.jdbc.Driver | mysql-connector-java-8.0.28.jar (https://jar-download.com/artifacts/mysql/mysql-connector-java/8.0.28)
 * 		Access: not supported by JDK1.8 and latter
 * 		Oracle: need username and password to access to
 * 
 * 		-> build path -> external JAR file -> add mysql-connector-java-6.0.6.jar to class path
 * 
 * 		2.2 Load and register the driver
 * 		String mysqlDriver = "com.mysql.jdbc.Driver";
 * 		Class.forName(mysqlDriver);
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
 * 		ResultSet rs = st.executeUpdate(sql);
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

package entity;

import java.sql.*; // Step 1

public class DatabaseConnection {
	private static DatabaseConnection instance;
	private static Connection conn;
	private static Statement st;

	private DatabaseConnection() throws ClassNotFoundException, SQLException { // private constructor for singleton !!!
		// Step 2: Load and register the driver
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 3: Create Connection
		conn = DriverManager.getConnection("jdbc:mysql://localhost/Clinick-Appointment-System");
		if (conn == null) {
			System.out.println("Error connecting to database.");
			System.out.println("Exiting...");
			System.exit(1);
		}
		
		setUpDatabaseIfNotExist();
		
		// Step 4: Create a statement
		st = conn.createStatement();
	} 
	
	// Already connect to database
	public static DatabaseConnection getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null)
			instance = new DatabaseConnection();
		return instance;
	}
	
	public static void closeConnection() throws SQLException{
		conn.close();
	}
	
	private void setUpDatabaseIfNotExist() {
		
	}
}
