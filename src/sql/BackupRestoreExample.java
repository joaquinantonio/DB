package sql;

import dbconfig.DatabaseConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class provides methods for backup and restore a MySQL or MariaDB database using command-line tools.
 */
public class BackupRestoreExample {

    /**
     * Main method to demonstrate backup and restore operations.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Backup specific database
        backupDatabase("mydb", "/Users/joaquinantonio/IDE/IdeaProjects/DB/src/backup_specific_db.sql");

        // Backup all databases
        backupAllDatabases("/Users/joaquinantonio/IDE/IdeaProjects/DB/src/backup_all.sql");

        // Restore specific database
        restoreDatabase("mydb", "/Users/joaquinantonio/IDE/IdeaProjects/DB/src/backup_specific_db.sql");
    }

    /**
     * Backup a specific database using the provided database name and backup file path.
     *
     * @param dbName     The name of the database to backup
     * @param backupPath The path where the backup file will be saved
     */
    private static void backupDatabase(String dbName, String backupPath) {
        try {
            // Construct the backup command
            String backupCommand = "mysqldump -u " + DatabaseConfig.USERNAME + " -p" + DatabaseConfig.PASSWORD + " --databases " + dbName + " -r " + backupPath;
            System.out.println("Backup Command: " + backupCommand);

            // Execute the backup command
            Process process = Runtime.getRuntime().exec(backupCommand);

            // Wait for the process to complete
            int processComplete = process.waitFor();
            if (processComplete == 0) {
                System.out.println("Backup created successfully!");
            } else {
                System.out.println("Could not create the backup");
                // Print the error stream
                printStream(process.getErrorStream());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Helper method to print the contents of a stream
    private static void printStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    /**
     * Backup all databases using the provided backup file path.
     *
     * @param backupPath The path where the backup file will be saved
     */
    private static void backupAllDatabases(String backupPath) {
        try {
            // Construct the backup command for all databases
            String backupCommand = "mysqldump -u" + DatabaseConfig.USERNAME + " -p" + DatabaseConfig.PASSWORD + " --all-databases -r " + backupPath;
            // Execute the backup command
            Process process = Runtime.getRuntime().exec(backupCommand);

            // Wait for the process to complete
            int processComplete = process.waitFor();
            if (processComplete == 0) {
                System.out.println("Backup for all databases created successfully!");
            } else {
                System.out.println("Could not create the backup for all databases");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restore a database from a backup file.
     *
     * @param dbName     The name of the database to restore
     * @param backupFile The path to the backup file
     */
    private static void restoreDatabase(String dbName, String backupFile) {
        try {
            // Construct the restore command
            String restoreCommand = String.format("mysql -u%s -p%s %s < %s",
                    DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD, dbName, backupFile);
            // Execute the restore command
            Process process = Runtime.getRuntime().exec(restoreCommand);

            // Wait for the process to complete
            int processComplete = process.waitFor();
            if (processComplete == 0) {
                System.out.println("Restore completed successfully!");
            } else {
                System.out.println("Could not restore the database");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
