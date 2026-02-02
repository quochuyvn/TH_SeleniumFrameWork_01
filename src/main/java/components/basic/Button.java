package components.basic;

import base.BaseComponent;
import components.visual.ButtonVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Button Component
 * Represents a clickable button element
 */
public class Button extends BaseComponent {

    public Button(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Button(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void click() {
        getElement().click();
    }

    public void hover() {
        // Will be used in visual tests
    }

    /* ===== VISUAL ASSERTION ===== */

    public ButtonVisualAssert should() {
        setElement(getElement());
        return new ButtonVisualAssert(this);
    }
}
