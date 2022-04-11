package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetUpDatabase {

	private static Statement st;
	
	public static void setUpDatabaseIfNotExist() {
		try {
			st = DatabaseConnection.getConnection().createStatement();
		
			// Recreate the tables in the database in case the tables' structure has changed
			String[] recreateTables = {
					"DROP TABLE IF EXISTS Appointment",
					"DROP TABLE IF EXISTS Allocation",
					"DROP TABLE IF EXISTS Branch",
					"DROP TABLE IF EXISTS Doctor",
					"DROP TABLE IF EXISTS Patient",
					"DROP TABLE IF EXISTS Receptionist",
					"DROP TABLE IF EXISTS Account",
					"DROP TABLE IF EXISTS Service",
					"DROP TABLE IF EXISTS Timeslot",
	
			};
			for (int i = 0; i < recreateTables.length; i++) {
				st.executeUpdate(recreateTables[i]);
			}
			
			
			// create all necessary tables
			String[] createTables = {
					"CREATE TABLE IF NOT EXISTS Account (id INT AUTO_INCREMENT, username VARCHAR(25) NOT NULL, password VARCHAR(25) NOT NULL, PRIMARY KEY (id))",
					"CREATE TABLE IF NOT EXISTS Doctor (id INT AUTO_INCREMENT, name VARCHAR(100) NOT NULL, accountId INT NOT NULL, PRIMARY KEY (id), FOREIGN KEY (accountId) REFERENCES Account(id))",
					"CREATE TABLE IF NOT EXISTS Patient (id INT AUTO_INCREMENT, name VARCHAR(100) NOT NULL, ic VARCHAR(25) NOT NULL, phone VARCHAR(25), address VARCHAR(100), accountId INT NOT NULL, PRIMARY KEY (id), FOREIGN KEY (accountId) REFERENCES Account(id))",
					"CREATE TABLE IF NOT EXISTS Receptionist (id INT AUTO_INCREMENT, name VARCHAR(25) NOT NULL, accountId INT NOT NULL, PRIMARY KEY (id), FOREIGN KEY (accountId) REFERENCES Account(id))",
					"CREATE TABLE IF NOT EXISTS Branch (id INT AUTO_INCREMENT, name VARCHAR(50) NOT NULL, address VARCHAR(250), telNo VARCHAR(25), receptionistId INT, PRIMARY KEY (id), FOREIGN KEY (receptionistId) REFERENCES Receptionist(id))",
					"CREATE TABLE IF NOT EXISTS Service (id INT AUTO_INCREMENT, name VARCHAR(25) NOT NULL, price double NOT NULL, description VARCHAR(250), timeSlotRequired int NOT NULL, PRIMARY KEY (id))",
					"CREATE TABLE IF NOT EXISTS Timeslot (date DATE, slotlist VARCHAR(250) NOT NULL, PRIMARY KEY (date))",
					"CREATE TABLE IF NOT EXISTS Allocation (id INT AUTO_INCREMENT, branchId INT NOT NULL, serviceId INT NOT NULL, doctorId INT NOT NULL, timeslotDate DATE NOT NULL, PRIMARY KEY (id), FOREIGN KEY (branchId) REFERENCES Branch(id), FOREIGN KEY (serviceId) REFERENCES Service(id), FOREIGN KEY (doctorId) REFERENCES Doctor(id), FOREIGN KEY (timeslotDate) REFERENCES Timeslot(date))",
					"CREATE TABLE IF NOT EXISTS Appointment (id INT AUTO_INCREMENT, attendance VARCHAR(10), startSlot INT NOT NULL, patientId INT NOT NULL, allocationId INT NOT NULL, PRIMARY KEY (id), FOREIGN KEY (patientId) REFERENCES Patient(id), FOREIGN KEY (allocationId) REFERENCES Allocation(id))",
				};
	
			for (int i = 0; i < createTables.length; i++) {
				st.executeUpdate(createTables[i]);
			}
	
			// insert data into all tables
			insertAccountTable();
			insertDoctorTable();
			insertPatientTable();
			insertReceptionistTable();
			insertBranchTable();
			insertServiceTable();
			insertTimeslotTable();
			insertAllocationTable();
			insertAppointmentTable();
	
			// conn.commit(); // Cannot call commit when autocommit is true
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertAccountTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false); // So that multiple SQL statements can all run inside the same transaction
		String[] insertStatements = {
				// todo: add 55 more records of Account to match total 75 users
				//"INSERT IGNORE INTO Account (username, password) VALUES ('', '')",

				"INSERT IGNORE INTO Account (username, password) VALUES ('peppapig', 'fairyprincess')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('felix', 'rightyO')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('cosmo', 'godparent1')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('wanda', 'godparent2')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('timmy', '10yoboy')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('vicky', 'babysitter')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('poofff', 'babyboy')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('trixie', 'imrich')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('bubbles', 'prettiestgirl')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('blossom', 'powerpuff')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('buttercup', 'kickass')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('phineas', 'whattodotoday')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('ferb', 'ferbs')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('candace', 'loveJ')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('jeremy', 'guitar')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('isabella', 'firesidegirls')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('perry', 'agentP')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('heinz', 'cursePERRY')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('suzy', 'loveJmost')",
				"INSERT IGNORE INTO Account (username, password) VALUES ('vanessa', 'whatever')",
		};
		for (int i = 0; i < insertStatements.length; i++) {
			st.addBatch(insertStatements[i]);
		}
		st.executeBatch(); // Should anything go wrong with any of the insert statements, the whole transaction would be rolled back, so will not have inconsistent data in the database
		conn.commit();
		conn.setAutoCommit(true); // Set back to default, so other part of the code will not be affected
	}

	private static void insertAllocationTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false);

		String[] insertStatements = {
				// records for the 5 branches on the same day
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (1, 1, 1, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (1, 1, 2, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (1, 2, 3, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (1, 2, 4, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (1, 3, 5, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (2, 4, 6, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (2, 4, 7, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (2, 5, 8, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (2, 5, 9, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (2, 6, 10, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (3, 7, 11, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (3, 7, 12, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (3, 8, 13, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (3, 8, 14, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (3, 9, 15, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (4, 10, 16, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (4, 10, 17, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (4, 11, 18, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (4, 11, 19, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (4, 12, 20, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (5, 13, 21, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (5, 13, 22, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (5, 14, 23, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (5, 14, 24, '2022-05-15')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (5, 15, 25, '2022-05-15')",

				// history records
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (1, 1, 1, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (1, 1, 2, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (1, 2, 3, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (1, 2, 4, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (1, 3, 5, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (2, 4, 6, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (2, 4, 7, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (2, 5, 8, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (2, 5, 9, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (2, 6, 10, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (3, 7, 11, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (3, 7, 12, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (3, 8, 13, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (3, 8, 14, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (3, 9, 15, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (4, 10, 16, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (4, 10, 17, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (4, 11, 18, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (4, 11, 19, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (4, 12, 20, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (5, 13, 21, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (5, 13, 22, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (5, 14, 23, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (5, 14, 24, '2022-01-20')",
				"INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId, timeslotDate) VALUES (5, 15, 25, '2022-01-20')",
		};
		for (int i = 0; i < insertStatements.length; i++) {
			st.addBatch(insertStatements[i]);
		}
		st.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}

	private static void insertAppointmentTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false);
		String[] insertStatements = {
				// allocationId = 1, so branchId = 1, serviceId = 1, doctorId = 1, timeslotDate = 2022-05-15
				"INSERT IGNORE INTO Appointment (attendance, startSlot, patientId, allocationId) VALUES ('NAN', 1, 1, 1)",
				// with the above information and with still allocationId = 1, now the startSlot = 4 (from the service timeSlotRequired = 3)
				//"INSERT IGNORE INTO Appointment (attendance, startSlot, patientId, allocationId) VALUES ('NAN', 4, 2, 1)",
				
				/* 
				// todo: create more than 50 records for the 50 existing patients
				"INSERT IGNORE INTO Appointment (attendance, startSlot, patientId, allocationId) VALUES ('NAN', 3, 3, )",
				
				// todo: different day records
				"INSERT IGNORE INTO Appointment (attendance, startSlot, patientId, allocationId) VALUES ('NAN', 3, 4, )",

				// todo: history records 
				"INSERT IGNORE INTO Appointment (attendance, startSlot, patientId, allocationId) VALUES ('NAN', , , )",
				*/

		};
		for (int i = 0; i < insertStatements.length; i++) {
			st.addBatch(insertStatements[i]);
		}
		st.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}

	private static void insertBranchTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false);
		String[] insertStatements = {
				// each branch has one receptionist
				"INSERT IGNORE INTO Branch (name, address, telNo, receptionistId) VALUES ('Klang Health Screening Centre', 'No.29, Jalan Bayu Tinggi 1A/KS6, Taman Bayu Tinggi, 41200 Klang, Selangor', '03-55699996', 1)",
				"INSERT IGNORE INTO Branch (name, address, telNo, receptionistId) VALUES ('Cheras Health Screening Centre', 'No 37, 39, 41&43, Jalan 4/96A, Taman Cheras Makmur, Cheras 56100 Kuala Lumpur', '03-91309163', 2)",
				"INSERT IGNORE INTO Branch (name, address, telNo, receptionistId) VALUES ('Seri Kembangan Health Screening Centre', 'No.1 & 3, Ground Floor, Jalan Besar Susur 1, 43300 Seri Kembangan, Selangor', '03-89599924', 3)",
				"INSERT IGNORE INTO Branch (name, address, telNo, receptionistId) VALUES ('Kajang Health Screening Centre', 'No.40 & 41, Jalan Tukang, 43000 Kajang, Selangor', '03-87337433', 4)",
				"INSERT IGNORE INTO Branch (name, address, telNo, receptionistId) VALUES ('Kudai Health Screening Centre', 'Tower 1, No.68, Jalan Pertama 1, Danga Utama Commercial Center, 81300 Skudai Johor Bahru', '07-5500323', 5)",
		};
		for (int i = 0; i < insertStatements.length; i++) {
			st.addBatch(insertStatements[i]);
		}
		st.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}

	private static void insertDoctorTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false);
		String[] insertStatements = {
				// each branch has 5 doctors
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Sutheaswari Thivy a/l Pragash', 6)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Nur Hajjah Anisah Nizam', 7)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Nor Hazmeera binti Syahir', 8)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Saajid bin Irfaan', 9)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Tian Yang', 10)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Gong Ming Yu', 11)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Liao Qing', 12)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Sun Ming Zhu', 13)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Siraaj bin Jaarallah', 14)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Haarith bin Maisoon', 15)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Felixia Kong Shei Kam', 16)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Sothinathan Goundar a/l Devaraj', 17)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Yu Yang', 18)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Ren Lu', 76)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Karina Yong', 77)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Sanjay Gupta', 19)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Ken Jeong', 20)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Drew Pinsky', 21)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Alex Oon Shun Nau', 22)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Nor Asma binti Zuraiful', 78)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Nur Mira binti Syed Shah Haizan', 23)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('R. G. Waythamoorthy', 24)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Anthony C. Griffin', 25)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Carol McGuire', 79)",
				"INSERT IGNORE INTO Doctor (name, accountId) VALUES ('Kang Min', 80)",
		};
		for (int i = 0; i < insertStatements.length; i++) {
			st.addBatch(insertStatements[i]);
		}
		st.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}

	private static void insertPatientTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false);
		
		String[] insertStatements = {
				// each patient can have more than one appointments
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Sannatasah a/l Kamal Lourdenadin', 'EC4744643', '6010-021 2543', '59, Jalan Air Itam, Bandar Bukit Sinar, 62033 Precinct 13, Putrajaya', 26)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Loh Pao Gu', '890503-01-5793', '6015-186 2389', 'Jln Kuching 2/42, SS67, 98569 Sri Aman, Sarawak', 27)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Nor Fathiah Saad binti Zubair Qayyum', 'N 1707843', '6015-497 5301', '4, Jln 69Y, Seksyen 67, 21164 Kuala Dungun, Terengganu', 28)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Melinda Tern Kay Ooi', 'N 00144521', '603-3826 8587', '5, Jalan 2/6W, Taman Indah, 05568 Alor Setar, Kedah', 29)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('D. Kathigasu', 'N 00477014', '6011-1297 3080', 'Jln Sultan Sulaiman 7/47E, USJ 17, 32718 Bidor, Perak', 30)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Hj Ridhuan Jumat', '880904-12-8743', '6015-226 6051', '50, Jalan Yap Ah Loy, Laman Tasik Puteri, 84908 Larkin, Johor Darul Tazim', 31)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Law Phong Jong', 'N 00474160', '6014-518 4462', 'Lot 71, Jalan 3/27, PJS4, 78209 Cheng, Melaka', 32)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Ngeh An Hoong', 'N 1188324', '6081-67 6147', '3, Jln Ariffin 9, Bandar Kuchai, 47675 Port Klang, Selangor', 33)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Tang Bung Gau', '941204-13-3569', '6015-238 095', '4, Jln Cinta 6, Batu Selamat, 32525 Parit, Perak', 34)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('K. Kundargal', 'E58728453', '6018-326 1950', 'No. 1Z-70, Jln Raja Abdullah 3/1, Medan Keramat, 21165 Bukit Payong, Terengganu', 35)",				
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Tew Tean Ti', 'E05160360', '606-049 0256', 'Lot 50, Jalan 42Y, Bandar Ria, 71671 Chembong, Negeri Sembilan Darul Khusus', 36)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Hang Bai Shok', '881114-06-5432', '6015-674 1559', '10, Jln 8, Taman Damansara, 62384 Precinct 8, Putrajaya', 37)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Nurul Salwa Azlansyhah', 'A7186475', '6019-869 9545', 'No. 870, Jln Maharajalela 7/5A, Bukit Ulu, 93253 Batu Niah, Sarawak', 38)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Mohd Khoirul bin Che Ashraf', '780522-08-0080', '6015-143 2342', '421, Jalan Syed Putra 8, PJU1, 35187 Sungai Siput, Perak Darul Ridzuan', 39)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Nur Weni Miskan binti Shohaimi', 'G39717034', '6016-563 0522', '3, Lorong 92L, Seri Tinggi, 34712 Changkat Jering, Perak', 40)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Noor Suhaida Nazarudin', 'E65078534', '606-368 6946', 'No. R-58-55, Jln 9/87H, Desa Tropika, 54063 Kampung Baru, Kuala Lumpur', 41)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Asha a/l Shankar', 'G39406062', '6015-987 4537', '56, Jalan 3/2, Medan Pahlawan, 83223 Johor Lama, Johor Darul Tazim', 42)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Soo Goo Thian', 'G54838948 ', '6086-49 9817', '65, Jalan Perak, Bandar Timur, 06498 Changlun, Kedah Darul Aman', 43)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Noor Amrina Muzammil binti Irfan', '750907-03-7452', '6011-233 9122', 'No. 32, Lorong Rakyat 2/8E, USJ 29N, 64103 Bentong, Pahang', 44)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Thanuja a/l Selvaraju', '690320-08-4683', '6015-277 3977', '76, Jalan 5O, Bandar Jaya, 75851 Pekan Asahan, Melaka', 45)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Shahrulnaim Hakimie bin Che Ramly', 'E3905107K', '6012-765 3146', 'No. 4W-73, Jln Perdana 9/8, Lembah Mulia, 84851 Pontian Kechil, Johor', 46)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Kanagaraj Bamadhaj a/l Mahathir Naidu', 'E4701031B', '6015-160 2748', '7-4, Jalan Sempadan 1/57, Taman Ria, 65765 Panching, Pahang', 47)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Kanagaraj a/l Devan Karathu', 'E5787905H', '6013-483 6663', 'Lot 8-8, Jalan 8K, Bandar Anggerik, 72976 Senawang, Negeri Sembilan Darul Khusus', 48)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Muhammed Afandy bin Nik Ihsan Rafizal', 'AA1126255', '606-587 1725', '20, Jalan 8/2P, Pangsapuri Maxwell, 02440 Mata Ayer, Perlis', 49)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Mohammad Wan Shahrizal bin Ady', '690903-05-3588', '607-571 836', 'D-18-02, Jln 4/7P, USJ 28, 23169 Kijal, Terengganu', 50)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Hailey Mok', '920605-11-6220', '6010-622 7211', 'No. 10, Jalan Ismail Abdul Rahman 10/7D, PJU72H, 84724 Mersing, Johor Darul Tazim', 51)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('M. G. G.  Karamjit', 'A35811373', '6011-1721 3067', 'No. 8-9, Jalan 4/9, Bandar Bendahara, 88912 Tanjung Aru, Sabah', 52)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Hajjah Adini Samsul binti Wazif', 'A 5085709', '6015-580 4336', '55, Jalan Kuching 9/38, Bandar Keramat, 53990 Karak, Pahang Darul Makmur', 53)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Sarjit Rishyakaran a/l Devaki Madhavan', '931230-09-5723', '6015-501 7663', 'No. J-23-24, Jalan Masjid 8/97W, Kampung Sentosa, 18140 Ketereh, Kelantan Darul Naim', 54)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Lok Meg Bow', 'C1732608', '6015-587 7152', '1N-44, Lorong Maxwell 4, Bandar Pahlawan, 53303 Kepong, Kuala Lumpur', 55)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Muhammet Aizam Ridzuan bin Zubir', '850604-08-3457', '6015-292 3309', '89, Jln 3, Puncak Ulu, 59398 Lake Gardens, KL', 56)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Haran a/l Santokh', 'LH615098', '6015-390 0833', '6, Lorong Wan Kadir 4T, Bandar Kinrara, 62170 Precinct 12, Putrajaya', 57)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Arulraj Kunalan a/l Sivarasa Pereira', 'A2074275', '6013-199 1900', '13, Jln Pudu Lama 7, Seksyen 97, 75788 Alor Gajah, Malacca', 58)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Muhamed Che Dzulkarnaen Sanorhizam', '870909-07-23467', '6015-441 7253', '10, Jln Sempadan 2/3Z, Kondominium Puchong, 17322 Salor, Kelantan Darul Naim', 59)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Thew Mau Men', 'A32888014', '6010-311 8511', 'No. 95, Lorong 5/93Q, Sri Tun Perak, 33559 Malim Nawar, Perak', 60)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Muhammad Haji Zul Johari bin Nik Daniel', 'A4689996', '6015-325 3482', '5G-73, Jln 5P, Kampung Aman, 25604 Gambang, Pahang', 61)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Nor Humaira Aznai binti Dolbahrin', 'A8300392', '6011-1315 2019', '38, Lorong Air Itam 2, Bandar Sungai Buaya, 65720 Kuang, Selangor Darul Ehsan', 62)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Sangeeta Gnanalingam a/l Surendran Kaveri', 'AT 552393', '6010-946 1787', 'No. 1, Jalan 5/73, Lembah Belakong, 86258 Segamat, Johor Darul Tazim', 63)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Mohd Syed Ajis bin Anwar Sulong', 'B 3044365', '606-919 0659', '8, Lorong 39I, Seksyen 90O, 32605 Changkat Jering, Perak Darul Ridzuan', 64)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('V.  Vadaketh', 'X050098', '6015-604 4121', 'No. 3, Jalan Bukit Petaling 7, Ara Flora, 75058 Durian Tunggal, Melaka', 65)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('B.  Baljit', 'X034935', '6016-990 0241', 'No. 6-9, Jalan Sultan Abdul Samad 6/6G, Petaling Duta, 54403 Setapak, Kuala Lumpur', 65)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Vellu a/l Serbegeth Marimuthu', '740808-01-6223', '6017-668 6183', '36, Jalan Bukit Bintang 2/1S, Seri Country Heights, 06912 Durian Burung, Kedah Darul Aman', 67)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Li Cheam Foo', '980905-22-8790', '604-320 9331', '167, Jalan Tan Cheng Lock 7, Alam Jaya, 57716 Setiawangsa, KL', 68)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Muhammad Hj Saidin bin Rusman', 'AA6342205', '606-305 9633', '86, Jalan Hang Tuah 3/5V, Bukit Bendahara, 17938 Wakaf Bharu, Kelantan', 69)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('S. A. Renuga', '000122-12-3742', '603-0217 4442', '9, Jalan 5, Petaling Brickfields, 91276 Tambunan, Sabah', 70)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Kiran a/l Kavi', 'M55210284', '607-229 2232', '47, Lorong 8P, Bandar Rahman, 01405 Sanglang, Perlis', 71)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Lok Nin Her', 'M49334041', '603-8629 7242', '5D-09, Lorong U-Thant 14H, Taman Desa Kinrara, 36228 Bidor, Perak', 72)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Khairudin Daryusman bin Azlan', 'B9103971', '6015-680 7432', '6-1, Jalan 5, USJ 47, 87016 Kiamsam, Labuan', 73)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Karnail a/l Yugendran', 'M79586826', '6019-644 3372', 'No. 73, Jalan Istana 7, Apartment Bestari, 18352 Wakaf Bharu, Kelantan', 74)",
				"INSERT IGNORE INTO Patient (name, ic, phone, address, accountId) VALUES ('Leung Lun Woon', 'A30930309', '606-488 0108', '621, Jalan 3/4, Bandar Baru Laksamana, 12619 George Town, Penang', 75)",
			};
		for (int i = 0; i < insertStatements.length; i++) {
			st.addBatch(insertStatements[i]);
		}
		st.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}

	private static void insertReceptionistTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false);
		String[] insertStatements = {
				// each receptionist works at one branch
				"INSERT IGNORE INTO Receptionist (name, accountId) VALUES ('Aw Kung Bang', 1)",
				"INSERT IGNORE INTO Receptionist (name, accountId) VALUES ('Liana Yaacup binti Che Jaferi', 2)",
				"INSERT IGNORE INTO Receptionist (name, accountId) VALUES ('Nurul Hasya binti Syarafuddin', 3)",
				"INSERT IGNORE INTO Receptionist (name, accountId) VALUES ('Rakesh Devaser a/l Shree', 4)",
				"INSERT IGNORE INTO Receptionist (name, accountId) VALUES ('Nor Jani binti Syed Tumiran', 5)",
		};
		for (int i = 0; i < insertStatements.length; i++) {
			st.addBatch(insertStatements[i]);
		}
		st.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}

	private static void insertServiceTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false);
		String[] insertStatements = {
				// each branch provides 3 services
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Heart Screening', 1799, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Resting ECG, Echocardiogram, Stress Test, Chest X-Ray, Ultrasound of Abdomen & Pelvis', 3)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Stroke Screening', 2699, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Resting ECG, MRA Brain, Diet Counselling', 3)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Lung Cancer Screening', 690, 'Low Dose CT Scan without Contrast', 1)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Eye Screening', 259, 'History & Clinical Examination, Visual Acuity, Auto-Refraction, Slit Lamp Examination, Intraocular Pressure, Eye Dilation', 2)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Oral Health Screening', 159, 'History & Clinical Examination, Oral Cancer Screening, Gum Health Profile, Decay Risk Profile', 1)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Bone Screening', 800, 'History & Clinical Examination, DEXA Scan, Laboratory Test, Nutritional & Body Fat Assessment', 2)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Blood Screening', 250, 'Full Blood Count, Urea, Creatinine, Uric Acid, Glucose Fasting, Hepatitis B Screening, Liver Function Profile, Lipid Profile, Urine FEME', 1)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Colonoscopy', 2099, 'History & Clinical Examination, Blood Test, Ultrasound of Abdomen, Fibroscan, CT Abdomen, Bowel Cleansing, Stress Test', 3)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Gastroscopy', 1500, 'History & Clinical Examination, Blood Test, Chest X-Ray, Ultrasound of Abdomen & Pelvis, CT Abdomen, Bowel Cleansing, Stress Test, H-Pylori Test', 3)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Allergy Test', 999, 'Complete Hemogram, IgE Total Antibody', 1)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Wellness Screeening', 1060, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Resting ECG, Pulmonary Function Test, Ultrasound of Abdomen & Pelvis, Diet Counselling', 2)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Men Health Screening', 1588, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Resting ECG, Pulmonary Function Test, Ankle Brachial Pressure Index, Uroflowmetry and Residual Urine Volume, Chest X-Ray, Ultrasound of Abdomen & Pelvis, Diet Counselling', 4)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Women Health Screening', 1999, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Thin Prep, Resting ECG, Pulmonary Function Test, Ankle Brachial Pressure Index, Chest X-Ray, 3D Mammogram, Ultrasound of Breast, Abdomen & Pelvis, Bone Mineral Density Scan, Diet Counselling', 5)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Pre-Marital Screening', 299, 'Blood Test including Full Blood Count, Peripheral Blood Film, Blood Grouping, Hepatitis B Screening, and HIV', 5)",
				"INSERT IGNORE INTO Service (name, price, description, timeSlotRequired) VALUES ('Post-Covid Screening', 399, 'History & Clinical Examination, Anthropometry, Visual Acuity, Blood Test, Urine Test, Chest X-Ray, Lung Function Test', 5)",
		};
		for (int i = 0; i < insertStatements.length; i++) {
			st.addBatch(insertStatements[i]);
		}
		st.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}

	private static void insertTimeslotTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false);
		String[] insertStatements = {
				"INSERT IGNORE INTO Timeslot (date, slotlist) VALUES ('2022-01-20', 'SLOT_1, SLOT_2, SLOT_3, SLOT_4, SLOT_5, SLOT_6, SLOT_7, SLOT_8, SLOT_9, SLOT_11, SLOT_111, SLOT_12, SLOT_13, SLOT_14')",
				"INSERT IGNORE INTO Timeslot (date, slotlist) VALUES ('2022-05-15', 'SLOT_1, SLOT_2, SLOT_3, SLOT_4, SLOT_5, SLOT_6, SLOT_7, SLOT_8, SLOT_9, SLOT_11, SLOT_111, SLOT_12, SLOT_13, SLOT_14')",
				"INSERT IGNORE INTO Timeslot (date, slotlist) VALUES ('2022-06-12', 'SLOT_1, SLOT_2, SLOT_3, SLOT_4, SLOT_5, SLOT_6, SLOT_7, SLOT_8, SLOT_9, SLOT_11, SLOT_111, SLOT_12, SLOT_13, SLOT_14')",
		};
		for (int i = 0; i < insertStatements.length; i++) {
			st.addBatch(insertStatements[i]);
		}
		st.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}
}
