package org.griddynamics.dao;

import org.griddynamics.H2DbManager;
import org.griddynamics.entity.Car;
import org.griddynamics.entity.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Data access object interface implementation
 * for h2 database
 * for Car class
 */
public class CarH2DAO {

    // h2 database manager reference
    private final H2DbManager h2DbManager;

    /**
     * Default constructor
     * @param h2DbManager | Database connection manager to work with
     */
    public CarH2DAO(H2DbManager h2DbManager) {
        this.h2DbManager = h2DbManager;
    }

    /**
     * Returns all cars, those owner is given company.
     * @param company
     * @return List of cars of given company
     */
    public List<Car> getCarsOfCompany(Company company) {
        List<Car> result = null;
        try (PreparedStatement statement = this.h2DbManager
                                               .getConnection()
                                               .prepareStatement("SELECT * FROM CAR WHERE COMPANY_ID = ?")) {
            // Setting desired COMPANY_ID value
            statement.setInt(1, company.getId());

            // Executing query
            ResultSet data = statement.executeQuery();

            // Reading cars
            result = new LinkedList<>();
            while (data.next()) {
                result.add(new Car(data.getInt("ID"),
                                   data.getString("NAME"),
                                   company));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Returning as immutable list
        return Collections.unmodifiableList(result);
    }

    /**
     * Adds car to CAR database
     * @param car | Car instance to add
     */
    public void addCar(Car car) {
        try (PreparedStatement statement = this.h2DbManager
                                               .getConnection()
                                               .prepareStatement("INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?);")) {
            // Setting values
            statement.setString(1, car.getName());
            statement.setInt(2, car.getOwner().getId());

            // Executing query
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
