package org.griddynamics.dao;

import org.griddynamics.H2DbManager;
import org.griddynamics.entity.Company;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Data access object interface implementation
 * for h2 database
 */
public class CompanyH2DAO {

    // h2 database manager reference
    private final H2DbManager h2DbManager;

    /**
     * Default constructor
     * @param h2DbManager | Database connection manager to work with
     */
    public CompanyH2DAO(H2DbManager h2DbManager) {
        this.h2DbManager = h2DbManager;
    }

    /**
     * Database connection manager getter
     */
    public H2DbManager getH2DbManager() {
        return h2DbManager;
    }

    /**
     * Reads companies from database
     * @return List of companies
     */
    public List<Company> getCompanies() {
        List<Company> result = null;
        try (Statement statement = this.h2DbManager.getConnection().createStatement()) {
            // Executing query
            ResultSet data = statement.executeQuery("SELECT * FROM Company");

            // Reading companies
            result = new LinkedList<>();
            while (data.next()) {
                result.add(new Company(data.getString("NAME")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Returning as immutable list
        return Collections.unmodifiableList(result);
    }

    /**
     * Adds given company to database
     * @param company | Company to add
     */
    public void addCompany(Company company) {
        try (Statement statement = this.h2DbManager.getConnection().createStatement()){
            // Creating query
            String query = "INSERT INTO Company (NAME) " +
                           "VALUES ('" + company.getName() + "');";

            // Executing query
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
