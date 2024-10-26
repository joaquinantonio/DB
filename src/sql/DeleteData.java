package sql;

import dbconfig.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class connects to a MariaDB database and deletes a record from a specified table based on the ID column.
 * JDBC is used to connect to the database, and PreparedStatement is used for executing the DELETE query.
 */
public class DeleteData {

    /**
     * Main method to connect to the database and delete a record from a specified table based on the ID column.
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
        String idColumn = "id";  // Assuming deletion based on ID

        // ID of the record to delete (modify as needed)
        int idToDelete = 2;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Connect to MariaDB
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Prepare SQL statement with placeholder
            String sql = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";
            statement = connection.prepareStatement(sql);

            // Set value for placeholder
            statement.setInt(1, idToDelete);

            // Execute the DELETE statement
            int rowsAffected = statement.executeUpdate();

            // Check if any rows were deleted
            if (rowsAffected > 0) {
                System.out.println("Record deleted successfully!");
            } else {
                System.out.println("No records deleted!");
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
