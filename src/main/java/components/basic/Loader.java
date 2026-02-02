package components.basic;

import base.BaseComponent;
import components.visual.LoaderVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Loader/Spinner Component
 * Represents a loading/spinner indicator element
 */
public class Loader extends BaseComponent {

    public Loader(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Loader(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public boolean isLoading() {
        try {
            return getElement().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoadingText() {
        try {
            return getElement().getText();
        } catch (Exception e) {
            return "";
        }
    }

    /* ===== VISUAL ASSERTION ===== */

    public LoaderVisualAssert should() {
        setElement(getElement());
        return new LoaderVisualAssert(this);
    }
}
