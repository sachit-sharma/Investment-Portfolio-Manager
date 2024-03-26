package ui;

import javax.swing.*;

public class TableRowFilter extends RowFilter {

    String searchText;

    // EFFECTS: creates a table row filter with the given search text
    public TableRowFilter(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public boolean include(Entry entry) {
        return entry.getStringValue(6).indexOf(searchText) >= 0;
    }
}
