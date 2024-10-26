package sql;

import dbconfig.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class to demonstrate database connection using JDBC.
 */
public class DBConnection {

    /**
     * Main method to establish a database connection.
     *
     * @param args command line arguments (not used)
     * @throws SQLException if a database access error occurs
     */
    public static void main(String[] args) throws SQLException {

        // Replace with your connection details
        String jdbcUrl = DatabaseConfig.JDBC_URL;
        String username = DatabaseConfig.USERNAME;
        String password = DatabaseConfig.PASSWORD;

        // Register JDBC Driver (optional with newer drivers)
        // Class.forName("org.mariadb.jdbc.Driver");  // Uncomment if needed

        // Establish connection
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        // Check if connection is successful
        if (connection != null) {
            System.out.println("Connection successful");
        } else {
            System.out.println("Connection failed");
        }

        // Close connection (omitted for brevity)
        connection.close();
    }
}