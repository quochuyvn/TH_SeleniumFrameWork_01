package components.basic;

import base.BaseComponent;
import components.visual.AlertVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Alert Component
 * Represents an alert/system message element
 */
public class Alert extends BaseComponent {

    private final By closeButton;

    public Alert(WebDriver driver, By locator, By closeButton) {
        super(driver, locator);
        this.closeButton = closeButton;
    }

    public Alert(WebDriver driver, By locator) {
        super(driver, locator);
        this.closeButton = By.xpath(".//button[contains(@class, 'close')]");
    }

    public Alert(WebDriver driver) {
        super(driver);
        this.closeButton = By.xpath(".//button[contains(@class, 'close')]");
    }

    /* ===== ACTION ===== */

    public String getAlertMessage() {
        return getElement().getText();
    }

    public boolean isSuccess() {
        return hasClass("success");
    }

    public boolean isError() {
        return hasClass("error");
    }

    public boolean isWarning() {
        return hasClass("warning");
    }

    public boolean isInfo() {
        return hasClass("info");
    }

    public void close() {
        try {
            if (driver != null && closeButton != null) {
                driver.findElement(closeButton).click();
            }
        } catch (Exception e) {
            logger.warn("Close button not found");
        }
    }

    private boolean hasClass(String className) {
        String classes = getAttribute("class");
        return classes != null && classes.contains(className);
    }

    /* ===== VISUAL ASSERTION ===== */

    public AlertVisualAssert should() {
        setElement(getElement());
        return new AlertVisualAssert(this);
    }
}
