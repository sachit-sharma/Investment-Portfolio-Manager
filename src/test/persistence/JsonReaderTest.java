package persistence;

import model.Stock;
import model.StockPortfolio;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Represents all tests for the JsonReader;
// Code Based on JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            StockPortfolio sp = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStockPortfolio.json");
        try {
            StockPortfolio sp = reader.read();
            assertEquals(0, sp.getRealisedProfit());
            assertEquals(0, sp.getNumItems());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStockPortfolio.json");
        try {
            StockPortfolio sp = reader.read();
            assertEquals(100.5, sp.getRealisedProfit());
            List<Stock> stocks= sp.getPortfolio();
            assertEquals(2, stocks.size());
            checkStock("apple", 1, 20, 14.5, "Technology", 13.5, true, stocks.get(0));
            checkStock("tesla", 2, 20, 100, "Technology", 200, true, stocks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}