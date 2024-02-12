package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StockPortfolioTest {

    private StockPortfolio testStockPortfolio;
    private Stock apple;
    private Stock tesla;
    private Stock gm;

    @BeforeEach
    public void setup(){
        testStockPortfolio = new StockPortfolio();
        apple = new Stock("Apple", 100, 120, "Technology");
        tesla = new Stock("Tesla", 30, 75, "Technology");
        gm = new Stock("General Motors", 20, 30, "Consumer Staples");
    }

    @Test
    public void testConstructor(){
        assertTrue(testStockPortfolio.isEmpty());
    }

    @Test
    public void testAddStockOnce() {
        testStockPortfolio.addStock(apple);
        assertEquals(apple, testStockPortfolio.getLastAddedStock());
        assertEquals(1, testStockPortfolio.getNumItems());
    }

    @Test
    public void testAddStockTwice() {
        testStockPortfolio.addStock(apple);
        assertEquals(apple, testStockPortfolio.getLastAddedStock());
        testStockPortfolio.addStock(tesla);
        assertEquals(tesla, testStockPortfolio.getLastAddedStock());
        assertEquals(2, testStockPortfolio.getNumItems());
    }

    @Test
    public void testGetNumItems() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        assertEquals(2, testStockPortfolio.getNumItems());
    }

    @Test
    public void testGetNumItemsThree() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(tesla);
        assertEquals(3, testStockPortfolio.getNumItems());
    }
}
