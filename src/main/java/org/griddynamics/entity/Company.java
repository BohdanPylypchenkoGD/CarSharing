package org.griddynamics.entity;

/**
 * Represents car sharing company
 */
public final class Company {

    // Company's name
    private final String name;

    /**
     * Default constructor
     * @param name | Company's name
     */
    public Company(String name) {
        this.name = name;
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
