package components.visual;

import components.basic.Input;

/**
 * InputVisualAssert
 * Fluent assertions for Input component
 */
public class InputVisualAssert extends VisualAssert<InputVisualAssert> {

    private final Input input;

    public InputVisualAssert(Input input) {
        super(input);
        this.input = input;
    }

    /* ===== INPUT-SPECIFIC ASSERTIONS ===== */

    public InputVisualAssert hasPlaceholder(String placeholder) {
        String actualPlaceholder = input.getElement().getAttribute("placeholder");
        assert actualPlaceholder != null && actualPlaceholder.equals(placeholder) : "Placeholder mismatch";
        return this;
    }

    public InputVisualAssert hasValue(String value) {
        String actualValue = input.getValue();
        assert actualValue != null && actualValue.equals(value) : "Value mismatch";
        return this;
    }

    public InputVisualAssert isEmpty() {
        String value = input.getValue();
        assert value != null && value.isEmpty() : "Input is not empty";
        return this;
    }

    public InputVisualAssert isRequired() {
        boolean required = input.getElement().getAttribute("required") != null;
        assert required : "Input is not required";
        return this;
    }

    public InputVisualAssert hasType(String type) {
        String actualType = input.getElement().getAttribute("type");
        assert actualType != null && actualType.equals(type) : "Type mismatch";
        return this;
    }

    public InputVisualAssert isReadOnly() {
        boolean readOnly = input.getElement().getAttribute("readonly") != null;
        assert readOnly : "Input is not readonly";
        return this;
    }
}
