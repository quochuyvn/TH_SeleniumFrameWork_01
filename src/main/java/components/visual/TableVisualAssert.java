package components.visual;

import components.basic.Table;

/**
 * TableVisualAssert
 * Fluent assertions for Table component
 */
public class TableVisualAssert extends VisualAssert<TableVisualAssert> {

    private final Table table;

    public TableVisualAssert(Table table) {
        super(table);
        this.table = table;
    }

    /* ===== TABLE-SPECIFIC ASSERTIONS ===== */

    public TableVisualAssert hasRows(int expectedRowCount) {
        int actualRowCount = table.getRowCount();
        assert actualRowCount == expectedRowCount : "Row count mismatch. Expected: " + expectedRowCount + ", Actual: " + actualRowCount;
        return this;
    }

    public TableVisualAssert hasAtLeastRows(int minRowCount) {
        int actualRowCount = table.getRowCount();
        assert actualRowCount >= minRowCount : "Row count is less than expected";
        return this;
    }

    public TableVisualAssert hasAtLeastOneCellContent(String content) {
        boolean found = table.getRows().stream()
                .anyMatch(row -> {
                    try {
                        String cellContent = table.getCellContent(table.getRows().indexOf(row), 0);
                        return cellContent.contains(content);
                    } catch (Exception e) {
                        return false;
                    }
                });
        assert found : "No cell with content: " + content;
        return this;
    }

    public TableVisualAssert hasBordered() {
        return hasClass("table-bordered");
    }

    public TableVisualAssert isStriped() {
        return hasClass("table-striped");
    }

    public TableVisualAssert isHoverable() {
        return hasClass("table-hover");
    }

    public TableVisualAssert hasTableHead() {
        boolean hasHead = table.getElement().findElements(org.openqa.selenium.By.tagName("thead")).size() > 0;
        assert hasHead : "Table doesn't have thead";
        return this;
    }
}
