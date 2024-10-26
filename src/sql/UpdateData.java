package sql;

import dbconfig.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class connects to a MariaDB database and updates a record in a specified table based on the ID column.
 * JDBC is used to connect to the database, and PreparedStatement is used for executing the UPDATE query.
 */
public class UpdateData {

    /**
     * Main method to connect to the database and update a record in a specified table based on the ID column.
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
        String tableName = "mytable";
        String idColumn = "id";  // Assuming update based on ID
        String nameColumn = "name";

        // Data to update (modify as needed)
        int idToUpdate = 2;  // Replace with the ID of the record to update
        String newName = "Isabela";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Connect to MariaDB
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Prepare SQL statement with placeholders
            String sql = "UPDATE " + tableName + " SET " + nameColumn + " = ? WHERE " + idColumn + " = ?";
            statement = connection.prepareStatement(sql);

            // Set values for placeholders
            statement.setString(1, newName);
            statement.setInt(2, idToUpdate);

            // Execute the UPDATE statement
            int rowsAffected = statement.executeUpdate();

            // Check if any rows were updated
            if (rowsAffected > 0) {
                System.out.println("Record updated successfully!");
            } else {
                System.out.println("No records updated!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (statement, connection)
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
