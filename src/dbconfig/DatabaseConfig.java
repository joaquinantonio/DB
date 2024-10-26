package dbconfig;

/**
 * Configuration class that provides the JDBC URL, username, and password
 * for connecting to a MariaDB or MySQL database.
 * Edit the JDBC_URL, USERNAME, and PASSWORD fields
 * according to your database configuration.
 * Note:
 * - For MariaDB, the JDBC URL format is "jdbc:mariadb://hostname:port/database_name".
 * - For MySQL, the JDBC URL format is "jdbc:mysql://hostname:port/database_name".
 */
public class DatabaseConfig {
    public static final String JDBC_URL = "jdbc:mariadb://localhost:3306/your_database";
    public static final String USERNAME = "your_username";
    public static final String PASSWORD = "your_pwd";
    public Exception t = new Exception();
}
