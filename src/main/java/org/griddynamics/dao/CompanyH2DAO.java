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
 * for Company class
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
     * Reads companies from database
     * @return List of companies
     */
    public List<Company> getCompanies() {
        List<Company> result = null;
        try (PreparedStatement statement = this.h2DbManager
                                               .getConnection()
                                               .prepareStatement("SELECT * FROM COMPANY")) {
            // Executing query
            ResultSet data = statement.executeQuery();

            // Reading companies
            result = new LinkedList<>();
            while (data.next()) {
                result.add(new Company(data.getInt("ID"),
                                       data.getString("NAME")));
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
        try (PreparedStatement statement = this.h2DbManager
                                               .getConnection()
                                               .prepareStatement("INSERT INTO COMPANY (NAME) VALUES (?);")){
            // Setting name to desired value
            statement.setString(1, company.getName());

            // Executing
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
