package ui;

import javax.swing.*;

// Represents the RowFilter used to filter rows based on category
public class TableRowFilter extends RowFilter {

    private String searchText;

    // EFFECTS: creates a table row filter with the given search text
    public TableRowFilter(String searchText) {
        this.searchText = searchText;
    }

    @Override
    // EFFECTS: returns true if the categories column of a row contains the given search text
    public boolean include(Entry entry) {
        return entry.getStringValue(6).indexOf(searchText) >= 0;
    }
}
