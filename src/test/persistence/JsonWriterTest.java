package persistence;

import model.Stock;
import model.StockPortfolio;
import persistence.JsonWriter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
// Represents all tests for the JsonWriter
// Code Based on JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            StockPortfolio sp = new StockPortfolio();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            StockPortfolio sp = new StockPortfolio();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPortfolio.json");
            writer.open();
            writer.write(sp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPortfolio.json");
            sp = reader.read();
            assertEquals(0, sp.getRealisedProfit());
            assertEquals(0, sp.getNumItems());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            StockPortfolio sp = new StockPortfolio();
            sp.addStock(new Stock("apple", 10, 20, "Technology"));
            sp.addStock(new Stock("tesla", 20, 30.5, "Consumer Staples"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPortfolio.json");
            writer.open();
            writer.write(sp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPortfolio.json");
            sp = reader.read();
            assertEquals(0, sp.getRealisedProfit());
            List<Stock> stocks = sp.getPorftolio();
            assertEquals(2, stocks.size());
            checkStock("apple", 1, 10, 20, "Technology", 20, true, stocks.get(0));
            checkStock("tesla", 2, 20, 30.5, "Consumer Staples", 30.5, true, stocks.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}