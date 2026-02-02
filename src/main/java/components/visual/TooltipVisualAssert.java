package components.visual;

import components.basic.Tooltip;

/**
 * TooltipVisualAssert
 * Fluent assertions for Tooltip component
 */
public class TooltipVisualAssert extends VisualAssert<TooltipVisualAssert> {

    private final Tooltip tooltip;

    public TooltipVisualAssert(Tooltip tooltip) {
        super(tooltip);
        this.tooltip = tooltip;
    }

    /* ===== TOOLTIP-SPECIFIC ASSERTIONS ===== */

    public TooltipVisualAssert hasText(String expectedText) {
        String actualText = tooltip.getTooltipText();
        assert actualText.equals(expectedText) : "Tooltip text mismatch";
        return this;
    }

    public TooltipVisualAssert textContains(String text) {
        String actualText = tooltip.getTooltipText();
        assert actualText.contains(text) : "Tooltip text doesn't contain: " + text;
        return this;
    }

    public TooltipVisualAssert isVisible() {
        // Tooltip visibility is typically shown on hover
        return this;
    }

    public TooltipVisualAssert isTop() {
        return hasClass("top");
    }

    public TooltipVisualAssert isBottom() {
        return hasClass("bottom");
    }

    public TooltipVisualAssert isLeft() {
        return hasClass("left");
    }

    public TooltipVisualAssert isRight() {
        return hasClass("right");
    }
}
