package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    private static Statement st;

    public static void setupDatabaseIfNotExist() {
        try {
            st = DatabaseConnection.getConnection().createStatement();

            // create all necessary tables
            String[] createTables = {
                    "CREATE TABLE IF NOT EXISTS Doctor (id INT AUTO_INCREMENT, name VARCHAR(100) NOT NULL, password VARCHAR(50) NOT NULL, PRIMARY KEY (id))",
                    "CREATE TABLE IF NOT EXISTS Patient (id INT AUTO_INCREMENT, name VARCHAR(100) NOT NULL, ic VARCHAR(25) NOT NULL, phone VARCHAR(25), address VARCHAR(100), password VARCHAR(50) NOT NULL, PRIMARY KEY (id))",
                    "CREATE TABLE IF NOT EXISTS Receptionist (id INT AUTO_INCREMENT, name VARCHAR(25) NOT NULL, password VARCHAR(50) NOT NULL, PRIMARY KEY (id))",
                    "CREATE TABLE IF NOT EXISTS Branch (id INT AUTO_INCREMENT, name VARCHAR(50) NOT NULL, address VARCHAR(250), telNo VARCHAR(25), receptionistId INT, PRIMARY KEY (id), FOREIGN KEY (receptionistId) REFERENCES Receptionist(id))",
                    "CREATE TABLE IF NOT EXISTS Service (id INT AUTO_INCREMENT, name VARCHAR(25) NOT NULL, price double NOT NULL, description VARCHAR(250), timeSlotRequired int NOT NULL, PRIMARY KEY (id))",
                    "CREATE TABLE IF NOT EXISTS Allocation (id INT AUTO_INCREMENT, branchId INT NOT NULL, serviceId INT NOT NULL, doctorId INT NOT NULL, PRIMARY KEY (id), FOREIGN KEY (branchId) REFERENCES Branch(id), FOREIGN KEY (serviceId) REFERENCES Service(id), FOREIGN KEY (doctorId) REFERENCES Doctor(id))",
                    "CREATE TABLE IF NOT EXISTS Appointment (id INT AUTO_INCREMENT, date DATE NOT NULL, attendance VARCHAR(10), startSlot INT NOT NULL, patientId INT NOT NULL, allocationId INT NOT NULL, PRIMARY KEY (id), FOREIGN KEY (patientId) REFERENCES Patient(id), FOREIGN KEY (allocationId) REFERENCES Allocation(id))", };
            for (String createTable : createTables) {
                st.executeUpdate(createTable);
            }

            // insert data into all tables
            insertDoctorTable();
            insertPatientTable();
            insertReceptionistTable();
            insertBranchTable();
            insertServiceTable();
            insertAllocationTable();
            insertAppointmentTable();

            // conn.commit(); // Cannot call commit when autocommit is true
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertAllocationTable() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        String[] insertStatements = {
                // number of doctors => number of allocations == 25 records for both tables
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (1, 1, 1)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (1, 2, 2)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (1, 2, 3)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (1, 3, 4)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (1, 3, 5)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (2, 4, 6)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (2, 4, 7)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (2, 5, 8)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (2, 5, 9)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (2, 6, 10)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (3, 7, 11)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (3, 8, 12)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (3, 8, 13)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (3, 9, 14)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (3, 9, 15)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (4, 10, 16)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (4, 10, 17)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (4, 10, 18)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (4, 11, 19)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (4, 12, 20)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (5, 13, 21)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (5, 14, 22)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (5, 14, 23)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (5, 15, 24)",
                "INSERT IGNORE INTO Allocation (branchId, serviceId, doctorId) VALUES (5, 15, 25)", };
        for (String insertStatement : insertStatements) {
            st.addBatch(insertStatement);
        }
        st.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
    }

    private static void insertAppointmentTable() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        String[] insertStatements = {
                // startSlot 1-14, patientId 1-50, allocationId 1-25
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (1, '2022-03-03', 'Attended',  3,   1,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (2, '2022-03-03', 'Attended', 11,   2,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (3, '2022-03-04', 'Attended',  8,   3,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (4, '2022-03-05', 'Attended',  1,   4,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (5, '2022-03-05', 'Attended',  7,   5,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (6, '2022-03-10', 'Attended',  2,   6,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (7, '2022-03-10', 'Attended',  5,   7,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (8, '2022-04-10', 'Attended',  8,   8,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (9, '2022-04-10', 'Attended', 12,   9,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (10 ,'2022-04-11', 'Attended',  6,  10,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (11, '2022-04-25', 'NAN',       1,  11,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (12, '2022-04-25', 'NAN',       5,  12,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (13, '2022-04-25', 'NAN',       8,  13,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (14, '2022-04-27', 'NAN',       4,  14,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (15, '2022-04-27', 'NAN',       8,  15,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (16, '2022-04-27', 'NAN',      11,  16,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (17, '2022-04-29', 'NAN',       2,  17,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (18, '2022-04-29', 'NAN',       5,  18,  1)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (19, '2022-03-01', 'Attended',  1,  2,   2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (20, '2022-03-01', 'Attended',  7,  4,   2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (21, '2022-04-02', 'Attended',  1,  6,   2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (22, '2022-04-02', 'Attended',  9,  8,   2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (23, '2022-04-03', 'Attended',  3,  10,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (24, '2022-04-03', 'Attended', 11,  26, 19)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (25, '2022-04-04', 'Attended',  8,  12,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (26, '2022-04-05', 'Attended',  1,  13,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (27, '2022-04-05', 'Attended',  7,  14,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (28, '2022-04-06', 'Attended',  5,  15,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (29, '2022-04-07', 'Attended',  3,  16,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (30, '2022-04-07', 'Attended', 11,  17,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (31, '2022-04-08', 'Attended',  5,  18,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (32, '2022-04-08', 'Attended', 11,  19,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (33, '2022-04-09', 'Attended',  9,  20,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (34, '2022-04-10', 'Attended',  5,  21,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (35, '2022-04-10', 'Attended', 12,  22,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (36, '2022-04-25', 'NAN',       1,  23,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (37, '2022-04-25', 'NAN',       8,  24,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (38, '2022-04-27', 'NAN',       5,  25,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (39, '2022-04-29', 'NAN',       2,  26,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (40, '2022-04-29', 'NAN',       9,  27,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (41, '2022-05-03', 'NAN',       5,  28,  2)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES (42, '2022-03-27', 'Attended',  4,   1, 15)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-01', 'Attended', 10,   6,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-02', 'Attended',  5,   8,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-02', 'Attended', 12,  10,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-03', 'Attended',  6,   7, 13)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-04', 'Attended',  4,  14,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-04', 'Attended', 12,  16,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-05', 'Attended',  4,  18,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-05', 'Attended', 11,  20,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-06', 'Attended', 10,  22,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-07', 'Attended',  7,  23,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-08', 'Attended',  1,  24,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-08', 'Attended',  5,  26,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-08', 'Attended',  8,  27,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-08', 'Attended', 11,  29,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-09', 'Attended',  1,  30,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-09', 'Attended',  9,  31,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended',  2,  32,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended',  5,  33,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended',  8,  34,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended', 12,  35,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-11', 'Attended',  6,  36,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',       1,  37,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',       5,  38,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',       8,  39,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',       4,  40, 23)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',       5,  41, 13)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',       6,  42, 12)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       2,  43,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       5,  44,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       9,  45,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-05-01', 'NAN',       1,  46,  3)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-05-03', 'NAN',       5,  47, 10)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-05-05', 'NAN',       2,  48, 11)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-03', 'Attended', 11,  11, 12)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-05', 'Attended',  1,   6, 13)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended', 10,  13, 14)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended',  5,  17, 15)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-11', 'Attended',  7,  24,  4)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',      13,  26,  4)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',      12,  33,  4)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',       3,  45,  4)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',      14,  46,  4)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-03', 'Attended',  3,   2,  5)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-04', 'Attended',  8,  22,  5)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-05', 'Attended',  7,  35,  5)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended',  5,  37,  5)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-10', 'Attended', 12,  39,  5)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',       1,  41,  5)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',       8,  42,  5)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',       8,  49,  5)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       2,  50,  5)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-01', 'Attended',  4,   1,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-02', 'Attended',  5,  17, 11)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-02', 'Attended', 10,  13, 14)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-03', 'Attended',  6,  12,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-04', 'Attended', 12,  16,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-05', 'Attended',  4,  18,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-06', 'Attended', 10,  22,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-08', 'Attended',  1,  24,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-08', 'Attended',  5,  26,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended',  5,  33,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended',  8,  34,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-11', 'Attended',  6,  36,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',       1,  37,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',       8,  39,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',       4,  40,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       2,  43,  6)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       5,  44, 20)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       9,  45, 20)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-05-01', 'NAN',       1,  46, 20)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-05-03', 'NAN',       5,  47, 20)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-05-05', 'NAN',       2,  48, 20)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-01', 'Attended',  4,   1, 21)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-02', 'Attended',  5,   8, 22)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-02', 'Attended', 12,  10, 23)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-03', 'Attended', 13,  12, 24)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-04', 'Attended',  9,  16, 25)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-05', 'Attended',  4,  18,  7)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-06', 'Attended', 10,  22,  7)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-08', 'Attended',  1,  24,  7)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-08', 'Attended',  5,  26,  7)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-29', 'Attended',  5,  33,  7)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-30', 'Attended', 11,  34,  7)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-11', 'Attended',  6,  36,  7)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',       1,  37,  7)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',      12,  39,  16)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',       4,  40,  16)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       2,  43,  17)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       5,  44,  18)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       9,  45,  18)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-05-01', 'NAN',       1,  46,  19)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-05-03', 'NAN',       5,  47,  19)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-05-05', 'NAN',       2,  48,  19)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-03', 'Attended', 11,   2,  8)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-05', 'Attended',  1,   3, 10)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended',  2,  13,  8)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended',  5,  17,  8)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-11', 'Attended',  7,  24,  8)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',      13,  26,  8)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',      12,  33,  8)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',       3,  45,  8)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-28', 'NAN',      14,  46,  8)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-29', 'Attended',  3,   1,  9)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-30', 'Attended',  8,  22,  9)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-03-31', 'Attended',  7,  35,  9)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended',  5,  37,  9)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-10', 'Attended', 10,  39,  22)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',       1,  41,  23)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-25', 'NAN',       8,  42,  24)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-27', 'NAN',       8,  49,  25)",
                "INSERT IGNORE INTO Appointment (id, date, attendance, startSlot, patientId, allocationId) VALUES ('2022-04-29', 'NAN',       2,  50,  26)", };
        for (String insertStatement : insertStatements) {
            st.addBatch(insertStatement);
        }
        st.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
    }

    private static void insertBranchTable() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        String[] insertStatements = {
                "INSERT IGNORE INTO Branch (id, name, address, telNo, receptionistId) VALUES (1, 'Klang Health Screening Centre', 'No.29, Jalan Bayu Tinggi 1A/KS6, Taman Bayu Tinggi, 41200 Klang, Selangor', '03-55699996', 1)",
                "INSERT IGNORE INTO Branch (id, name, address, telNo, receptionistId) VALUES (2, 'Cheras Health Screening Centre', 'No 37, 39, 41&43, Jalan 4/96A, Taman Cheras Makmur, Cheras 56100 Kuala Lumpur', '03-91309163', 2)",
                "INSERT IGNORE INTO Branch (id, name, address, telNo, receptionistId) VALUES (3, 'Seri Kembangan Health Screening Centre', 'No.1 & 3, Ground Floor, Jalan Besar Susur 1, 43300 Seri Kembangan, Selangor', '03-89599924', 3)",
                "INSERT IGNORE INTO Branch (id, name, address, telNo, receptionistId) VALUES (4, 'Kajang Health Screening Centre', 'No.40 & 41, Jalan Tukang, 43000 Kajang, Selangor', '03-87337433', 4)",
                "INSERT IGNORE INTO Branch (id, name, address, telNo, receptionistId) VALUES (5, 'Kudai Health Screening Centre', 'Tower 1, No.68, Jalan Pertama 1, Danga Utama Commercial Center, 81300 Skudai Johor Bahru', '07-5500323', 5)", };
        for (String insertStatement : insertStatements) {
            st.addBatch(insertStatement);
        }
        st.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
    }

    private static void insertDoctorTable() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        String[] insertStatements = {
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (1, 'Sutheaswari Thivy a/l Pragash', 'Thivy111')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (2, 'Nur Hajjah Anisah Nizam', 'Hajjah222')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (3, 'Nor Hazmeera binti Syahir', 'Hazmeera333')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (4, 'Saajid bin Irfaan', 'Saajid444')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (5, 'Tian Yang', 'Tianyang555')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (6, 'Gong Ming Yu', 'Mingyu666')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (7, 'Liao Qing', 'Liaoqing777')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (8, 'Sun Ming Zhu', 'Mingzhu888')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (9, 'Siraaj bin Jaarallah', 'Siraaj999')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (10, 'Haarith bin Maisoon', 'Haarith10')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (11, 'Felixia Kong Shei Kam', 'Felixia11')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (12, 'Sothinathan Goundar a/l Devaraj', 'Goundar12')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (13, 'Yu Yang', 'Yuyang13')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (14, 'Rena Lu', 'Renalu14')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (15, 'Karina Yong', 'Karina15')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (16, 'Sanjay Gupta', 'Sanjay16')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (17, 'Ken Jeong', 'Kenjeong17')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (18, 'Drew Pinsky', 'Pinsky18')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (19, 'Alexie Oon Shun Nau', 'Alexie19')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (20, 'Nor Asma binti Zuraiful', 'Zuraiful20')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (21, 'Nur Mira binti Syed Shah Haizan', 'Haizan21')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (22, 'R. G. Waythamoorthy', 'Waythamoorthy22')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (23, 'Anthony C. Griffin', 'Anthony23')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (24, 'Caroline McGuire', 'Caroline24')",
                "INSERT IGNORE INTO Doctor (id, name, password) VALUES (25, 'Kang Min', 'Kangmin25')", };
        for (String insertStatement : insertStatements) {
            st.addBatch(insertStatement);
        }
        st.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
    }

    private static void insertPatientTable() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        String[] insertStatements = {
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (1, 'Sannatasah a/l Kamal Lourdenadin', 'EC4744643', '6010-021 2543', '59, Jalan Air Itam, Bandar Bukit Sinar, 62033 Precinct 13, Putrajaya', 'Sannatasah111')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (2, 'Loh Pao Gu', '890503-01-5793', '6015-186 2389', 'Jln Kuching 2/42, SS67, 98569 Sri Aman, Sarawak', 'Paogu222')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (3, 'Nor Fathiah Saad binti Zubair Qayyum', 'N 1707843', '6015-497 5301', '4, Jln 69Y, Seksyen 67, 21164 Kuala Dungun, Terengganu', 'Fathiah333')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (4, 'Melinda Tern Kay Ooi', 'N 00144521', '603-3826 8587', '5, Jalan 2/6W, Taman Indah, 05568 Alor Setar, Kedah', 'Melinda444')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (5, 'D. Kathigasu', 'N 00477014', '6011-1297 3080', 'Jln Sultan Sulaiman 7/47E, USJ 17, 32718 Bidor, Perak', 'Kathigasu555')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (6, 'Hj Ridhuan Jumat', '880904-12-8743', '6015-226 6051', '50, Jalan Yap Ah Loy, Laman Tasik Puteri, 84908 Larkin, Johor Darul Tazim', 'Ridhuan666')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (7, 'Law Phong Jong', 'N 00474160', '6014-518 4462', 'Lot 71, Jalan 3/27, PJS4, 78209 Cheng, Melaka', 'Phong777')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (8, 'Ngeh An Hoong', 'N 1188324', '6081-67 6147', '3, Jln Ariffin 9, Bandar Kuchai, 47675 Port Klang, Selangor', 'Anhoong888')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (9, 'Tang Bung Gau', '941204-13-3569', '6015-238 095', '4, Jln Cinta 6, Batu Selamat, 32525 Parit, Perak', 'Bunggau999')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (10, 'K. Kundargal', 'E58728453', '6018-326 1950', 'No. 1Z-70, Jln Raja Abdullah 3/1, Medan Keramat, 21165 Bukit Payong, Terengganu', 'Kundargal10')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (11, 'Tew Tean Ti', 'E05160360', '606-049 0256', 'Lot 50, Jalan 42Y, Bandar Ria, 71671 Chembong, Negeri Sembilan Darul Khusus', 'Teanti11')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (12, 'Hang Bai Shok', '881114-06-5432', '6015-674 1559', '10, Jln 8, Taman Damansara, 62384 Precinct 8, Putrajaya', 'Baishok12')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (13, 'Nurul Salwa Azlansyhah', 'A7186475', '6019-869 9545', 'No. 870, Jln Maharajalela 7/5A, Bukit Ulu, 93253 Batu Niah, Sarawak', 'Azlansyhah13')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (14, 'Mohd Khoirul bin Che Ashraf', '780522-08-0080', '6015-143 2342', '421, Jalan Syed Putra 8, PJU1, 35187 Sungai Siput, Perak Darul Ridzuan', 'Khoirul14')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (15, 'Nur Weni Miskan binti Shohaimi', 'G39717034', '6016-563 0522', '3, Lorong 92L, Seri Tinggi, 34712 Changkat Jering, Perak', 'Shohaimi15')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (16, 'Noor Suhaida Nazarudin', 'E65078534', '606-368 6946', 'No. R-58-55, Jln 9/87H, Desa Tropika, 54063 Kampung Baru, Kuala Lumpur', 'Suhaida16')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (17, 'Asha a/l Shankar', 'G39406062', '6015-987 4537', '56, Jalan 3/2, Medan Pahlawan, 83223 Johor Lama, Johor Darul Tazim', 'Shankar17')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (18, 'Soo Goo Thian', 'G54838948 ', '6086-49 9817', '65, Jalan Perak, Bandar Timur, 06498 Changlun, Kedah Darul Aman', 'Goothian18')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (19, 'Noor Amrina Muzammil binti Irfan', '750907-03-7452', '6011-233 9122', 'No. 32, Lorong Rakyat 2/8E, USJ 29N, 64103 Bentong, Pahang', 'Amrina19')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (20, 'Thanuja a/l Selvaraju', '690320-08-4683', '6015-277 3977', '76, Jalan 5O, Bandar Jaya, 75851 Pekan Asahan, Melaka', 'Thanuja20')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (21, 'Shahrulnaim Hakimie bin Che Ramly', 'E3905107K', '6012-765 3146', 'No. 4W-73, Jln Perdana 9/8, Lembah Mulia, 84851 Pontian Kechil, Johor', 'Hakimie21')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (22, 'Kanagaraj Bamadhaj a/l Mahathir Naidu', 'E4701031B', '6015-160 2748', '7-4, Jalan Sempadan 1/57, Taman Ria, 65765 Panching, Pahang', 'Bamadhaj22')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (23, 'Kanagaraj a/l Devan Karathu', 'E5787905H', '6013-483 6663', 'Lot 8-8, Jalan 8K, Bandar Anggerik, 72976 Senawang, Negeri Sembilan Darul Khusus', 'Karathu23')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (24, 'Muhammed Afandy bin Nik Ihsan Rafizal', 'AA1126255', '606-587 1725', '20, Jalan 8/2P, Pangsapuri Maxwell, 02440 Mata Ayer, Perlis', 'Afandy24')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (25, 'Mohammad Wan Shahrizal bin Ady', '690903-05-3588', '607-571 836', 'D-18-02, Jln 4/7P, USJ 28, 23169 Kijal, Terengganu', 'Shahrizal25')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (26, 'Hailey Mok', '920605-11-6220', '6010-622 7211', 'No. 10, Jalan Ismail Abdul Rahman 10/7D, PJU72H, 84724 Mersing, Johor Darul Tazim', 'Hailey26')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (27, 'M. G. G.  Karamjit', 'A35811373', '6011-1721 3067', 'No. 8-9, Jalan 4/9, Bandar Bendahara, 88912 Tanjung Aru, Sabah', 'Karamjit27')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (28, 'Haji Adini Samsul binti Wazif', 'A 5085709', '6015-580 4336', '55, Jalan Kuching 9/38, Bandar Keramat, 53990 Karak, Pahang Darul Makmur', 'Samsul28')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (29, 'Sarjit Rishyakaran a/l Devaki Madhavan', '931230-09-5723', '6015-501 7663', 'No. J-23-24, Jalan Masjid 8/97W, Kampung Sentosa, 18140 Ketereh, Kelantan Darul Naim', 'Sarjit29')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (30, 'Lok Meg Bow', 'C1732608', '6015-587 7152', '1N-44, Lorong Maxwell 4, Bandar Pahlawan, 53303 Kepong, Kuala Lumpur', 'Megbow30')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (31, 'Muhammet Aizabi Ridzuan bin Zubir', '850604-08-3457', '6015-292 3309', '89, Jln 3, Puncak Ulu, 59398 Lake Gardens, KL', 'Aizabi31')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (32, 'Haran a/l Santokh', 'LH615098', '6015-390 0833', '6, Lorong Wan Kadir 4T, Bandar Kinrara, 62170 Precinct 12, Putrajaya', 'Haran32')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (33, 'Arulraj Kunalan a/l Sivarasa Pereira', 'A2074275', '6013-199 1900', '13, Jln Pudu Lama 7, Seksyen 97, 75788 Alor Gajah, Malacca', 'Arulraj33')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (34, 'Muhamed Che Dzulkarnaen Sanorhizam', '870909-07-23467', '6015-441 7253', '10, Jln Sempadan 2/3Z, Kondominium Puchong, 17322 Salor, Kelantan Darul Naim', 'Sanorhizam34')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (35, 'Thew Mau Men', 'A32888014', '6010-311 8511', 'No. 95, Lorong 5/93Q, Sri Tun Perak, 33559 Malim Nawar, Perak', 'Maumen35')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (36, 'Muhammad Haji Zul Johari bin Nik Daniel', 'A4689996', '6015-325 3482', '5G-73, Jln 5P, Kampung Aman, 25604 Gambang, Pahang', 'Daniel36')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (37, 'Nor Humaira Aznai binti Dolbahrin', 'A8300392', '6011-1315 2019', '38, Lorong Air Itam 2, Bandar Sungai Buaya, 65720 Kuang, Selangor Darul Ehsan', 'Humaira37')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (38, 'Sangeeta Gnanalingam a/l Surendran Kaveri', 'AT 552393', '6010-946 1787', 'No. 1, Jalan 5/73, Lembah Belakong, 86258 Segamat, Johor Darul Tazim', 'Sangeeta38')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (39, 'Mohd Syed Ajis bin Anwar Sulong', 'B 3044365', '606-919 0659', '8, Lorong 39I, Seksyen 90O, 32605 Changkat Jering, Perak Darul Ridzuan', 'Sulong39')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (40, 'V.  Vadaketh', 'X050098', '6015-604 4121', 'No. 3, Jalan Bukit Petaling 7, Ara Flora, 75058 Durian Tunggal, Melaka', 'Vadaketh40')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (41, 'B.  Baljit', 'X034935', '6016-990 0241', 'No. 6-9, Jalan Sultan Abdul Samad 6/6G, Petaling Duta, 54403 Setapak, Kuala Lumpur', 'Baljit41')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (42, 'Vellu a/l Serbegeth Marimuthu', '740808-01-6223', '6017-668 6183', '36, Jalan Bukit Bintang 2/1S, Seri Country Heights, 06912 Durian Burung, Kedah Darul Aman', 'Vellu42')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (43, 'Li Cheam Foo', '980905-22-8790', '604-320 9331', '167, Jalan Tan Cheng Lock 7, Alam Jaya, 57716 Setiawangsa, KL', 'Cheam43')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (44, 'Muhammad Hj Saidin bin Rusman', 'AA6342205', '606-305 9633', '86, Jalan Hang Tuah 3/5V, Bukit Bendahara, 17938 Wakaf Bharu, Kelantan', 'Rusman44')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (45, 'S. A. Renuga', '000122-12-3742', '603-0217 4442', '9, Jalan 5, Petaling Brickfields, 91276 Tambunan, Sabah', 'Renuga45')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (46, 'Kiraan a/l Kavi', 'M55210284', '607-229 2232', '47, Lorong 8P, Bandar Rahman, 01405 Sanglang, Perlis', 'Kiraan46')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (47, 'Lok Nin Her', 'M49334041', '603-8629 7242', '5D-09, Lorong U-Thant 14H, Taman Desa Kinrara, 36228 Bidor, Perak', 'Ninher47')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (48, 'Khairudin Daryusman bin Azlan', 'B9103971', '6015-680 7432', '6-1, Jalan 5, USJ 47, 87016 Kiamsam, Labuan', 'Khairudin48')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (49, 'Karnail a/l Yugendran', 'M79586826', '6019-644 3372', 'No. 73, Jalan Istana 7, Apartment Bestari, 18352 Wakaf Bharu, Kelantan', 'Karnail49')",
                "INSERT IGNORE INTO Patient (id, name, ic, phone, address, password) VALUES (50, 'Leung Lun Woon', 'A30930309', '606-488 0108', '621, Jalan 3/4, Bandar Baru Laksamana, 12619 George Town, Penang', 'Leung50')", };
        for (String insertStatement : insertStatements) {
            st.addBatch(insertStatement);
        }
        st.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
    }

	private static void insertReceptionistTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false);
		String[] insertStatements = {
				"INSERT IGNORE INTO Receptionist (id, name, password) VALUES (1, 'Aw Kung Bang', 'Kungbang111')",
				"INSERT IGNORE INTO Receptionist (id, name, password) VALUES (2, 'Liana Yaacup binti Che Jaferi', 'Liana222')",
				"INSERT IGNORE INTO Receptionist (id, name, password) VALUES (3, 'Nurul Hasya binti Syarafuddin', 'Hasya333')",
				"INSERT IGNORE INTO Receptionist (id, name, password) VALUES (4, 'Rakesh Devaser a/l Shree', 'Rakesh444')",
				"INSERT IGNORE INTO Receptionist (id, name, password) VALUES (5, 'Nor Janni binti Syed Tumiran', 'Janni555')", };
		for (String insertStatement : insertStatements) {
			st.addBatch(insertStatement);
		}
		st.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}

	private static void insertServiceTable() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		conn.setAutoCommit(false);
		String[] insertStatements = {
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (1, 'Heart Screening', 1799, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Resting ECG, Echocardiogram, Stress Test, Chest X-Ray, Ultrasound of Abdomen & Pelvis', 3)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (2, 'Stroke Screening', 2699, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Resting ECG, MRA Brain, Diet Counselling', 3)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (3, 'Lung Cancer Screening', 690, 'Low Dose CT Scan without Contrast', 1)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (4, 'Eye Screening', 259, 'History & Clinical Examination, Visual Acuity, Auto-Refraction, Slit Lamp Examination, Intraocular Pressure, Eye Dilation', 2)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (5, 'Oral Health Screening', 159, 'History & Clinical Examination, Oral Cancer Screening, Gum Health Profile, Decay Risk Profile', 1)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (6, 'Bone Screening', 800, 'History & Clinical Examination, DEXA Scan, Laboratory Test, Nutritional & Body Fat Assessment', 2)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (7, 'Blood Screening', 250, 'Full Blood Count, Urea, Creatinine, Uric Acid, Glucose Fasting, Hepatitis B Screening, Liver Function Profile, Lipid Profile, Urine FEME', 1)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (8, 'Colonoscopy', 2099, 'History & Clinical Examination, Blood Test, Ultrasound of Abdomen, Fibroscan, CT Abdomen, Bowel Cleansing, Stress Test', 3)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (9, 'Gastroscopy', 1500, 'History & Clinical Examination, Blood Test, Chest X-Ray, Ultrasound of Abdomen & Pelvis, CT Abdomen, Bowel Cleansing, Stress Test, H-Pylori Test', 3)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (10, 'Allergy Test', 999, 'Complete Hemogram, IgE Total Antibody', 1)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (11, 'Wellness Screeening', 1060, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Resting ECG, Pulmonary Function Test, Ultrasound of Abdomen & Pelvis, Diet Counselling', 2)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (12, 'Men Health Screening', 1588, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Resting ECG, Pulmonary Function Test, Ankle Brachial Pressure Index, Uroflowmetry and Residual Urine Volume, Chest X-Ray, Ultrasound of Abdomen & Pelvis, Diet Counselling', 4)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (13, 'Women Health Screening', 1999, 'History & Clinical Examination, Vision Test, Blood Pressure Screening, Blood Investigation, Urine FEME, Thin Prep, Resting ECG, Pulmonary Function Test, Ankle Brachial Pressure Index, Chest X-Ray, 3D Mammogram, Ultrasound of Breast, Abdomen & Pelvis, Bone Mineral Density Scan, Diet Counselling', 5)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (14, 'Pre-Marital Screening', 299, 'Blood Test including Full Blood Count, Peripheral Blood Film, Blood Grouping, Hepatitis B Screening, and HIV', 5)",
				"INSERT IGNORE INTO Service (id, name, price, description, timeSlotRequired) VALUES (15, 'Post-Covid Screening', 399, 'History & Clinical Examination, Anthropometry, Visual Acuity, Blood Test, Urine Test, Chest X-Ray, Lung Function Test', 5)", };
		for (String insertStatement : insertStatements) {
			st.addBatch(insertStatement);
		}
		st.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}

    /*
    // run me to test database setup!!!
    // only run this once
    // DatabaseSetupTest
    public static void main(String[] args) {
        DatabaseSetup.setupDatabaseIfNotExist();
    }*/
}