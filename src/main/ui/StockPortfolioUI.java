package ui;

import model.Stock;
import model.StockPortfolio;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the GUI of the Stock Portfolio Application
public class StockPortfolioUI extends JFrame {

    private static final String JSON_STORE = "./data/portfolio.json";
    private static final String IMAGE_STORE = "./Images/stock_image.jpeg";
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final int TABLE_HEIGHT = 400;
    private static final int TABLE_WIDTH = 600;
    private static final int LEFT_PADDING = 70;
    private static final int ROWHEIGHT = 40;
    private StockPortfolio portfolio;
    private JTable table;
    private TableRowSorter tableRowSorter;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JPanel bottomPanel;
    private JPanel rightPanel;
    private JLabel realisedProfitLabel;
    private GridLayout rowlayout;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // MODIFIES: this;
    // EFFECTS:  creates an instance of the GUI of stock portfolio app and initialises the elements
    public StockPortfolioUI() {
        portfolio = new StockPortfolio();
        initialise();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        rowlayout = new GridLayout(1, 2);
    }

    // MODIFIES: this
    // EFFECTS: initialises the home screen with the menu, table, bottom and
    private void initialise() {
        setLayout(new BorderLayout());
        setTitle("Stock Portfolio App");
        setSize(WIDTH, HEIGHT);
        addMenu();
        addBottomPanel();
        addTable();
        addRightPanel();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS:  adds the bottom panel that displays realised profit to the main frame
    private void addBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(200, ROWHEIGHT));
        bottomPanel.setLayout(new BorderLayout());
        realisedProfitLabel = new JLabel("Realised Profit: " + portfolio.getRealisedProfit());
        bottomPanel.add(realisedProfitLabel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

    }


    // MODIFIES: this
    // EFFECTS: creates the menu bar and adds it to the frame
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu portfolioMenu = new JMenu("Portfolio");
        menuBar.add(portfolioMenu);

        addMenuItem(portfolioMenu, new AddStockAction());
        addMenuItem(portfolioMenu, new SellStockAction());

        menuBar.add(portfolioMenu);

        JMenu statsMenu = new JMenu("Stats");
        menuBar.add(statsMenu);
        JMenuItem assetAllocationMenu = new JMenuItem("View AssetAllocation");
        statsMenu.add(assetAllocationMenu);
        setJMenuBar(menuBar);

    }

    // MODIFIES: this
    // EFFECTS: creates a new JTable adds the table to the frame
    private void addTable() {

        DefaultTableModel model = createModel();
        tableRowSorter = new TableRowSorter(model);
        table = new JTable(model);
        table.setRowSorter(tableRowSorter);
        table.setSize(TABLE_WIDTH, TABLE_HEIGHT);
        table.setFont(new Font("SansSerif", Font.PLAIN, 15));
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", 0, 20));
        setColumnWidths();
        scrollPane = new JScrollPane(table);
        scrollPane.setSize(TABLE_WIDTH, TABLE_HEIGHT);
        this.add(scrollPane);

    }


    // MODIFIES: this
    // EFFECTS: sets column widths of the table
    private void setColumnWidths() {
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
    }

    // MODIFIES: this
    // EFFECTS: Creates and returns the table model by adding column names and row data
    private DefaultTableModel createModel() {

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Company");
        tableModel.addColumn("Stock Price");
        tableModel.addColumn("Current Price");
        tableModel.addColumn("Quantity Bought");
        tableModel.addColumn("Amount Invested");
        tableModel.addColumn("Category");

        for (Stock s: portfolio.getPortfolio()) {
            tableModel.addRow(s.featuresToArray());
        }
        return tableModel;
    }

    // MODIFIES: this
    // EFFECTS:  adds a panel to the right of main frame with search, load and save buttons
    public void addRightPanel() {

        rightPanel = new JPanel(new FlowLayout());
        rightPanel.setPreferredSize(new Dimension(200,  200));

        JLabel categoriesHeader = new JLabel(("Categories"));
        rightPanel.add(categoriesHeader);

        JTextField categoriesSearchField = new JTextField(15);
        JButton searchCategoriesButton = new JButton("Search Categories");
        searchCategoriesButton.setPreferredSize(new Dimension(150, 50));
        categoriesSearchField.setPreferredSize((new Dimension(200,50)));
        rightPanel.add(categoriesSearchField);
        rightPanel.add(searchCategoriesButton);
        rightPanel.add(getImage());
        this.add(rightPanel, BorderLayout.EAST);

        addSaveButton();
        searchCategoriesButton.addActionListener(new ActionListener() {
            @Override
            //MOFIFIES: this
            //EFFECTS: assigns a new filter to the table object to filter based on the text in JTextField
            public void actionPerformed(ActionEvent e) {
                String searchText = categoriesSearchField.getText();
                tableRowSorter.setRowFilter(new TableRowFilter(searchText));
            }
        });
    }

    // MODIFIES:  this
    // EFFECTS: adds the save button to the rightPanel
    private void addSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveListener());
        rightPanel.add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new LoadListener());
        rightPanel.add(loadButton);
    }


    // EFFECTS: returns the stock categories as an array
    public String[] getListOfStockCategories() {
        String[] categories  = {"Industrials","Technology", "HealthCare", "Materials", "Financial", "Energy",
                "Utilities",
                "Consumer Staples"};
        return categories;
    }

    // MODIFIES: this
    // EFFECTS Adds a menu item with the action to be taken when it is clicked
    public void addMenuItem(JMenu theMenu, AbstractAction action) {
        JMenuItem menuItem = new JMenuItem(action);
        theMenu.add(menuItem);
    }

    // EFFECTS: prompts user for string input until valid input is given and returns given input
    private String getStringInput(String message, String title) {
        String givenInput = "";
        while (givenInput.length() < 1) {
            givenInput = JOptionPane.showInputDialog(null, message, title,
            JOptionPane.QUESTION_MESSAGE);
            if (givenInput.length() < 1) {
                JOptionPane.showMessageDialog(null, "Please enter a valid name",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return givenInput;
    }

    // EFFECTS: prompts user to select a stock category and returns the String value of the selection
    private String getCategoryInput() {
        String [] categories = getListOfStockCategories();
        int categorySelection  = JOptionPane.showOptionDialog(null,
                "Select Category", "Enter Stock Category", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,null, categories, categories[0]);
        String actualCategory = parseCategory(categorySelection);
        return actualCategory;
    }

    // EFFECTS: prompts user give a positive integer and returns the given input
    private int getPositiveIntegerInput(String message, String title) {
        int givenInput = -1;
        while (givenInput <= 0) {
            try {
                givenInput = Integer.valueOf(JOptionPane.showInputDialog(null, message, title,
                        JOptionPane.QUESTION_MESSAGE));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (givenInput <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return givenInput;
    }



    // EFFECTS: gets double input from the user, displaying the given message and title.
    // displays error message if illegal input is given
    private double getPositiveDoubleInput(String message, String title) {
        double givenInput = -1;
        while (givenInput < 0) {
            try {
                givenInput = Double.valueOf(JOptionPane.showInputDialog(null, message, title,
                        JOptionPane.QUESTION_MESSAGE));
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (givenInput < 0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number","Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return givenInput;
    }

    // REQUIRES: 0 <= category <= 7
    // EFFECTS: Returns the corresponding Category String according to the selection
    private String parseCategory(int category) {
        if (category == 0) {
            return "Industrials";
        }
        if (category == 1) {
            return "Technology";
        }
        if (category == 2) {
            return "HealthCare";
        }
        if (category == 3) {
            return "Materials";
        }
        if (category == 4) {
            return "Financial";
        }
        if (category == 5) {
            return "Energy";
        }
        if (category == 6) {
            return "Utilities";
        } else {
            return "Consumer Staples";
        }
    }

    // EFFECTS: returns a JLabel with the stock image on it
    public JLabel getImage() {
        JLabel imageLabel = new JLabel();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(IMAGE_STORE));
        } catch (Exception e) {
            System.out.println("Could not read image");
        }
        imageLabel.setIcon(new ImageIcon(img));
        return imageLabel;

    }


    // Represents the actions to be taken when user wants to add stock
    private class AddStockAction extends AbstractAction {

        AddStockAction() {
            super("Add Stock");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = getStringInput("Name", "Enter company name");

            int quantity = getPositiveIntegerInput("Quantity Bought", "Enter Quantity Bought");

            double price = getPositiveDoubleInput("Cost Price", "Enter Cost Price of 1 stock");

            String actualCategory = getCategoryInput();

            Stock stockToBeAdded = new Stock(name, quantity, price, actualCategory);

            portfolio.addStock(stockToBeAdded);
            tableModel.addRow(stockToBeAdded.featuresToArray());
        }

    }

    // Represents the actions to be taken when user wants to sell a stock
    private class SellStockAction extends AbstractAction {

        private SellStockAction() {
            super("Sell Stock");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            int id = getPositiveIntegerInput("Enter ID of stock to sell", "ID");
            if (!portfolio.validID(id)) {
                JOptionPane.showMessageDialog(null, "No Such Stock Found","Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            double sellingPrice = getPositiveDoubleInput("Enter Selling Price", "Selling Price");
            portfolio.removeStock(id, sellingPrice);
            tableModel.removeRow(getRowIndex(id));
            JOptionPane.showMessageDialog(null, "Sold all units at " + sellingPrice,
                    "Successful", JOptionPane.INFORMATION_MESSAGE);

            realisedProfitLabel.setText("Realised Profit: " + portfolio.getRealisedProfit());
        }



        // REQUIRES: id belongs to a stock in the portfolio
        // EFFECTS:gets the index of the row which contains the stock with given id
        public int getRowIndex(int id) {
            int rowValue = 0;
            String idString = String.valueOf(id);
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(idString)) {
                    rowValue =  i;
                    return rowValue;
                }
            }
            return rowValue;
        }
    }

    // Represents the action to be performed when user wants to save to file
    private class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            savePortfolio();
        }

        // Reference: JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
        // EFFECTS: saves portfolio to file
        public void savePortfolio() {
            try {
                jsonWriter.open();
                jsonWriter.write(portfolio);
                jsonWriter.close();
                System.out.println("Saved " + "portfolio" + " to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // Represents the action taken when user wants to load from file
    private class LoadListener implements ActionListener {

        // MODIFIES: this
        //EFFECTS: loads the json data from file when user clicks the load button
        @Override
        public void actionPerformed(ActionEvent e) {
            loadPortfolio();
        }

        // Reference: JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
        // MODIFIES: this
        // EFFECTS: loads portfolio from file
        public void loadPortfolio() {
            try {
                portfolio = jsonReader.read();
                System.out.println("Loaded " + "portfolio" + " from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                tableModel.removeRow(i);
            }
            for (Stock s: portfolio.getPortfolio()) {
                tableModel.addRow(s.featuresToArray());
            }

            realisedProfitLabel.setText("Realised Profit: " + portfolio.getRealisedProfit());

        }



    }

}


