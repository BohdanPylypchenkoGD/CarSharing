package org.griddynamics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to manage connection to
 * h2 database
 */
public class H2DbManager {

    // Instance reference
    private static H2DbManager instance;

    // Database path
    private static String dbPath;

    // Connection reference
    private final Connection connection;

    /**
     * Private constructor
     * @throws SQLException If can't connect to database
     * @throws ClassNotFoundException If h2 driver class is not present
     */
    private H2DbManager() throws SQLException,
                                 ClassNotFoundException {
        // Initializing driver
        Class.forName("org.h2.Driver");

        // Trying to create connection
        this.connection = DriverManager.getConnection(dbPath);

        // Setting autocommit
        connection.setAutoCommit(true);

        // Initializing database
        this.initializeDb();
    }

    /**
     * Initializes database driver and path
     * @param path | path to h2 database
     */
    public static void initializeDbPath(String path) {
        if (dbPath == null) {
            dbPath = path;
        }
    }

    /**
     * Singleton instance getter
     * @return instance of H2DbManager
     */
    public static H2DbManager getInstance() {
        try {
            // Checking if instance is not created
            if (instance == null) {
                instance = new H2DbManager();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Returning instance
        return instance;
    }

    /**
     * Connection getter
     * @return Connection to h2 database
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes connection to database
     */
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes database as it should be
     */
    private void initializeDb() {
        createCompanyTableIfNotExist();
        createCarTableIfNotExist();
    }

    /**
     * Ensures existence of COMPANY table
     */
    private void createCompanyTableIfNotExist() {
        try (Statement statement = this.connection.createStatement()) {
            // Creating query
            String query = "CREATE TABLE IF NOT EXISTS COMPANY (" +
                           "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                           "NAME VARCHAR(255) NOT NULL UNIQUE);";

            // Executing query
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ensures existence of CAR table
     */
    private void createCarTableIfNotExist() {
        try (Statement statement = this.connection.createStatement()) {
            // Creating query
            String query = "CREATE TABLE IF NOT EXISTS CAR (" +
                           "ID INT PRIMARY KEY AUTO_INCREMENT," +
                           "NAME VARCHAR(255) UNIQUE NOT NULL," +
                           "COMPANY_ID INT NOT NULL," +
                           "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID));";

            // Executing query
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
