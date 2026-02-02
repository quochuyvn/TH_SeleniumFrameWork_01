package base;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * =====================================================
 * BaseComponent
 * =====================================================
 * - Base class cho tất cả component
 * - Cung cấp WebDriver và WebElement
 * - CSS utilities cho visual testing
 * - Hỗ trợ cả PageFactory (@FindBy) và By locator
 * =====================================================
 */
public abstract class BaseComponent {

    protected WebDriver driver;
    protected WebElement element;
    protected By locator;
    protected Logger logger;

    /**
     * Constructor cho component (PageFactory)
     * @param driver WebDriver instance
     */
    public BaseComponent(WebDriver driver) {
        this.driver = driver;
        this.logger = LogManager.getLogger(this.getClass());
        PageFactory.initElements(driver, this);
    }

    /**
     * Constructor cho component (By locator)
     * @param driver WebDriver instance
     * @param locator By locator
     */
    public BaseComponent(WebDriver driver, By locator) {
        this.driver = driver;
        this.locator = locator;
        this.logger = LogManager.getLogger(this.getClass());
    }

    /**
     * Getter for WebDriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Getter for WebElement
     */
    public WebElement getElement() {
        if (locator != null) {
            element = driver.findElement(locator);
        }
        return element;
    }

    /**
     * Setter for WebElement
     */
    public void setElement(WebElement element) {
        this.element = element;
    }

    /**
     * Lấy Rectangle (vị trí + kích thước) của element
     */
    public Rectangle getRect() {
        if (element == null) {
            throw new RuntimeException("Element không được khởi tạo");
        }
        return element.getRect();
    }

    /**
     * Lấy CSS property value của element
     * @param propertyName CSS property name (e.g., "background-color", "font-size")
     * @return CSS property value (never null, returns empty string if null)
     */
    @SuppressWarnings("null")
    public String getCss(String propertyName) {
        if (element == null) {
            throw new RuntimeException("Element không được khởi tạo");
        }
        return java.util.Objects.requireNonNullElse(element.getCssValue(propertyName), "");
    }

    /**
     * Lấy attribute value của element
     * @param attributeName HTML attribute name (e.g., "class", "id")
     * @return Attribute value (never null, returns empty string if null)
     */
    @SuppressWarnings("null")
    public String getAttribute(String attributeName) {
        if (element == null) {
            throw new RuntimeException("Element không được khởi tạo");
        }
        return java.util.Objects.requireNonNullElse(element.getAttribute(attributeName), "");
    }

    /**
     * Log helper for debugging
     */
    protected void log(String message) {
        logger.debug(message);
    }
}
