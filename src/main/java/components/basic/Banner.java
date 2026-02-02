package components.basic;

import base.BaseComponent;
import components.visual.BannerVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Banner Component
 * Represents a header/promotion banner element
 */
public class Banner extends BaseComponent {

    public Banner(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Banner(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public String getTitle() {
        return getElement().getText();
    }

    public boolean isVisible() {
        try {
            return getElement().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /* ===== VISUAL ASSERTION ===== */

    public BannerVisualAssert should() {
        setElement(getElement());
        return new BannerVisualAssert(this);
    }
}
