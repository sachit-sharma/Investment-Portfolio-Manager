package model;

// Represents a Single Stock that is a part of the portfolio.
// Every stock will have a name,
// id, number of units of that stock purchased, cost price, current price a
// and whether it has been bought.

import org.json.JSONObject;

public class Stock {

    private String name;
    private static int nextStockId = 1;
    private int id;
    private int qty;
    private double costPrice;
    private String category;
    private double currentPrice;
    private Boolean buying;

    // REQUIRES: name is a non-empty string, costPrice > 0 and quantity > 0.
    // EFFECTS creates a new stock with the given name, the number of units purchased, cost price of a single stock
    // and stock category. The new stock has a unique transaction id, currentPrice is equal to cost price and
    // buying = true.
    // Category is one of
    // Industrials, Technology, HealthCare, Materials, Financial, Energy, Utilities, Consumer Staples

    public Stock(String name, int qty, double costPrice, String category) {

        this.id = nextStockId++;
        this.name = name;
        this.costPrice = costPrice;
        this.category = category;
        this.buying = true;
        this.currentPrice = costPrice;
        this.qty = qty;
    }

    // MODIFIES: this
    // EFFECTS: sets next stock id
    public static void resetNextStockId() {
        nextStockId = 1;
    }


    // EFFECTS: returns the stock name
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the stock transaction id
    public int getId() {
        return this.id;
    }

    // EFFECTS: returns the cost at which stock is bought
    public double getCostPrice() {
        return this.costPrice;
    }

    // EFFECTS: returns the quantity of the stock
    public int getQuantity() {
        return this.qty;

    }

    // EFFECTS: returns nextStockId
    public int getNextStockId() {
        return nextStockId;
    }

    //EFFECTS: returns !buying
    public boolean isSold() {
        return !this.buying;
    }

    // EFFECTS: returns the stock category
    public String getCategory() {
        return this.category;
    }


    // EFFECTS returns the current price of the stock
    public double getCurrentPrice() {
        return this.currentPrice;
    }

    // REQUIRES: currentPrice > 0
    // MODIFIES: this
    // EFFECTS: sets the current price of the stock to the given price
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;

    }

    // MODIFIES: this
    // EFFECTS: sets buying to false
    public void sell() {
        this.buying = false;

    }

    // EFFECTS: Returns the amount invested in that stock, ie  Qty * getCostPrice()
    public double getAmountInvested() {
        return this.costPrice * this.qty;

    }

    // EFFECTS: Returns the currentValue of that stock, ie  Qty * CurrentPrice
    public double getCurrentValue() {
        return this.currentPrice * this.qty;

    }

    // REQUIRES qty > 0
    // MODIFIES: this
    // EFFECTS: increases qty by unitsPurchased
    public void buyMore(int unitsPurchased) {
        this.qty = this.qty + unitsPurchased;
    }



    // EFFECTS: calculates the percentage return on the stock using formula:
    // percentage return = (current price  - original price)/ original price

    public double calculatePctReturn() {
        double pctReturn;
        pctReturn = ((getCurrentPrice() - getCostPrice()) * 100 / getCostPrice());
        return pctReturn;
    }

    // EFFECTS: calculates total monetary return on the stock where
    // total return = (currentPrice  - costPrice)* qty
    public double calculateTotalReturn() {
        return (this.getCurrentPrice() - this.getCostPrice()) * this.getQuantity();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("id", id);
        json.put("qty", qty);
        json.put("category", category);
        json.put("costPrice", costPrice);
        json.put("currentPrice", currentPrice);
        json.put("buying", buying);
        return json;
    }
}