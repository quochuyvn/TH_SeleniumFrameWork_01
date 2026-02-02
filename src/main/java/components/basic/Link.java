package components.basic;

import base.BaseComponent;
import components.visual.LinkVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Link Component
 * Represents a navigation link element
 */
public class Link extends BaseComponent {

    public Link(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Link(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void click() {
        getElement().click();
    }

    public String getText() {
        return getElement().getText();
    }

    public String getHref() {
        return getElement().getAttribute("href");
    }

    /* ===== VISUAL ASSERTION ===== */

    public LinkVisualAssert should() {
        setElement(getElement());
        return new LinkVisualAssert(this);
    }
}
