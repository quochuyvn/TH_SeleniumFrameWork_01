package components.visual;

import components.basic.Checkbox;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * CheckboxVisualAssert
 * Fluent assertions for Checkbox component
 */
public class CheckboxVisualAssert extends VisualAssert<CheckboxVisualAssert> {

    private final Checkbox checkbox;

    public CheckboxVisualAssert(Checkbox checkbox) {
        super(checkbox);
        this.checkbox = checkbox;
    }

    /* ===== CHECKBOX-SPECIFIC ASSERTIONS ===== */

    public CheckboxVisualAssert isChecked() {
        assert checkbox.isChecked() : "Checkbox is not checked";
        return this;
    }

    public CheckboxVisualAssert isUnchecked() {
        assert !checkbox.isChecked() : "Checkbox is checked";
        return this;
    }

    public CheckboxVisualAssert isRequired() {
        boolean required = checkbox.getElement().getAttribute("required") != null;
        assert required : "Checkbox is not required";
        return this;
    }

    public CheckboxVisualAssert hasValue(String value) {
        String actualValue = checkbox.getElement().getAttribute("value");
        assert actualValue != null && actualValue.equals(value) : "Value mismatch";
        return this;
    }

    public CheckboxVisualAssert hasLabel(String labelText) {
        String id = checkbox.getElement().getAttribute("id");
        String actualLabel = null;
        
        // Try to find label by "for" attribute
        if (id != null && !id.isEmpty()) {
            try {
                WebElement label = checkbox.getElement().findElement(
                    By.xpath("//label[@for='" + id + "']")
                );
                actualLabel = label.getText().trim();
            } catch (NoSuchElementException e) {
                // Label not found by "for" attribute
            }
        }
        
        // If not found, try parent label element
        if (actualLabel == null || actualLabel.isEmpty()) {
            try {
                WebElement label = checkbox.getElement().findElement(By.xpath("./ancestor::label"));
                actualLabel = label.getText().trim();
            } catch (NoSuchElementException e) {
                // No parent label
            }
        }
        
        assert actualLabel != null && actualLabel.equals(labelText) 
            : String.format("Label mismatch. Expected: '%s', Actual: '%s'", labelText, actualLabel);
        return this;
    }
}
