package components.visual;

import components.basic.Dropdown;

/**
 * DropdownVisualAssert
 * Fluent assertions for Dropdown component
 */
public class DropdownVisualAssert extends VisualAssert<DropdownVisualAssert> {

    private final Dropdown dropdown;

    public DropdownVisualAssert(Dropdown dropdown) {
        super(dropdown);
        this.dropdown = dropdown;
    }

    /* ===== DROPDOWN-SPECIFIC ASSERTIONS ===== */

    public DropdownVisualAssert hasSelectedValue(String value) {
        String actualValue = dropdown.getSelectedValue();
        assert actualValue.equals(value) : "Selected value mismatch";
        return this;
    }

    public DropdownVisualAssert hasSelectedText(String text) {
        String actualText = dropdown.getSelectedText();
        assert actualText.equals(text) : "Selected text mismatch";
        return this;
    }

    public DropdownVisualAssert isRequired() {
        boolean required = dropdown.getElement().getAttribute("required") != null;
        assert required : "Dropdown is not required";
        return this;
    }

    public DropdownVisualAssert isMultiple() {
        boolean multiple = dropdown.getElement().getAttribute("multiple") != null;
        assert multiple : "Dropdown is not multiple";
        return this;
    }

    public DropdownVisualAssert isDisabled() {
        boolean disabled = dropdown.getElement().getAttribute("disabled") != null;
        assert disabled : "Dropdown is not disabled";
        return this;
    }
}
