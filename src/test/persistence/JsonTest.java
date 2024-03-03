package persistence;

import model.Stock;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code Based on JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
// Represents basic helper test methods for Json Writer and Reader
public class JsonTest {
    protected void checkStock(String name, int id, int qty, double costPrice, String category, double currentPrice, Boolean buying,
                               Stock stock) {
        assertEquals(name, stock.getName());
        assertEquals(category, stock.getCategory());
        assertEquals(qty, stock.getQuantity());
        assertEquals(costPrice, stock.getCostPrice());
        assertEquals(currentPrice, stock.getCurrentPrice());
        assertEquals(buying, !stock.isSold());
        assertEquals(id, stock.getId());
    }
}
