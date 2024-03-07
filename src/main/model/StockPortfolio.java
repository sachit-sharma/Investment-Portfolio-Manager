package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// Represents a Stock Portfolio that contains an arbitrary number of Stocks, along with the realised profit.
// Realised profit refers to the profit already realised by selling stocks.

public class StockPortfolio implements Writable {

    double realisedProfit;
    LinkedList<Stock> portfolio = new LinkedList<>();

    // EFFECTS: Creates a new empty stock portfolio
    public StockPortfolio() {
        Stock.resetNextStockId();
    }

    // EFFECTS: returns realised profit
    public double getRealisedProfit() {
        return realisedProfit;
    }

    // EFFECTS: sets realised profit
    public void setRealisedProfit(double rp) {
        this.realisedProfit = rp;
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

    // EFFECTS: returns the list of stocks in portfolio
    public List<Stock> getPortfolio() {
        return portfolio;
    }

    // EFFECTS: returns true if the Portfolio is empty
    public boolean isEmpty() {
        return portfolio.isEmpty();
    }


    // EFFECTS:  removes  stock with given id from portfolio and adds net profit to realised profit
    // returns true if the stock was removed, false if there is no stock with the given id in the porftolio
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
            if (category.equals(s.getCategory())) {
                totalAmountInCategory += s.getAmountInvested();
            }
        }
        return totalAmountInCategory;
    }

    // EFFECTS: Returns stock with given id in the portfolio. Returns null if none was found
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


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("realisedProfit", realisedProfit);
        json.put("portfolio", stocksToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray stocksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Stock s : portfolio) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}

