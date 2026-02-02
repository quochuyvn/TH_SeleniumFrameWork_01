package components.basic;

import base.BaseComponent;
import components.visual.TooltipVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Tooltip Component
 * Represents a tooltip/hover information element
 */
public class Tooltip extends BaseComponent {

    private final By tooltipLocator;

    public Tooltip(WebDriver driver, By locator, By tooltipLocator) {
        super(driver, locator);
        this.tooltipLocator = tooltipLocator;
    }

    public Tooltip(WebDriver driver, By locator) {
        super(driver, locator);
        this.tooltipLocator = By.xpath(".//div[@class*='tooltip']");
    }

    public Tooltip(WebDriver driver) {
        super(driver);
        this.tooltipLocator = By.xpath(".//div[@class*='tooltip']");
    }

    /* ===== ACTION ===== */

    public void hover() {
        WebDriver driverRef = driver;
        WebElement element = getElement();
        if (driverRef != null && element != null) {
            new Actions(driverRef).moveToElement(element).perform();
        }
    }

    public String getTooltipText() {
        hover();
        try {
            if (driver != null && tooltipLocator != null) {
                return driver.findElement(tooltipLocator).getText();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    /* ===== VISUAL ASSERTION ===== */

    public TooltipVisualAssert should() {
        setElement(getElement());
        return new TooltipVisualAssert(this);
    }
}
