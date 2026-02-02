package components.visual;

import components.basic.RadioButton;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * RadioVisualAssert
 * Fluent assertions for RadioButton component
 */
public class RadioVisualAssert extends VisualAssert<RadioVisualAssert> {

    private final RadioButton radio;

    public RadioVisualAssert(RadioButton radio) {
        super(radio);
        this.radio = radio;
    }

    /* ===== RADIO-SPECIFIC ASSERTIONS ===== */

    public RadioVisualAssert isSelected() {
        assert radio.isSelected() : "Radio button is not selected";
        return this;
    }

    public RadioVisualAssert isNotSelected() {
        assert !radio.isSelected() : "Radio button is selected";
        return this;
    }

    public RadioVisualAssert hasValue(String value) {
        String actualValue = radio.getValue();
        assert actualValue != null && actualValue.equals(value) : "Value mismatch";
        return this;
    }

    public RadioVisualAssert hasName(String name) {
        String actualName = radio.getElement().getAttribute("name");
        assert actualName != null && actualName.equals(name) : "Name mismatch";
        return this;
    }

    public RadioVisualAssert hasLabel(String labelText) {
        String id = radio.getElement().getAttribute("id");
        String actualLabel = null;
        
        // Try to find label by "for" attribute
        if (id != null && !id.isEmpty()) {
            try {
                WebElement label = radio.getElement().findElement(
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
                WebElement label = radio.getElement().findElement(By.xpath("./ancestor::label"));
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
