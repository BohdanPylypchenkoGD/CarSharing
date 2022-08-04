package org.griddynamics;

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

    // Path to database
    private final String dbPath;

    /**
     * Default constructor
     * @param dbPath | path to database
     */
    public CarSharingTUI(String dbPath) {
        // Initializing path
        this.dbPath = dbPath;
    }

    /**
     * Program's main menu
     */
    public void mainMenu() {
        // Creating Company's data access object instance
        H2DbManager.initializeDbPath(dbPath);
        CompanyH2DAO companyH2DAO = new CompanyH2DAO(H2DbManager.getInstance());

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
                    managerMenu(companyH2DAO);
                    break;
                case "0":
                    companyH2DAO.getH2DbManager().closeConnection();
                    System.exit(0);
                default:
                    System.out.println("Invalid command!");
            }
        }
    }

    /**
     * Manager's menu
     */
    private void managerMenu(CompanyH2DAO companyH2DAO) {
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
                    listCompanies(companyH2DAO);
                    System.out.println();
                    break;
                case "2":
                    System.out.println();
                    addCompanyMenu(companyH2DAO);
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
    private void listCompanies(CompanyH2DAO companyH2DAO) {
        // Getting list
        List<Company> companies = companyH2DAO.getCompanies();

        // Printing
        if (companies.size() == 0) {
            System.out.println("The company list is empty!");
        } else {
            //companies.forEach(System.out::println);
            for (int i = 0; i < companies.size(); i++) {
                System.out.println((i + 1) + ". " + companies.get(i));
            }
        }
    }

    /**
     * Menu for addCompany method
     */
    private void addCompanyMenu(CompanyH2DAO companyH2DAO) {
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
}
