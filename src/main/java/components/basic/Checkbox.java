package components.basic;

import base.BaseComponent;
import components.visual.CheckboxVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Checkbox Component
 * Represents a checkbox input element
 */
public class Checkbox extends BaseComponent {

    public Checkbox(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Checkbox(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void check() {
        if (!isChecked()) {
            getElement().click();
        }
    }

    public void uncheck() {
        if (isChecked()) {
            getElement().click();
        }
    }

    public void toggle() {
        getElement().click();
    }

    public boolean isChecked() {
        return getElement().isSelected();
    }

    /* ===== VISUAL ASSERTION ===== */

    public CheckboxVisualAssert should() {
        setElement(getElement());
        return new CheckboxVisualAssert(this);
    }
}
