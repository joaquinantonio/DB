package sql;

import dbconfig.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class connects to a MariaDB database and inserts a new record into a specified table.
 * JDBC is used to connect to the database, and PreparedStatement is used for executing the INSERT query.
 */
public class InsertData {

    /**
     * Main method to connect to the database and insert a new record into a specified table.
     *
     * @param args command line arguments (not used)
     * @throws SQLException if a database access error occurs
     */
    public static void main(String[] args) throws SQLException {

        // Replace with your connection details
        String jdbcUrl = DatabaseConfig.JDBC_URL;
        String username = DatabaseConfig.USERNAME;
        String password = DatabaseConfig.PASSWORD;

        // Database table and column names (replace as needed)
        String tableName = "new_table";
        String nameColumn = "name";
        String ageColumn = "age";

        // Data to insert (modify as needed)
        String name = "KhairAnas";
        int age = 12;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Connect to MariaDB
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Prepare SQL statement with placeholders for data
            String sql = "INSERT INTO " + tableName + " (" + nameColumn + ", " + ageColumn + ") VALUES (?, ?)";
            statement = connection.prepareStatement(sql);

            // Set values for placeholders
            statement.setString(1, name);
            statement.setInt(2, age);

            // Execute the INSERT statement
            int rowsAffected = statement.executeUpdate();

            // Check if any rows were inserted
            if (rowsAffected > 0) {
                System.out.println("Record inserted successfully!");
            } else {
                System.out.println("No records inserted!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (connection, statement)
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
