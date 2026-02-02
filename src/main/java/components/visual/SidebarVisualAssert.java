package components.visual;

import components.basic.Sidebar;

/**
 * SidebarVisualAssert
 * Fluent assertions for Sidebar component
 */
public class SidebarVisualAssert extends VisualAssert<SidebarVisualAssert> {

    private final Sidebar sidebar;

    public SidebarVisualAssert(Sidebar sidebar) {
        super(sidebar);
        this.sidebar = sidebar;
    }

    /* ===== SIDEBAR-SPECIFIC ASSERTIONS ===== */

    public SidebarVisualAssert isExpanded() {
        assert sidebar.isExpanded() : "Sidebar is not expanded";
        return this;
    }

    public SidebarVisualAssert isCollapsed() {
        assert !sidebar.isExpanded() : "Sidebar is not collapsed";
        return this;
    }

    public SidebarVisualAssert hasMenuItems(int expectedCount) {
        int actualCount = sidebar.getMenuItems().size();
        assert actualCount == expectedCount : "Menu items count mismatch";
        return this;
    }

    public SidebarVisualAssert hasMenuItemWithText(String text) {
        boolean found = sidebar.getMenuItems().stream()
                .anyMatch(item -> item.getText().equals(text));
        assert found : "Menu item not found: " + text;
        return this;
    }

    public SidebarVisualAssert isLeft() {
        return hasClass("sidebar-left");
    }

    public SidebarVisualAssert isRight() {
        return hasClass("sidebar-right");
    }

    public SidebarVisualAssert isDark() {
        return hasClass("dark");
    }
}
