package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class StockPortfolioTest {

    private StockPortfolio testStockPortfolio;
    private Stock apple;
    private Stock tesla;
    private Stock pidilite;
    private Stock appleSecond;
    private Stock jnj;
    private Stock astraZeneca;

    @BeforeEach
    public void setup(){
        testStockPortfolio = new StockPortfolio();
        Stock.resetNextStockId();
        apple = new Stock("Apple", 100, 120, "Technology");
        tesla = new Stock("Tesla", 30, 75, "Technology");
        pidilite = new Stock("Pidilite", 20, 30, "Consumer Staples");
        appleSecond = new Stock("Apple", 50, 130.5, "Technology");
        jnj = new Stock("Johnson and Johnson", 100, 50.75, "Healthcare");
        astraZeneca = new Stock("AstraZeneca", 200, 60, "Healthcare");

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
        assertEquals(tesla, testStockPortfolio.getLastAddedStock());
    }

    @Test
    public void testGetNumItemsZero() {
        assertEquals(0, testStockPortfolio.getNumItems());

    }


    @Test
    public void testGetNumItemsThree() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        assertEquals(tesla, testStockPortfolio.getLastAddedStock());
        testStockPortfolio.addStock(pidilite);
        assertEquals(pidilite, testStockPortfolio.getLastAddedStock());
        assertEquals(3, testStockPortfolio.getNumItems());
    }

    @Test
    public void testIsEmptyTrueCase() {
        assertTrue(testStockPortfolio.isEmpty());
    }

    @Test
    public void testIsEmptyFalseCase() {
        testStockPortfolio.addStock(apple);
        assertFalse(testStockPortfolio.isEmpty());
    }

    @Test
    public void testTotalAmountInvestedSingleStock() {
        testStockPortfolio.addStock(apple);
        assertEquals(12000, testStockPortfolio.getTotalAmountInvested());
    }

    @Test
    public void testTotalAmountInvestedThree() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        assertEquals(14850, testStockPortfolio.getTotalAmountInvested());
    }

    @Test
    public void testTotalAmountInvestedFour() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(appleSecond);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        assertEquals(21375, testStockPortfolio.getTotalAmountInvested());
    }

    @Test
    public void testTotalAmountInvestedByCategory(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(appleSecond);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        testStockPortfolio.addStock(astraZeneca);
        testStockPortfolio.addStock(jnj);
        assertEquals(20775, testStockPortfolio.getTotalAmountInvestedByCategory("Technology"));
        assertEquals(600, testStockPortfolio.getTotalAmountInvestedByCategory(("Consumer Staples")));
        assertEquals(17075, testStockPortfolio.getTotalAmountInvestedByCategory("Healthcare"));
    }

    @Test
    public void testTotalAmountInvestedByCategorySmall(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(appleSecond);
        assertEquals(20775, testStockPortfolio.getTotalAmountInvestedByCategory("Technology"));
        assertEquals(0, testStockPortfolio.getTotalAmountInvestedByCategory("Healthcare"));
    }

    @Test
    public void testRemoveStockNoProfit(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        assertTrue(testStockPortfolio.removeStock(1, 120));
        assertEquals(1,testStockPortfolio.getNumItems());
        assertEquals(tesla, testStockPortfolio.getLastAddedStock());
        assertEquals(0, testStockPortfolio.getRealisedProfit());
    }

    @Test
    public void testRemoveStockProfit(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        assertTrue(testStockPortfolio.removeStock(1, 130));
        assertEquals(130, apple.getCurrentPrice());
        assertEquals(1,testStockPortfolio.getNumItems());
        assertEquals(1,testStockPortfolio.getNumItems());
        assertEquals(tesla, testStockPortfolio.getLastAddedStock());
        assertEquals(1000, testStockPortfolio.getRealisedProfit());
    }

    @Test
    public void testRemoveStockTwoProfit(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        testStockPortfolio.removeStock(1, 130);
        testStockPortfolio.removeStock(3, 50);
        assertEquals(1,testStockPortfolio.getNumItems());
        assertEquals(tesla, testStockPortfolio.getLastAddedStock());
        assertEquals(1400, testStockPortfolio.getRealisedProfit());
    }
    @Test
    public void testRemoveStockTwoNetZero(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        testStockPortfolio.removeStock(1, 122);
        testStockPortfolio.removeStock(3, 20);
        assertEquals(1,testStockPortfolio.getNumItems());
        assertEquals(tesla, testStockPortfolio.getLastAddedStock());
        assertEquals(0, testStockPortfolio.getRealisedProfit());
    }

    @Test
    public void testRemoveStockTwoLoss() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        assertTrue(testStockPortfolio.removeStock(1, 121));
        assertTrue(testStockPortfolio.removeStock(2, 60));
        assertEquals(1, testStockPortfolio.getNumItems());
        assertEquals(-350, testStockPortfolio.getRealisedProfit());
        assertEquals(pidilite, testStockPortfolio.getLastAddedStock());
        assertEquals(600, testStockPortfolio.getTotalAmountInvested());
        assertTrue(testStockPortfolio.removeStock(3, 40));
        assertEquals(-150, testStockPortfolio.getRealisedProfit());
        assertEquals(0, testStockPortfolio.getTotalAmountInvested());
    }

    @Test

    public void testRemoveStockAll() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(appleSecond);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        testStockPortfolio.removeStock(2, 50);
        testStockPortfolio.removeStock(4, 100);
        assertEquals(pidilite, testStockPortfolio.getLastAddedStock());
        assertEquals(2, testStockPortfolio.getNumItems());
        assertEquals(-2275, testStockPortfolio.getRealisedProfit());
    }

    @Test
    public void testUpdateCurrentPrice() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.updateCurrentPrice(1, 130);
        assertEquals(130, apple.getCurrentPrice());
    }

    @Test
    public void testUpdateCurrentPriceTwice() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.updateCurrentPrice(1, 130);
        assertEquals(130, testStockPortfolio.getLastAddedStock().getCurrentPrice());
        testStockPortfolio.updateCurrentPrice(1, 140);
        assertEquals(140, testStockPortfolio.getLastAddedStock().getCurrentPrice());
    }

    @Test
    public void testUpdateCurrentPriceInBigList() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        testStockPortfolio.updateCurrentPrice(1, 130);
        testStockPortfolio.updateCurrentPrice(2, 100);
        assertEquals(130, apple.getCurrentPrice());
        assertEquals(100, tesla.getCurrentPrice());
    }

    @Test
    public void testGetStockWithIdThirdMatches() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        assertEquals(pidilite,testStockPortfolio.getStockWithId(3));
    }

    @Test
    public void testGetStockWithIdFirstMatches() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        assertEquals(apple,testStockPortfolio.getStockWithId(1));
    }

    @Test
    public void testGetStockWithIdTestAll() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        assertEquals(apple,testStockPortfolio.getStockWithId(1));
        assertEquals(pidilite,testStockPortfolio.getStockWithId(3));
        assertEquals(tesla,testStockPortfolio.getStockWithId(2));
    }


    @Test
    public void testGetTotalPortfolioValueSingleUpdate() {
        testStockPortfolio.addStock(tesla);
        assertEquals(2250, testStockPortfolio.getTotalPortfolioValue());
        testStockPortfolio.addStock(pidilite);
        testStockPortfolio.updateCurrentPrice(3,400);
        assertEquals(10250, testStockPortfolio.getTotalPortfolioValue());
    }

    @Test
    public void testGetTotalPortfolioValueMultipleUpdate() {
        testStockPortfolio.addStock(apple);
        testStockPortfolio.updateCurrentPrice(1, 130);
        assertEquals(13000,testStockPortfolio.getTotalPortfolioValue());
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.updateCurrentPrice(2, 100);
        assertEquals(16000,testStockPortfolio.getTotalPortfolioValue());
        testStockPortfolio.addStock(pidilite);
        assertEquals(16600,testStockPortfolio.getTotalPortfolioValue());
    }


    @Test
    public void testValidIDTrueFirstTry(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        assertTrue(testStockPortfolio.validID(1));
    }

    @Test
    public void testValidIDTrueSecondTry(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        assertTrue(testStockPortfolio.validID(2));
    }

    @Test
    public void testValidIDTrueAtLast(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.addStock(pidilite);
        assertTrue(testStockPortfolio.validID(3));
    }


    @Test
    public void testValidIDFalse(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        assertFalse(testStockPortfolio.validID(3));
    }

    @Test
    public void getRealisedProfitZero(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        assertEquals(0, testStockPortfolio.getRealisedProfit());
    }

    @Test
    public void getRealisedProfitNonZero(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        testStockPortfolio.removeStock(1, 200);
        assertEquals(8000, testStockPortfolio.getRealisedProfit());
    }

    @Test
    public void getPortfolioTwo(){
        testStockPortfolio.addStock(apple);
        testStockPortfolio.addStock(tesla);
        assertEquals(apple,testStockPortfolio.getPorftolio().get(0));
        assertEquals(tesla,testStockPortfolio.getPorftolio().get(1));
    }











}
