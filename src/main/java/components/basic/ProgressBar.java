package components.basic;

import base.BaseComponent;
import components.visual.ProgressBarVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * ProgressBar Component
 * Represents a progress bar element
 */
public class ProgressBar extends BaseComponent {

    public ProgressBar(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public ProgressBar(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public int getProgress() {
        try {
            String width = getElement().getCssValue("width");
            String value = getAttribute("aria-valuenow");
            if (value != null) {
                return Integer.parseInt(value);
            }
            // Parse width percentage if available
            if (width.contains("%")) {
                return Integer.parseInt(width.replace("%", "").replace("px", ""));
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public String getLabel() {
        try {
            return getElement().getText();
        } catch (Exception e) {
            return "";
        }
    }

    /* ===== VISUAL ASSERTION ===== */

    public ProgressBarVisualAssert should() {
        setElement(getElement());
        return new ProgressBarVisualAssert(this);
    }
}
