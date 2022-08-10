package org.griddynamics.dao;

import org.griddynamics.H2DbManager;
import org.griddynamics.entity.Car;
import org.griddynamics.entity.Company;
import org.griddynamics.entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Data access object interface implementation
 * for h2 database
 * for Customer class
 */
public class CustomerH2DAO {

    // h2 database manager reference
    private final H2DbManager h2DbManager;

    /**
     * Default constructor
     * @param h2DbManager | Database connection manager to work with
     */
    public CustomerH2DAO(H2DbManager h2DbManager) {
        this.h2DbManager = h2DbManager;
    }

    /**
     * Reads customers from database
     * @return List of customers
     */
    public List<Customer> getCustomers() {
        LinkedList<Customer> result = null;
        try (PreparedStatement statement = this.h2DbManager
                .getConnection()
                .prepareStatement("SELECT CUSTOMER.ID, CUSTOMER.NAME, CAR.ID, CAR.NAME, COMPANY.ID, COMPANY.NAME " +
                                  "FROM CUSTOMER " +
                                  "LEFT JOIN CAR ON CUSTOMER.RENTED_CAR_ID = CAR.ID " +
                                  "LEFT JOIN COMPANY ON CAR.COMPANY_ID = COMPANY.ID;")) {
            // Executing query
            ResultSet data = statement.executeQuery();

            // Reading companies
            result = new LinkedList<>();
            int rentedCarId;
            Customer temp;
            while (data.next()) {
                // Adding with no car
                temp = new Customer(data.getInt("CUSTOMER.ID"),
                                    data.getString("CUSTOMER.NAME"),
                                    null);
                // Getting car name
                int carId = data.getInt("CAR.ID");

                // Checking for null
                if (carId != 0) {
                    // Not null -> rent a car by customer
                    temp.rentCar(new Car(carId,
                                         data.getString("CAR.NAME"),
                                         new Company(data.getInt("COMPANY.ID"),
                                                     data.getString("COMPANY.NAME"))));
                }

                // Adding
                result.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Returning as immutable list
        return Collections.unmodifiableList(result);
    }

    /**
     * Adds given customer to database
     * @param customer | Customer to add
     */
    public void addCustomer(Customer customer) {
        try (PreparedStatement statement = this.h2DbManager
                                               .getConnection()
                                               .prepareStatement("INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) VALUES (?, ?);")){
            // Setting params
            statement.setString(1, customer.getName());
            if (customer.getRentedCar() == null) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, customer.getRentedCar().getId());
            }

            // Executing
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates database info of given customer
     * @param customer
     */
    public void updateCustomer(Customer customer) {
        try (PreparedStatement statement = this.h2DbManager
                                               .getConnection()
                                               .prepareStatement("UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE CUSTOMER.ID = ?")){
            // Setting params
            if (customer.getRentedCar() == null) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, customer.getRentedCar().getId());
            }
            statement.setInt(2, customer.getId());

            // Executing
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
