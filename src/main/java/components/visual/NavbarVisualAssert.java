package components.visual;

import components.basic.Navbar;

/**
 * NavbarVisualAssert
 * Fluent assertions for Navbar component
 */
public class NavbarVisualAssert extends VisualAssert<NavbarVisualAssert> {

    private final Navbar navbar;

    public NavbarVisualAssert(Navbar navbar) {
        super(navbar);
        this.navbar = navbar;
    }

    /* ===== NAVBAR-SPECIFIC ASSERTIONS ===== */

    public NavbarVisualAssert hasMenuItems(int expectedCount) {
        int actualCount = navbar.getMenuItems().size();
        assert actualCount == expectedCount : "Menu items count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount;
        return this;
    }

    public NavbarVisualAssert hasMenuItemWithText(String text) {
        boolean found = navbar.getMenuItems().stream()
                .anyMatch(item -> item.getText().equals(text));
        assert found : "Menu item not found: " + text;
        return this;
    }

    public NavbarVisualAssert isDark() {
        return hasClass("navbar-dark");
    }

    public NavbarVisualAssert isLight() {
        return hasClass("navbar-light");
    }

    public NavbarVisualAssert isSticky() {
        return hasClass("sticky");
    }

    public NavbarVisualAssert isFixed() {
        String classes = navbar.getElement().getAttribute("class");
        boolean isFixed = classes != null && (classes.contains("fixed-top") || classes.contains("fixed-bottom"));
        assert isFixed : "Navbar is not fixed";
        return this;
    }
}
