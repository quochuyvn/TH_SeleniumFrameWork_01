package components.visual;

import components.basic.TextArea;

/**
 * TextAreaVisualAssert
 * Fluent assertions for TextArea component
 */
public class TextAreaVisualAssert extends VisualAssert<TextAreaVisualAssert> {

    private final TextArea textArea;

    public TextAreaVisualAssert(TextArea textArea) {
        super(textArea);
        this.textArea = textArea;
    }

    /* ===== TEXTAREA-SPECIFIC ASSERTIONS ===== */

    public TextAreaVisualAssert hasValue(String value) {
        String actualValue = textArea.getValue();
        assert actualValue != null && actualValue.equals(value) : "Value mismatch";
        return this;
    }

    public TextAreaVisualAssert isEmpty() {
        String value = textArea.getValue();
        assert value != null && value.isEmpty() : "TextArea is not empty";
        return this;
    }

    public TextAreaVisualAssert hasPlaceholder(String placeholder) {
        String actualPlaceholder = textArea.getElement().getAttribute("placeholder");
        assert actualPlaceholder != null && actualPlaceholder.equals(placeholder) : "Placeholder mismatch";
        return this;
    }

    public TextAreaVisualAssert hasRows(int rows) {
        String actualRows = textArea.getElement().getAttribute("rows");
        assert actualRows != null && actualRows.equals(String.valueOf(rows)) : "Rows mismatch";
        return this;
    }

    public TextAreaVisualAssert hasCols(int cols) {
        String actualCols = textArea.getElement().getAttribute("cols");
        assert actualCols != null && actualCols.equals(String.valueOf(cols)) : "Cols mismatch";
        return this;
    }

    public TextAreaVisualAssert isRequired() {
        boolean required = textArea.getElement().getAttribute("required") != null;
        assert required : "TextArea is not required";
        return this;
    }
}
