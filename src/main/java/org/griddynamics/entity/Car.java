package org.griddynamics.entity;

/**
 * Car class implementation
 */
public class Car {

    // Car's id
    private final int id;

    // Car's name
    private final String name;

    // Company reference
    private final Company owner;

    /**
     * Default constructor
     * @param name | Car's name
     * @param owner | Car's owner company
     */
    public Car(String name, Company owner) {
        this.id = -1;
        this.name = name;
        this.owner = owner;
    }

    /**
     * Car from database constructor
     * @param id | Car's database id
     * @param name | Car's name
     * @param owner | Car's owner (company)
     */
    public Car(int id, String name, Company owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    /**
     * Car's database id getter
     */
    public int getId() {
        return id;
    }

    /**
     * Car's name getter
     * @return Car's name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Car's owner company getter
     */
    public Company getOwner() {
        return owner;
    }

    /**
     * toString overriding
     * @return Car's string representation
     */
    @Override
    public String toString() {
        return name;
    }
}
