package components.basic;

import base.BaseComponent;
import components.visual.ImageVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Image Component
 * Represents an image or icon element
 */
public class Image extends BaseComponent {

    public Image(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Image(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public String getSrc() {
        return getElement().getAttribute("src");
    }

    public String getAlt() {
        return getElement().getAttribute("alt");
    }

    public int getWidth() {
        String width = getElement().getAttribute("width");
        return width != null ? Integer.parseInt(width) : 0;
    }

    public int getHeight() {
        String height = getElement().getAttribute("height");
        return height != null ? Integer.parseInt(height) : 0;
    }

    /* ===== VISUAL ASSERTION ===== */

    public ImageVisualAssert should() {
        setElement(getElement());
        return new ImageVisualAssert(this);
    }
}
