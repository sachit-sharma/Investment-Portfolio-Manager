package persistence;

import model.Stock;
import model.StockPortfolio;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads stock portfolio from JSON data stored in file
// Code Based on JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads stock portfolio from file and returns it;
    // throws IOException if an error occurs reading data from file
    public StockPortfolio read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStockPortfolio(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Stock Portfolio from JSON object and returns it
    private StockPortfolio parseStockPortfolio(JSONObject jsonObject) {
        double realisedProfit = jsonObject.getDouble("realisedProfit");
        StockPortfolio sp = new StockPortfolio();
        sp.setRealisedProfit(realisedProfit);
        addStocks(sp,jsonObject);
        return sp;
    }

    // MODIFIES: sp
    // EFFECTS: parses thingies from JSON object and adds them to stock portfolio
    private void addStocks(StockPortfolio sp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("portfolio");
        for (Object json : jsonArray) {
            JSONObject nextStock = (JSONObject) json;
            addStock(sp, nextStock);
        }
    }

    // MODIFIES: sp
    // EFFECTS: parses stock from JSON object and adds it to portfolio
    private void addStock(StockPortfolio sp, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Integer id = jsonObject.getInt("id");
        Double costPrice = jsonObject.getDouble("costPrice");
        String category = jsonObject.getString("category");
        Double currentPrice = jsonObject.getDouble("currentPrice");
        Boolean buying = jsonObject.getBoolean("buying");
        int qty = jsonObject.getInt("qty");
        Stock stock = new Stock(name, qty, costPrice, category);
        stock.setCurrentPrice(currentPrice);
        sp.addStock(stock);
    }
}

