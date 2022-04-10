/**
 * @subject 	UECS2344 SOFTWARE DESIGN 
 * @trimester 	January 2022
 * 
 * @system 		Clinik Appointment System
 * @date 		15/04/2022
 * 
 * @author 		Ling Sun Shuai      2004562 P2 
 * @author 		Kong Suet Hua       2005756 P5
 * @author 		Olivia Ong Yee Ming 2004564 P5
 * @author 		Tan Jia Qi          1904022 P2
 * @author 		Yang Chu Yan        2005912 P2
 * 
 * @database 	MySQL, using JDBC API (driver class version 8.0.28)
 * @see 		entity.DatabaseConnection (username: root password: root)
 * 
 * @description A console program that aims to digitize the process of making an appointment. 
 * 				Applying object-oriented programming concept, using entity-boundary-controller design pattern.
 **/

package boundary;

import java.sql.SQLException;

import entity.*;

public class ClinickAppointmentSystem {
	public static void main(String[] args) throws SQLException {
		// Retrieve data from database
		IDataStore dataList = DataList.getInstance(); // Already retrieved the data

		// the login test data is created in DataList
		// role: doctor
		// userid: 1
		// username: username
		// password: password

		new ConsoleUI().start();
	}
}
