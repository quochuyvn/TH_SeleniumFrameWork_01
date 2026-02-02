package components.basic;

import base.BaseComponent;
import components.visual.RadioVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * RadioButton Component
 * Represents a radio button input element
 */
public class RadioButton extends BaseComponent {

    public RadioButton(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public RadioButton(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void select() {
        if (!isSelected()) {
            getElement().click();
        }
    }

    public boolean isSelected() {
        return getElement().isSelected();
    }

    public String getValue() {
        return getElement().getAttribute("value");
    }

    /* ===== VISUAL ASSERTION ===== */

    public RadioVisualAssert should() {
        setElement(getElement());
        return new RadioVisualAssert(this);
    }
}
