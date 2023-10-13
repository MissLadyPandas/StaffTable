
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StaffDatabase {

    // constants for connecting to the database.
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/yourDatabaseName?useSSL=false"; // Database connection string.
    private static final String JDBC_USER = "root"; // MySQL user (default root).
    private static final String JDBC_PASSWORD = ""; // Empty password.

    public static void main(String[] args) throws Exception {
        // example usage of the methods. this is where the program starts executing.
        viewStaff("123456789"); // Viewing staff with ID '123456789'.
        insertStaff("987654321", "Doe", "John", "J", "123 Street", "SomeCity", "ST", "1234567890", "john.doe@example.com"); // Inserting a new staff record.
        updateStaff("987654321", "Doe", "Johnny", "J", "123 Street", "SomeCity", "ST", "1234567890", "johnny.doe@example.com"); // Updating the previously inserted staff record.
    }

    // method to view staff data for a given ID.
    public static void viewStaff(String id) throws Exception {
        // SQL query to fetch staff data by ID.
        String query = "SELECT * FROM Staff WHERE id = ?";

        // establishing a connection to the database and preparing the SQL query.
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id); 

            // executing the SQL query and storing the result.
            ResultSet rs = stmt.executeQuery();

            // loop through the results (if any).
            while (rs.next()) {
                // Printing each column's data.
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("Last Name: " + rs.getString("lastName"));
            }
        } 
    }

    // method to insert a new staff record into the database.
    public static void insertStaff(String id, String lastName, String firstName, String mi, String address,
                                   String city, String state, String telephone, String email) throws Exception {
        // sql query to insert a new staff record.
        String query = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // establishing a connection to the database and preparing the SQL query.
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // setting values for each column in the SQL query.
            stmt.setString(1, id);
            stmt.setString(2, lastName);
            stmt.setString(3, firstName);
            stmt.setString(4, mi);
            stmt.setString(5, address);
            stmt.setString(6, city);
            stmt.setString(7, state);
            stmt.setString(8, telephone);
            stmt.setString(9, email);

            // executing the SQL query to insert the new record.
            stmt.executeUpdate();
        } 
    }

    // method to update an existing staff record in the database.
    public static void updateStaff(String id, String lastName, String firstName, String mi, String address,
                                   String city, String state, String telephone, String email) throws Exception {
        // sQL query to update a staff record by ID.
        String query = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? WHERE id = ?";

        // establishing a connection to the database and preparing the SQL query.
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // setting new values for each column in the SQL query.
            stmt.setString(1, lastName);
            stmt.setString(2, firstName);
            stmt.setString(3, mi);
            stmt.setString(4, address);
            stmt.setString(5, city);
            stmt.setString(6, state);
            stmt.setString(7, telephone);
            stmt.setString(8, email);
            stmt.setString(9, id); 

            // executing the SQL query to update the record.
            stmt.executeUpdate();
        } 
    }
}
