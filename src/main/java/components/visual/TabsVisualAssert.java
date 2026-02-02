package components.visual;

import components.basic.Tabs;

/**
 * TabsVisualAssert
 * Fluent assertions for Tabs component
 */
public class TabsVisualAssert extends VisualAssert<TabsVisualAssert> {

    private final Tabs tabs;

    public TabsVisualAssert(Tabs tabs) {
        super(tabs);
        this.tabs = tabs;
    }

    /* ===== TABS-SPECIFIC ASSERTIONS ===== */

    public TabsVisualAssert hasTabs(int expectedCount) {
        int actualCount = tabs.getTabs().size();
        assert actualCount == expectedCount : "Tabs count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount;
        return this;
    }

    public TabsVisualAssert hasTabWithText(String text) {
        boolean found = tabs.getTabs().stream()
                .anyMatch(tab -> tab.getText().equals(text));
        assert found : "Tab not found: " + text;
        return this;
    }

    public TabsVisualAssert activeTabNameIs(String expectedName) {
        String actualName = tabs.getActiveTabName();
        assert actualName.equals(expectedName) : "Active tab name mismatch";
        return this;
    }

    public TabsVisualAssert activeTabIndexIs(int expectedIndex) {
        int actualIndex = tabs.getActiveTabIndex();
        assert actualIndex == expectedIndex : "Active tab index mismatch";
        return this;
    }

    public TabsVisualAssert isVertical() {
        return hasClass("tabs-vertical");
    }

    public TabsVisualAssert isHorizontal() {
        return hasClass("tabs-horizontal");
    }

    public TabsVisualAssert hasBorderedTabs() {
        return hasClass("tabs-bordered");
    }
}
