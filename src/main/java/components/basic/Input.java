package components.basic;

import base.BaseComponent;
import components.visual.InputVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.StaleElementReferenceException;

/**
 * Input Component
 * Represents an input field (text, password, email, etc.)
 */
public class Input extends BaseComponent {

    public Input(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Input(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public Input type(String text) {
        try {
            getElement().clear();
            getElement().sendKeys(text);
        } catch (StaleElementReferenceException ex) {
            // Refresh element once if DOM updated
            this.element = null;
            getElement().clear();
            getElement().sendKeys(text);
        }
        return this;
    }

    public Input clear() {
        try {
            getElement().clear();
        } catch (StaleElementReferenceException ex) {
            this.element = null;
            getElement().clear();
        }
        return this;
    }

    public String getValue() {
        return getElement().getAttribute("value");
    }

    /* ===== VISUAL ASSERTION ===== */

    public InputVisualAssert should() {
        setElement(getElement());
        return new InputVisualAssert(this);
    }
}
