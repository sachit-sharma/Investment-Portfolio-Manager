package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockTest {

    private Stock testStock;

    @BeforeEach
    public void setup() {
        testStock = new Stock("Apple", 10, 180.5, "Technology");
    }

    @Test
    public void testConstructor(){
        assertEquals("Apple",testStock.getName());
        assertEquals(180.5, testStock.getCostPrice());
        assertEquals("Technology", testStock.getCategory());
        assertEquals(10, testStock.getQuantity());
        assertEquals(180.5, testStock.getCurrentPrice());
        assertFalse(testStock.isSold());
    }

    @Test
    public void testBuyMore(){
        testStock.buyMore(20);
        assertEquals(30, testStock.getQuantity());
    }

    @Test
    public void testGetCostPrice(){
        assertEquals(1805, testStock.getAmountInvested());
    }

    @Test
    public void testSell() {
        testStock.sell();
        assertTrue(testStock.isSold());
    }

    @Test
    public void testGetCurrentValue() {
        testStock.setCurrentPrice(100);
        assertEquals(1000, testStock.getCurrentValue());
    }

    @Test
    public void testCalculatePctReturn() {
        testStock.setCurrentPrice(361);
        assertEquals(100, testStock.calculatePctReturn());
    }

    @Test
    public void testCalculateTotalReturn() {
        testStock.setCurrentPrice(361);
        assertEquals(1805, testStock.calculateTotalReturn());
    }

    @Test
    public void testResetId() {
        assertEquals(2, testStock.getNextStockId());
        Stock.resetNextStockId();
        assertEquals(1, testStock.getNextStockId());
    }
}

