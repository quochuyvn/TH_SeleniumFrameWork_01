package components.visual;

import components.basic.Accordion;

/**
 * AccordionVisualAssert
 * Fluent assertions for Accordion component
 */
public class AccordionVisualAssert extends VisualAssert<AccordionVisualAssert> {

    private final Accordion accordion;

    public AccordionVisualAssert(Accordion accordion) {
        super(accordion);
        this.accordion = accordion;
    }

    /* ===== ACCORDION-SPECIFIC ASSERTIONS ===== */

    public AccordionVisualAssert isExpanded() {
        assert accordion.isExpanded() : "Accordion is not expanded";
        return this;
    }

    public AccordionVisualAssert isCollapsed() {
        assert !accordion.isExpanded() : "Accordion is not collapsed";
        return this;
    }

    public AccordionVisualAssert hasTitle(String expectedTitle) {
        String actualTitle = accordion.getTitle();
        assert actualTitle.equals(expectedTitle) : "Title mismatch";
        return this;
    }

    public AccordionVisualAssert titleContains(String text) {
        String actualTitle = accordion.getTitle();
        assert actualTitle.contains(text) : "Title doesn't contain: " + text;
        return this;
    }

    public AccordionVisualAssert hasContent(String expectedContent) {
        String actualContent = accordion.getContent();
        assert actualContent.equals(expectedContent) : "Content mismatch";
        return this;
    }

    public AccordionVisualAssert contentContains(String text) {
        String actualContent = accordion.getContent();
        assert actualContent.contains(text) : "Content doesn't contain: " + text;
        return this;
    }

    public AccordionVisualAssert isDisabled() {
        return hasClass("disabled");
    }
}
