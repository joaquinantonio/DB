package sql;

import dbconfig.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class connects to a MariaDB database and reads the most recently inserted record from a specified table.
 * JDBC is used to connect to the database, and Statement is used for executing the SELECT query.
 */
public class ReadData {

    /**
     * Main method to connect to the database and read the most recently inserted record from a specified table.
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
        String idColumn = "id"; // Assuming ID is auto-incremented after insert
        String nameColumn = "name";
        String ageColumn = "age"; // Add the age column name

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Connect to MariaDB
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Create a statement object
            statement = connection.createStatement();

            // Write the SQL query to retrieve recently inserted data
            // Assuming auto-increment on ID, retrieve the maximum ID
            String sql = "SELECT id, name, age FROM " + tableName + " WHERE " + idColumn + " = (SELECT MAX(" + idColumn + ") FROM " + tableName + ")";

            // Execute the query and get the results
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                int id = resultSet.getInt(idColumn);
                String name = resultSet.getString(nameColumn);
                int age = resultSet.getInt(ageColumn);

                System.out.println("Recently Inserted Data:");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            } else {
                System.out.println("No data found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (result set, statement, connection)
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
