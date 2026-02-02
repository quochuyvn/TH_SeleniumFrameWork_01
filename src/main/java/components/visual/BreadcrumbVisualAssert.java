package components.visual;

import components.basic.Breadcrumb;

/**
 * BreadcrumbVisualAssert
 * Fluent assertions for Breadcrumb component
 */
public class BreadcrumbVisualAssert extends VisualAssert<BreadcrumbVisualAssert> {

    private final Breadcrumb breadcrumb;

    public BreadcrumbVisualAssert(Breadcrumb breadcrumb) {
        super(breadcrumb);
        this.breadcrumb = breadcrumb;
    }

    /* ===== BREADCRUMB-SPECIFIC ASSERTIONS ===== */

    public BreadcrumbVisualAssert hasItems(int expectedCount) {
        int actualCount = breadcrumb.getItems().size();
        assert actualCount == expectedCount : "Breadcrumb items count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount;
        return this;
    }

    public BreadcrumbVisualAssert hasItemWithText(String text) {
        boolean found = breadcrumb.getItems().stream()
                .anyMatch(item -> item.getText().equals(text));
        assert found : "Breadcrumb item not found: " + text;
        return this;
    }

    public BreadcrumbVisualAssert currentPathIs(String expectedPath) {
        String actualPath = breadcrumb.getCurrentPath();
        assert actualPath.equals(expectedPath) : "Current path mismatch";
        return this;
    }

    public BreadcrumbVisualAssert pathContains(String text) {
        String actualPath = breadcrumb.getCurrentPath();
        assert actualPath.contains(text) : "Path doesn't contain: " + text;
        return this;
    }

    public BreadcrumbVisualAssert hasAtLeastItems(int minCount) {
        int actualCount = breadcrumb.getItems().size();
        assert actualCount >= minCount : "Breadcrumb items count is less than expected";
        return this;
    }
}
