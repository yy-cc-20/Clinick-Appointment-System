package database;

/**
 * This system is using MySQL database. If you want to run the system, you must first connect to the
 * MySQL database in your computer.
 *
 * @setup MySQL database in your computer
 * <p>
 * YouTube tutorial: https://www.youtube.com/watch?v=BOUMR85B-V0
 * 1. Has MySQL connector downloaded (Download at https://dev.mysql.com/downloads/file/?id=510038)
 * <p>
 * 2. Open MySQL workbench, create a schema (as known as database) called "Clinick-Appointment-System"
 * <p>
 * 3. Download the JAR file "mysql-connector-java-8.0.28.jar" at https://jar-download.com/artifacts/mysql/mysql-connector-java/8.0.28
 * This is the latest MySQL driver. Some older JDK might not be able to support this MySQL driver.
 * <p>
 * 4. Add the JARs file to the project build path:
 * build path -> Library -> class path -> add external JARs
 * <p>
 * 5. Change the port number, username and password for the attributes of this class to the same as your MySQL database
 * <p>
 * 6. You can run the main method in this class to check the connectivity to your database.
 * <p>
 * 7. You are done. Now you can run the main method of boundary.ClinickAppointmentSystem to start the system.
 * 
 * @description What this class does:
 * - connects to the database
 * - call SetUpDatabase to create tables if tables not exist
 * <p>
 * This class is using the singleton design pattern.
 * There is only one object created for the Connection class in the system.
 * <p>
 * How to use this class:
 * 1. Connection conn = DatabaseConnection.getConnection();
 * 2. Statement st = conn.createStatement();
 * 3. PreparedStatement pstmt = conn.prepareStatement("SQL query here");
 * 4. st.executeUpdate("SQl query here");
 */

/**
 * @description
 *
 * What this class does:
 * - connects to the database 
 * - call SetUpDatabase to create tables if tables not exist
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

import java.sql.*;

public class DatabaseConnection {
    private static Connection conn; // Singleton

    /** @setup 5. Change the value of these variable to connect to your database */
    private static final int portNo = 3308;
    private static final String databaseName = "clinick-appointment-system";
    private static final String url = "jdbc:mysql://localhost:" + portNo + "/" + databaseName;
    private static final String username = "root";
    private static final String password = "root";

	private DatabaseConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Error connecting to database.");
			System.out.println("Exiting...");
			e.printStackTrace();
			System.exit(1);
		}
		DatabaseSetup.setupDatabaseIfNotExist();
	} // Private constructor for singleton

    public static Connection getConnection() throws SQLException {
        if (conn == null)
            new DatabaseConnection();
        return conn; // Already connect to database
    }


    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // run me to test connection!!!
    // DatabaseConnectionTest
    public static void main(String[] args) throws SQLException {
        Statement st = DatabaseConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM Appointment ;");
        while (rs.next()) { // Move the cursor to the next row, return false if empty
            System.out.println("There are " + rs.getInt(1) + " appointment records.");
        }
        System.out.println("DatabaseConnection test success!");
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
