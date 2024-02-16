package ui;

import model.Stock;
import model.StockPortfolio;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

// Stock Portfolio Application
public class StockPortfolioApp {
    private StockPortfolio portfolio;
    private Scanner input;

    // EFFECTS: Runs the stock portfolio tracker application
    public StockPortfolioApp() {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS:  processes user input for the tracker
    // References: TellerApp that was provided for reference.
    public void runTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initialises an empty portfolio
    private void init() {
        portfolio = new StockPortfolio(); // A new Stock Portfolio
        input = new Scanner(System.in);   // Inspiration from TellerApp
        input.useDelimiter("\n"); // Inspiration from TellerApp
    }

    // EFFECTS: displays the menu to the user
    // Inspiration from TellerApp
    private void displayMenu() {
        System.out.println("\nWelcome to My Stock Portfolio App!");
        System.out.println("\nPlease select from:");
        System.out.println("\ta -> add a stock");
        System.out.println("\ts -> sell a stock");
        System.out.println("\tv -> view portfolio");
        System.out.println("\tl -> show asset allocation");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    // Inspiration from TellerApp
    private void processCommand(String command) {
        if (command.equals("a")) {
            addToPortfolio();
        } else if (command.equals("s")) {
            removeFromPortfolio();
        } else if (command.equals("v")) {
            viewPortfolio();
        } else if (command.equals("l")) {
            showAssetAllocation();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void addToPortfolio() {
        String name = askForCompany();
        int qty = askForUnits();

        double cost = askForCost();
        String category = askForCategory();

        if (qty > 0.0 && cost > 0.0) {
            Stock stockToBeAdded = new Stock(name, qty, cost, category);
            portfolio.addStock(stockToBeAdded);
        }

        printAmountInvested();
        printTotalValue();
        printRealisedProfit();
    }

    // MODIFIES: this
    // EFFECTS: gets company name from user
    private String askForCompany() {
        String name = "";
        while (name.isEmpty()) {
            System.out.println("Enter Company Name");
            name = input.next();
        }
        return name;
    }

    // MODIFIES: this
    // EFFECTS: gets number of units from user
    private int askForUnits() {

        int qty = -1;
        while (qty < 0) {
            System.out.println("Enter number of units purchased");
            qty = input.nextInt();
            if (qty < 0) {
                System.out.println("Cannot process a negative quantity");
            }
        }
        return qty;
    }

    // MODIFIES: this
    // EFFECTS: gets cost from user
    private double askForCost() {
        double cost = -1;
        while (cost < 0) {
            System.out.println("Enter price of 1 unit ");
            cost = input.nextDouble();
            if (cost < 0) {
                System.out.println("Cannot process a negative cost");
            }
        }
        return cost;
    }

    // MODIFIES: this
    // EFFECTS: gets the category from user
    private String askForCategory() {
        System.out.println("Choose Category");
        String category = "Invalid";
        while (category == "Invalid") {
            displayCategories();
            String selection = input.next();
            category  = processCategory(selection);
            if (category == "Invalid") {
                System.out.println("Selection was invalid. Please select again");
            }
        }
        return category;
    }


    //EFFECTS: displays the categories of the companies
    // Industrials, Technology, HealthCare, Materials, Financial, Energy, Utilities, Consumer Staples
    private void displayCategories() {
        System.out.println("\nChoose from:");
        System.out.println("\ti -> Industrials");
        System.out.println("\tt -> Technology");
        System.out.println("\th -> HealthCare");
        System.out.println("\tm-> Materials");
        System.out.println("\tf-> Financial");
        System.out.println("\te-> Energy");
        System.out.println("\tu-> Utilities");
        System.out.println("\tc-> Consumer Staples");
    }
    // REQUIRES: selection has to be one of i, t, g, m , f, e, u, c
    // MODIFIES: this
    // EFFECTS: processes input for category selected

    private String processCategory(String selection) {
        if (selection.equals("i")) {
            return "Industrials";
        } else if (selection.equals("t")) {
            return "Technology";
        } else if (selection.equals("h")) {
            return "HealthCare";
        } else if (selection.equals("m")) {
            return "Materials";
        } else if (selection.equals("f")) {
            return "Financial";
        } else if (selection.equals("e")) {
            return "Energy";
        } else if (selection.equals("u")) {
            return "Utilities";
        } else if (selection.equals("c")) {
            return "Consumer Staples";
        } else {
            return "Invalid";
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a stock removal transaction
    private void removeFromPortfolio() {

        int sellingId = 0;
        int sellingPrice = -1;
        viewPortfolio();
        while (!portfolio.validID(sellingId)) {
            System.out.println("Enter a valid ID of Stock to sell");
            sellingId = input.nextInt();
        }
        while (sellingPrice < 0) {
            System.out.println("Enter selling price");
            sellingPrice = input.nextInt();
        }

        portfolio.removeStock(sellingId, sellingPrice);

        System.out.printf("Sold all units of the given stock at %d\n", portfolio.getStockWithId(sellingId), sellingPrice);
        System.out.printf("After this transaction you have\n");
        printAmountInvested();
        printTotalValue();
        printRealisedProfit();
    }

    // EFFECTS: shows a display of all the stocks in the portfolio
    private void viewPortfolio() {
        System.out.printf("--------------------------------------------------------\n");
        System.out.printf("                 Your Portfolio                         \n");
        System.out.printf("--------------------------------------------------------\n");
        ;
        System.out.printf("|%5s | %-20s | %-15s| %-25s| %n", "ID", "NAME", "AMOUNT INVESTED", "CATEGORY");
        for (Stock s : portfolio.getPorftolio()) {
            System.out.printf("|%5d | %-20s | %-15f| %-25s| %n", s.getId(),
                    s.getName(), s.getAmountInvested(), s.getCategory());
        }
    }

    // EFFECTS: prints total amount invested
    private void printAmountInvested() {
        System.out.printf("Total Amount Invested: $%.2f\n", portfolio.getTotalAmountInvested());
    }

    // EFFECTS: prints total current value invested
    private void printTotalValue() {
        System.out.printf("Total Value of Stocks: $%.2f\n", portfolio.getTotalPortfolioValue());
    }

    // EFFECTS: prints realised profit for portfolio
    private void printRealisedProfit() {
        System.out.printf("Realised Profit: $%.2f\n", portfolio.getRealisedProfit());
    }

    //EFFECTS: shows a display of the amount invested in all categories
    private void showAssetAllocation() {
        System.out.printf(" %-25s| %-20s| %n", "CATEGORY","Total AMOUNT INVESTED");
        System.out.printf(" %-25s| %-20f| %n", "Industrials",portfolio.getTotalAmountInvestedByCategory("Industrials"));
        System.out.printf(" %-25s| %-20f| %n", "Technology",portfolio.getTotalAmountInvestedByCategory("Technology"));
        System.out.printf(" %-25s| %-20f| %n", "HealthCare",portfolio.getTotalAmountInvestedByCategory("HealthCare"));
        System.out.printf(" %-25s| %-20f| %n", "Materials",portfolio.getTotalAmountInvestedByCategory("Materials"));
        System.out.printf(" %-25s| %-20f| %n", "Financial",portfolio.getTotalAmountInvestedByCategory("Financial"));
        System.out.printf(" %-25s| %-20f| %n", "Energy",portfolio.getTotalAmountInvestedByCategory("Energy"));
        System.out.printf(" %-25s| %-20f| %n", "Utilities",portfolio.getTotalAmountInvestedByCategory("Utilities"));
        System.out.printf(" %-25s| %-20f| %n", "Consumer Staples",portfolio.getTotalAmountInvestedByCategory("Consumer Staples"));
    }
}
