package components.basic;

import base.BaseComponent;
import components.visual.TextAreaVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * TextArea Component
 * Represents a multi-line text input field
 */
public class TextArea extends BaseComponent {

    public TextArea(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public TextArea(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void type(String text) {
        getElement().clear();
        getElement().sendKeys(text);
    }

    public void clear() {
        getElement().clear();
    }

    public String getValue() {
        return getElement().getAttribute("value");
    }

    public String getText() {
        return getElement().getText();
    }

    /* ===== VISUAL ASSERTION ===== */

    public TextAreaVisualAssert should() {
        setElement(getElement());
        return new TextAreaVisualAssert(this);
    }
}
