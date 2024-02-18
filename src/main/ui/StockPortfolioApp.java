package ui;

import model.Stock;
import model.StockPortfolio;


import java.util.ArrayList;
import java.util.Scanner;

// Represents the interface of the Stock Portfolio Application
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
        System.out.println("\tu -> update price of a stock");
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
        } else if (command.equals("u")) {
            updatePrice();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS:  lets user add a stock while specifying name, cost and category
    private void addToPortfolio() {
        String name = askForCompany();
        int qty = askForUnits();

        double cost = askForCost();
        String category = askForCategory();

        if (qty >= 0.0 && cost >= 0.0) {
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
        return getPositiveIntegerInput("Enter Number of Units Purchased");
    }

    // MODIFIES: this
    // EFFECTS: gets cost from user
    private double askForCost() {
        return getPositiveDoubleInput("Enter Cost of One Stock");
    }

    // MODIFIES: this
    // EFFECTS: gets  a valid integer input from user
    private int getPositiveIntegerInput(String message) {
        int givenValue = -1; // Forces into loop
        while (givenValue < 0) {
            System.out.println(message);
            String userInput = input.next();
            try {
                givenValue = Integer.valueOf(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Illegal Value Given");
            }
            if (givenValue < 0) {
                System.out.println("Please provide an integer greater than 0");
            }
        }
        return givenValue;
    }

    // MODIFIES: this
    // EFFECTS: gets  a valid integer input from user
    private double getPositiveDoubleInput(String message) {
        double givenValue = -1; // Forces into loop
        while (givenValue < 0) {
            System.out.println(message);
            String userInput = input.next();
            try {
                givenValue = Double.valueOf(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Illegal Value Given");
            }
            if (givenValue < 0) {
                System.out.println("Please provide a real number greater than 0");
            }
        }
        return givenValue;
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

        viewPortfolio();
        int sellingId = getValidStockID("Enter valid ID of stock to sell");
        double sellingPrice = getPositiveDoubleInput("Enter Selling Price");

        portfolio.removeStock(sellingId, sellingPrice);

        System.out.println("Sold all units of the given stock at $ " + sellingPrice);
        System.out.println("After this transaction you have\n");
        printAmountInvested();
        printTotalValue();
        printRealisedProfit();
    }

    // EFFECTS: Gets selling id from user
    private int getValidStockID(String message) {
        int sellingId = 0;
        while (!portfolio.validID(sellingId)) {
            sellingId = getPositiveIntegerInput(message);
        }
        return sellingId;
    }

    // EFFECTS: shows a display of all the stocks in the portfolio
    private void viewPortfolio() {
        System.out.printf("--------------------------------------------------------\n");
        System.out.printf("                 Your Portfolio                         \n");
        System.out.printf("--------------------------------------------------------\n");
        ;
        System.out.printf("|%5s | %-20s | %-18s |%-15s| %-18s| %-18s| %-25s| %n", "ID", "NAME", "COST PER SHARE",
                "QUANTITY", "AMOUNT INVESTED", "CURRENT VALUE", "CATEGORY");
        for (Stock s : portfolio.getPorftolio()) {
            System.out.printf("|%5d | %-20s | %-18.2f |%-15d| %-18.2f| %-18.2f| %-25s| %n", s.getId(),
                    s.getName(), s.getCostPrice(), s.getQuantity(), s.getAmountInvested(),
                    s.getCurrentValue(), s.getCategory());
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
        System.out.printf(" %-25s| %-20f| %n", "Consumer Staples",
                portfolio.getTotalAmountInvestedByCategory("Consumer Staples"));
    }

    //MODIFIES: this
    // EFFECTS: allows user to update the price of a stock
    private void updatePrice() {
        int id;
        double currentPrice;

        if (portfolio.isEmpty()) {
            System.out.println("You have not purchased any stocks");
        } else {
            viewPortfolio();
            id = getValidStockID("Enter Valid ID of Stock to update");
            currentPrice = getPositiveDoubleInput("Enter New Price");
            portfolio.updateCurrentPrice(id, currentPrice);
            double stockReturn = portfolio.getStockWithId(id).calculateTotalReturn();
            System.out.printf("Price Updated. The current return is %.2f. \n", stockReturn);
        }
        printAmountInvested();
        printTotalValue();
        printRealisedProfit();
    }
}
