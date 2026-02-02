package components.basic;

import base.BaseComponent;
import components.visual.LabelVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Label Component
 * Represents a label or static text element
 */
public class Label extends BaseComponent {

    public Label(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Label(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public String getText() {
        return getElement().getText();
    }

    /* ===== VISUAL ASSERTION ===== */

    public LabelVisualAssert should() {
        setElement(getElement());
        return new LabelVisualAssert(this);
    }
}
