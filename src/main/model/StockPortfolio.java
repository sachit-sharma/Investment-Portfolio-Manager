package model;


import java.util.LinkedList;

public class StockPortfolio {

    double realisedProfit;
    LinkedList<Stock> portfolio = new LinkedList<>();

    // Creates a new empty stock portfolio
    public StockPortfolio() {
    }

    public double getRealisedProfit() {
        return realisedProfit;
    }

    // REQUIRES: cost > 0
    // MODIFIES: this
    // EFFECTS:  adds a new stock to the end of  list of stocks in portfolio
    public void addStock(Stock stock) {
        portfolio.add(stock);

    }

    // REQUIRES: stock portfolio needs to be non-empty
    // EFFECTS:  returns the last stock purchase added to the portfolio
    public Stock getLastAddedStock() {
        return portfolio.getLast();
    }

    // EFFECTS: returns the total number of items in the portfolio
    public int getNumItems() {
        return portfolio.size();

    }

    // EFFECTS: returns true if the Portfolio is empty
    public boolean isEmpty() {
        return portfolio.isEmpty();
    }


    // REQUIRES: stock with given id is in portfolio, sellingPrice > 0
    // MODIFIES: this
    // EFFECTS:  removes  stock with given id from portfolio and adds net profit to realised profit
    public void removeStock(int id, double sellingPrice){
    }

    // EFFECTS: Returns the total current value of all the stocks in the portfolio.
    // Each stock's value is the currentPrice * Qty
    public double getTotalPortfolioValue() {
        return 0;
    }

    // EFFECTS: Returns the total cost of all the stocks in the portfolio.
    // Each stock's cost is costPrice * Qty
    public double getTotalAmountInvested() {
        double totalAmount = 0;
        for (Stock s: portfolio) {
            totalAmount += s.getAmountInvested();
        }
        return totalAmount;
    }

    // REQUIRES: Category is one of
    // Technology, HealthCare, Materials, Financial, Energy, Utilities, Consumer Staples
    // EFFECTS: Returns the total cost of the stocks in the given category
    // Each stock's value = costPrice * Qty
    public double getTotalAmountInvestedByCategory(String category) {
        double totalAmountInCategory = 0;
        for (Stock s: portfolio) {
            if (category == s.getCategory()) {
                totalAmountInCategory += s.getAmountInvested();
            }
        }
        return totalAmountInCategory;
    }












}
