package sql;

import dbconfig.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; // Import for PreparedStatement
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class connects to a MariaDB database and creates a new database if it does not exist.
 * It also creates a new table in that database.
 * JDBC is used to connect to the database, and Statement and PreparedStatement objects are used for executing SQL queries.
 * It includes a helper method to check if a database exists.
 */
public class CreateDbTable {

    /**
     * Main method to connect to the database, create a new database if needed, and create a new table in that database.
     *
     * @param args command line arguments (not used)
     * @throws SQLException if a database access error occurs
     */
    public static void main(String[] args) throws SQLException {

        // Database name (replace as needed)
        String dbName = "demodb";

        // Table name and column definitions (replace as needed)
        String tableName = "new_table";
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "  // Assuming auto-increment ID
                + "name VARCHAR(50) NOT NULL, "
                + "age INT NOT NULL "
                + ")";

        Connection connection = null;
        Statement statement = null;

        try {
            // Access connection details from DatabaseConfig
            String jdbcUrl = DatabaseConfig.JDBC_URL;
            String username = DatabaseConfig.USERNAME;
            String password = DatabaseConfig.PASSWORD;

            // Connect to MariaDB
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Create a statement object (can use PreparedStatement for better security)
            statement = connection.createStatement();

            // Check if database exists (replace with your actual check logic for different databases)
            boolean databaseExists = checkDatabaseExists(connection, dbName);

            if (!databaseExists) {
                System.out.println("Database '" + dbName + "' does not exist. Creating...");
                statement.execute("CREATE DATABASE " + dbName);
                System.out.println("Database '" + dbName + "' has been created.");
            } else {
                System.out.println("Database '" + dbName + "' already exists.");
            }

            // Create the table (consider using PreparedStatement for table creation as well)
            statement.execute(createTableSql);
            System.out.println("Table '" + tableName + "' created (if it didn't exist).");

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

    /**
     * Helper method to check if a database exists.
     *
     * @param connection the database connection
     * @param dbName     the name of the database to check
     * @return true if the database exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    private static boolean checkDatabaseExists(Connection connection, String dbName) throws SQLException {
        String sql = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dbName);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
