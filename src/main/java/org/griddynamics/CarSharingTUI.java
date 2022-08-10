package org.griddynamics;

import org.griddynamics.dao.CarH2DAO;
import org.griddynamics.dao.CustomerH2DAO;
import org.griddynamics.entity.Car;
import org.griddynamics.entity.Company;
import org.griddynamics.dao.CompanyH2DAO;
import org.griddynamics.entity.Customer;

import java.util.ArrayList;
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

    // Customer DAO reference
    private final CustomerH2DAO customerH2DAO;

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
        this.customerH2DAO = new CustomerH2DAO(H2DbManager.getInstance());
    }

    /**
     * Program's main menu
     */
    public void mainMenu() {
        // Menu cycle
        while (true) {
            // Printing
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");

            // Reading customers from database
            List<Customer> customers = new ArrayList<>(customerH2DAO.getCustomers());

            // Reading
            String command = scanIn.nextLine();

            // Switch
            switch (command) {
                case "1":
                    System.out.println();
                    managerMenu();
                    break;
                case "2":
                    System.out.println();
                    listCustomers(customers);
                    break;
                case "3":
                    System.out.println();
                    addCustomer(customers);
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

    private void listCustomers(List<Customer> customers) {
        // Checking for empty list
        if (customers.size() == 0) {
            // Printing empty list case
            System.out.println("The customer list is empty!\n");

            // Returning
            return;
        }

        // Printing list
        System.out.println("Customer list:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i));
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
            System.out.println();
            return;
        }

        // Checking for bound
        if (indexOfChosen < 1 || indexOfChosen > customers.size()) {
            System.out.println("\nIndex out of bound!");
            return;
        }

        // Running company menu for chosen
        customerMenu(customers.get(indexOfChosen - 1));
        System.out.println();
    }

    private void customerMenu(Customer customer) {
        while(true) {
            // Printing
            System.out.println("\n1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");

            // Getting user command
            String command = scanIn.nextLine();
            // Switching
            switch (command) {
                case "1":
                    if (customer.getRentedCar() != null) {
                        System.out.println("\nYou've already rented a car!");
                        break;
                    }
                    // Getting list
                    List<Company> companies = companyH2DAO.getCompanies();

                    // Checking for empty list
                    if (companies.size() == 0) {
                        // Printing empty list case
                        System.out.println("\nThe company list is empty!");

                        // Returning
                        break;
                    }

                    // Printing list
                    System.out.println("\nChoose the company:");
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
                        break;
                    }

                    // Check for back
                    if (indexOfChosen == 0) {
                        break;
                    }

                    // Checking for bound
                    if (indexOfChosen < 1 || indexOfChosen > companies.size()) {
                        System.out.println("\nIndex out of bound!");
                        break;
                    }

                    // Printing cars
                    indexOfChosen--;
                    System.out.println("\nChoose a car:");
                    List<Car> cars = carH2DAO.getFreeCarsOfCompany(companies.get(indexOfChosen));
                    for (int i = 0; i < cars.size(); i++) {
                        System.out.println((i + 1) + ". " + cars.get(i));
                    }

                    // Getting car index from user
                    try {
                        indexOfChosen = Integer.parseInt(scanIn.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid command!\n");
                        break;
                    }

                    // Check for back
                    if (indexOfChosen == 0) {
                        break;
                    }

                    // Checking for bound
                    if (indexOfChosen < 1 || indexOfChosen > cars.size()) {
                        System.out.println("\nIndex out of bound!");
                        break;
                    }

                    // Renting car
                    customer.rentCar(cars.get(indexOfChosen - 1));
                    customerH2DAO.updateCustomer(customer);

                    // Printing
                    System.out.println("\nYou rented '" + customer.getRentedCar() + "'");
                    break;
                case "2":
                    System.out.println();
                    if (customer.getRentedCar() == null) {
                        System.out.println("You didn't rent a car!");
                    } else {
                        customer.rentCar(null);
                        customerH2DAO.updateCustomer(customer);
                        System.out.println("You've returned a rented car!");
                    }
                    break;
                case "3":
                    System.out.println();
                    if (customer.getRentedCar() == null) {
                        System.out.println("You didn't rent a car!");
                    } else {
                        System.out.println("You rented car:");
                        System.out.println(customer.getRentedCar());
                        System.out.println("Company:");
                        System.out.println(customer.getRentedCar().getOwner());
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }

    private void addCustomer(List<Customer> customers) {
        // Asking to enter customer's name
        System.out.println("Enter the customer name:");

        // Reading
        String name = scanIn.nextLine();

        // Creating new Customer
        Customer customer = new Customer(name, null);

        // Adding
        customers.add(customer);

        // Writing
        customerH2DAO.addCustomer(customer);

        // Printing
        System.out.println("The customer was added!\n");
    }
}
