package org.griddynamics.entity;

/**
 * Customer class implementation
 */
public class Customer {

    // Customer's id
    private final int id;

    // Customer's name
    private final String name;

    // Rented car reference
    private Car rentedCar;

    /**
     * car constructor
     * Creates customer with no id and given car
     * @param name | Name of customer
     * @param car  | Car to rent, can be null
     */
    public Customer(String name, Car car) {
        this.id = -1;
        this.name = name;
        this.rentedCar = car;
    }

    /**
     * id-car Customer constructor
     * Creates customer with given id and car
     * @param id   | Customer's database id
     * @param name | Name of customer
     * @param car  | Car to rent, can be null
     */
    public Customer(int id, String name, Car car) {
        this.id = id;
        this.name = name;
        this.rentedCar = car;
    }

    /**
     * id getter
     */
    public int getId() {
        return id;
    }

    /**
     * name getter
     */
    public String getName() {
        return name;
    }

    /**
     * Rented car getter
     */
    public Car getRentedCar() {
        return rentedCar;
    }

    /**
     * Rents given car
     * @param carToRent | Car to rent
     */
    public void rentCar(Car carToRent) {
        this.rentedCar = carToRent;
    }

    /**
     * toString overriding
     * @return Customer's string representation
     */
    @Override
    public String toString() {
        return name;
    }
}
