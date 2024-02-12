package model;

public class StockPortfolio {

    int realisedProfit;


    // Creates a new empty stock portfolio
    public StockPortfolio() {

    }

    // REQUIRES: cost > 0
    // MODIFIES: this
    // EFFECTS:  adds a new stock to the  list of stocks in portfolio
    public void addStock(Stock stock) {

    }

    // REQUIRES: stock portfolio needs to be non-empty
    // EFFECTS:  returns the last stock purchase added to the portfolio
    public Stock getLastAddedStock() {
        return new Stock("X", 10, 50, "HealthCare");
    }

    // EFFECTS: returns the total number of items in the portfolio
    public int getNumItems() {
        return 0;

    }

    // EFFECTS: returns true if the Portfolio is empty
    public boolean isEmpty() {
        return false;
    }


    // REQUIRES: stock with given id is in portfolio, sellingPrice > 0
    // MODIFIES: this
    // EFFECTS:  removes  stock with given id from portfolio and adds net profit to realised profit
    public void removeStock(int id, double sellingPrice){
    }

    // EFFECTS: Returns the total value of all the stocks in the portfolio.
    // Each stock's value is the currentPrice * Qty
    public int getTotalPortfolioValue() {
        return 0;
    }

    // REQUIRES: Category is one of
    // Technology, HealthCare, Materials, Financial, Energy, Utilities, Consumer Staples
    // EFFECTS: Returns the total value of the stocks in the given category
    // Each stock's value = currentPrice * Qty
    public int getTotalAmountByCategory(String category) {
        return 0;
    }












}
