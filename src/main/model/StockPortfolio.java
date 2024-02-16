package model;


import java.util.LinkedList;
import java.util.List;

// Represents a Stock Portfolio that contains an arbitrary number of Stocks
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

    public List<Stock> getPorftolio() {
        return portfolio;
    }

    // EFFECTS: returns true if the Portfolio is empty
    public boolean isEmpty() {
        return portfolio.isEmpty();
    }


    // REQUIRES: stock with given id is in portfolio, sellingPrice > 0
    // MODIFIES: this
    // EFFECTS:  removes  stock with given id from portfolio and adds net profit to realised profit
    // returns true if the stock was removed
    public boolean removeStock(int id, double sellingPrice) {
        double stockProfit = 0;
        for (Stock s: portfolio) {
            if (id == s.getId()) {
                s.setCurrentPrice(sellingPrice);
                stockProfit = s.calculateTotalReturn();
                realisedProfit += stockProfit;
                portfolio.remove(s);
                return true;
            }
        }
        return false;
    }


    // EFFECTS: Returns the total current value of all the stocks in the portfolio.
    // Each stock's value is the currentPrice * Qty
    public double getTotalPortfolioValue() {
        double totalValue = 0;
        for (Stock s: portfolio) {
            totalValue += s.getCurrentPrice() * s.getQuantity();
        }
        return totalValue;
    }

    // REQUIRES: stock with given id is present in portfolio, currentPrice > 0
    // MODIFIES: this
    // EFFECTS: updates the currentPrice of the stock with given id
    public void updateCurrentPrice(int id, double currentPrice) {
        getStockWithId(id).setCurrentPrice(currentPrice);
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
    // Industrials, Technology, HealthCare, Materials, Financial, Energy, Utilities, Consumer Staples
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

    // REQUIRES: Stock with given id is in the portfolio
    // EFFECTS: Returns stock with given id in the portfolio
    public Stock getStockWithId(int id) {
        for (Stock s: portfolio) {
            if (id == s.getId()) {
                return s;
            }
        }
        return null;
    }

    // EFFECTS: Returns true if stock with given id in the portfolio
    public boolean validID(int id) {
        for (Stock s: portfolio) {
            if (id == s.getId()) {
                return true;
            }
        }
        return false;
    }











}
