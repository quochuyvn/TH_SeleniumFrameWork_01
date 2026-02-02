package components.basic;

import base.BaseComponent;
import components.visual.ModalVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Modal Component
 * Represents a modal/dialog popup element
 */
public class Modal extends BaseComponent {

    private final By closeButton;

    public Modal(WebDriver driver, By locator, By closeButton) {
        super(driver, locator);
        this.closeButton = closeButton;
    }

    public Modal(WebDriver driver, By locator) {
        super(driver, locator);
        this.closeButton = By.xpath(".//button[contains(@class, 'close')]");
    }

    public Modal(WebDriver driver) {
        super(driver);
        this.closeButton = By.xpath(".//button[contains(@class, 'close')]");
    }

    /* ===== ACTION ===== */

    public boolean isDisplayed() {
        try {
            return getElement().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void close() {
        try {
            if (driver != null && closeButton != null) {
                driver.findElement(closeButton).click();
            }
        } catch (Exception e) {
            logger.warn("Close button not found or not clickable");
        }
    }

    /* ===== VISUAL ASSERTION ===== */

    public ModalVisualAssert should() {
        setElement(getElement());
        return new ModalVisualAssert(this);
    }
}
