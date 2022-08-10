package org.griddynamics.entity;

/**
 * Represents car sharing company
 */
public final class Company {

    // Id from database
    private final int id;

    // Company's name
    private final String name;

    /**
     * Default constructor
     * @param name | Company's name
     */
    public Company(String name) {
        this.id = -1;
        this.name = name;
    }

    /**
     * Company from database id constructor
     * @param id | id from database
     * @param name | company's name
     */
    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Database id getter
     */
    public int getId() {
        return id;
    }

    /**
     * name getter
     * @return Company's name as string
     */
    public String getName() {
        return name;
    }

    /**
     * toString overriding
     * @return Companies string representation
     */
    @Override
    public String toString() {
        return this.name;
    }
}
