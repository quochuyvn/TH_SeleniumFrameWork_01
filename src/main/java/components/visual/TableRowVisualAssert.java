package components.visual;

import components.basic.TableRow;

/**
 * TableRowVisualAssert
 * Fluent assertions for TableRow component
 */
public class TableRowVisualAssert extends VisualAssert<TableRowVisualAssert> {

    private final TableRow tableRow;

    public TableRowVisualAssert(TableRow tableRow) {
        super(tableRow);
        this.tableRow = tableRow;
    }

    /* ===== TABLE ROW-SPECIFIC ASSERTIONS ===== */

    public TableRowVisualAssert hasCells(int expectedCellCount) {
        int actualCellCount = tableRow.getCellCount();
        assert actualCellCount == expectedCellCount : "Cell count mismatch. Expected: " + expectedCellCount + ", Actual: " + actualCellCount;
        return this;
    }

    public TableRowVisualAssert hasAtLeastCells(int minCellCount) {
        int actualCellCount = tableRow.getCellCount();
        assert actualCellCount >= minCellCount : "Cell count is less than expected";
        return this;
    }

    public TableRowVisualAssert hasCellContent(int cellIndex, String content) {
        String actualContent = tableRow.getCellContent(cellIndex);
        assert actualContent.equals(content) : "Cell content mismatch";
        return this;
    }

    public TableRowVisualAssert cellContentContains(int cellIndex, String text) {
        String actualContent = tableRow.getCellContent(cellIndex);
        assert actualContent.contains(text) : "Cell content doesn't contain: " + text;
        return this;
    }

    public TableRowVisualAssert isSelectable() {
        return hasClass("selectable");
    }

    public TableRowVisualAssert isHighlighted() {
        return hasClass("highlight");
    }
}
