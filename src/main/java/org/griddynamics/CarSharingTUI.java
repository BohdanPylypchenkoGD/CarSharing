package org.griddynamics;

import org.griddynamics.dao.CarH2DAO;
import org.griddynamics.entity.Car;
import org.griddynamics.entity.Company;
import org.griddynamics.dao.CompanyH2DAO;

import java.util.List;
import java.util.Scanner;

/**
 * Menu for car sharing program
 */
public class CarSharingTUI {

    // stdin scanner
    private final Scanner scanIn = new Scanner(System.in);

    // Company DAO reference
    private final CompanyH2DAO companyH2DAO;

    // Car DAO reference
    private final CarH2DAO carH2DAO;

    /**
     * Default constructor
     * @param dbPath | path to database
     */
    public CarSharingTUI(String dbPath) {
        // Initializing path
        H2DbManager.initializeDbPath(dbPath);

        // Creating DAO
        this.companyH2DAO = new CompanyH2DAO(H2DbManager.getInstance());
        this.carH2DAO = new CarH2DAO(H2DbManager.getInstance());
    }

    /**
     * Program's main menu
     */
    public void mainMenu() {
        // Menu cycle
        while (true) {
            // Printing
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");

            // Reading
            String command = scanIn.nextLine();

            // Switch
            switch (command) {
                case "1":
                    System.out.println();
                    managerMenu();
                    break;
                case "0":
                    H2DbManager.getInstance().closeConnection();
                    System.exit(0);
                default:
                    System.out.println("Invalid command!");
            }
        }
    }

    /**
     * Manager's menu
     */
    private void managerMenu() {
        while (true) {
            // Printing
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");

            // Reading command
            String command = scanIn.nextLine();

            // Switch
            switch (command) {
                case "1":
                    System.out.println();
                    listCompanies();
                    System.out.println();
                    break;
                case "2":
                    System.out.println();
                    addCompanyMenu();
                    System.out.println();
                    break;
                case "0":
                    System.out.println();
                    return;
                default:
                    System.out.println("\nInvalid command!\n");
            }
        }
    }

    /**
     * Menu for getCompanies method
     */
    private void listCompanies() {
        // Getting list
        List<Company> companies = companyH2DAO.getCompanies();

        // Checking for empty list
        if (companies.size() == 0) {
            // Printing empty list case
            System.out.println("The company list is empty!");

            // Returning
            return;
        }

        // Printing list
        System.out.println("Choose the company:");
        for (int i = 0; i < companies.size(); i++) {
            System.out.println((i + 1) + ". " + companies.get(i));
        }
        System.out.println("0. Back");

        // Reading user's command
        int indexOfChosen;
        try {
            indexOfChosen = Integer.parseInt(scanIn.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid command!\n");
            return;
        }

        // Check for back
        if (indexOfChosen == 0) {
            return;
        }

        // Checking for bound
        if (indexOfChosen < 1 || indexOfChosen > companies.size()) {
            System.out.println("\nIndex out of bound!");
            return;
        }

        // Running company menu for chosen
        companyMenu(companies.get(indexOfChosen - 1));
    }

    /**
     * Menu for addCompany method
     */
    private void addCompanyMenu() {
        // Asking to enter name
        System.out.println("Enter the company name:");

        // Getting name
        String name = scanIn.nextLine();

        // Creating company
        Company company = new Company(name);

        // Adding company
        companyH2DAO.addCompany(company);

        // Printing
        System.out.println("The company was created!");
    }

    /**
     * Menu for company
     */
    private void companyMenu(Company company){
        // Printing name
        System.out.println("\n'" + company.toString() + "' company");

        // Endless cycle
        while(true) {
            // Printing menu
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");

            // Reading command
            String command = scanIn.nextLine();

            // Switch
            switch (command) {
                case "1":
                    System.out.println();
                    listCars(company);
                    System.out.println();
                    break;
                case "2":
                    System.out.println();
                    addCarMenu(company);
                    System.out.println();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\nInvalid command!\n");
            }
        }
    }

    private void listCars(Company company) {
        // Getting list
        List<Car> cars = carH2DAO.getCarsOfCompany(company);

        // Checking for empty list
        if (cars.size() == 0) {
            // Printing empty list case
            System.out.println("The car list is empty!");

            // Returning
            return;
        }

        // Printing list
        System.out.println("Car list:");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ". " + cars.get(i));
        }
    }

    private void addCarMenu(Company company) {
        // Asking to enter name
        System.out.println("Enter the car name:");

        // Getting name
        String name = scanIn.nextLine();

        // Creating company
        Car car = new Car(name, company);

        // Adding company
        carH2DAO.addCar(car);

        // Printing
        System.out.println("The car was created!");
    }
}
