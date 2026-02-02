package components.visual;

import components.basic.Label;

/**
 * LabelVisualAssert
 * Fluent assertions for Label component
 */
public class LabelVisualAssert extends VisualAssert<LabelVisualAssert> {

    private final Label label;

    public LabelVisualAssert(Label label) {
        super(label);
        this.label = label;
    }

    /* ===== LABEL-SPECIFIC ASSERTIONS ===== */

    public LabelVisualAssert hasText(String text) {
        String actualText = label.getText();
        assert actualText != null && actualText.equals(text) : "Text mismatch";
        return this;
    }

    public LabelVisualAssert containsText(String text) {
        String actualText = label.getText();
        assert actualText != null && actualText.contains(text) : "Text doesn't contain: " + text;
        return this;
    }

    public LabelVisualAssert isRequired() {
        String text = label.getText();
        assert text != null && text.contains("*") : "Label is not marked as required";
        return this;
    }

    public LabelVisualAssert hasFor(String forAttr) {
        String actualFor = label.getElement().getAttribute("for");
        assert actualFor != null && actualFor.equals(forAttr) : "For attribute mismatch";
        return this;
    }
}
