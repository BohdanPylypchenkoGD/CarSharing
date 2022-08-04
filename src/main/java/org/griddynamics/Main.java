package org.griddynamics;

import java.sql.Connection;

/**
 * Program entry class
 */
public class Main {

    private static String getDbPath(String[] args) {
        // Checking for args count
        if (args.length != 2) {
            System.out.println("Wrong argument count!");
            System.exit(1);
        }

        // Checking for database filename argument
        if (!"-databaseFileName".equals(args[0])) {
            System.out.println("Wrong argument 0");
            System.exit(1);
        }

        // returning
        return "jdbc:h2:./h2db/" + args[1];
        //return "jdbc:h2:./Car sharing/task/src/carsharing/db/" + args[1];
        //return "jdbc:h2:./src/carsharing/db/" + args[1];
    }

    /**
     * Entry point
     * @param args
     */
    public static void main(String[] args) {
        // Getting db path
        String dbPath = getDbPath(args);

        // Creating menu
        CarSharingTUI menu = new CarSharingTUI(dbPath);

        // Running
        menu.mainMenu();
    }
}