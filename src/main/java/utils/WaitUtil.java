package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.exceptions.ElementInteractionException;

import java.time.Duration;

/**
 * =====================================================
 * WaitUtil
 * =====================================================
 * ✅ CENTRALIZED wait utility (DRY principle)
 * 
 * Eliminates wait logic duplication across:
 *   - KeyboardUtil
 *   - MouseUtil
 *   - SliderUtil
 *   - BasePage (optional - can still use this)
 * 
 * Benefits:
 *   - Single source of truth for wait logic
 *   - Better error messages with context
 *   - Easier to adjust timeouts globally
 *   - Consistent wait patterns across framework
 * 
 * Usage:
 *   WaitUtil waitUtil = new WaitUtil(driver, 10);
 *   waitUtil.waitForClickable(element);
 *   waitUtil.waitForVisible(locator);
 *   
 * Thread-Safe: No static state; safe for parallel tests
 * =====================================================
 */
public class WaitUtil {

    private static final Logger logger = LogManager.getLogger(WaitUtil.class);
    
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final int explicitWaitSeconds;

    /**
     * Constructor
     * @param driver WebDriver instance
     * @param explicitWaitSeconds timeout in seconds
     */
    public WaitUtil(WebDriver driver, int explicitWaitSeconds) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        if (explicitWaitSeconds <= 0) {
            throw new IllegalArgumentException("Explicit wait seconds must be > 0");
        }
        
        this.driver = driver;
        this.explicitWaitSeconds = explicitWaitSeconds;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    // ============================================
    // VISIBILITY WAITS
    // ============================================

    /**
     * Wait for element to be visible
     * @param element WebElement to wait for
     * @throws ElementInteractionException if timeout
     */
    public void waitForVisible(WebElement element) throws ElementInteractionException {
        if (element == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        try {
            logger.debug("[WAIT] Visibility of element: {}", describeElement(element));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            String message = String.format(
                "Element not visible within %d seconds: %s",
                explicitWaitSeconds,
                describeElement(element)
            );
            logger.error(message);
            throw new ElementInteractionException(message, e);
        }
    }

    /**
     * Wait for element to be visible by locator
     * @param locator By locator
     * @throws ElementInteractionException if timeout
     */
    public void waitForVisible(By locator) throws ElementInteractionException {
        if (locator == null) {
            throw new IllegalArgumentException("Locator cannot be null");
        }
        try {
            logger.debug("[WAIT] Visibility of locator: {}", locator);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            String message = String.format(
                "Locator not visible within %d seconds: %s",
                explicitWaitSeconds,
                locator
            );
            logger.error(message);
            throw new ElementInteractionException(message, e);
        }
    }

    // ============================================
    // CLICKABILITY WAITS
    // ============================================

    /**
     * Wait for element to be clickable
     * @param element WebElement to wait for
     * @throws ElementInteractionException if timeout
     */
    public void waitForClickable(WebElement element) throws ElementInteractionException {
        if (element == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        try {
            logger.debug("[WAIT] Clickability of element: {}", describeElement(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            String message = String.format(
                "Element not clickable within %d seconds: %s",
                explicitWaitSeconds,
                describeElement(element)
            );
            logger.error(message);
            throw new ElementInteractionException(message, e);
        }
    }

    /**
     * Wait for element to be clickable by locator
     * @param locator By locator
     * @throws ElementInteractionException if timeout
     */
    public void waitForClickable(By locator) throws ElementInteractionException {
        if (locator == null) {
            throw new IllegalArgumentException("Locator cannot be null");
        }
        try {
            logger.debug("[WAIT] Clickability of locator: {}", locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            String message = String.format(
                "Locator not clickable within %d seconds: %s",
                explicitWaitSeconds,
                locator
            );
            logger.error(message);
            throw new ElementInteractionException(message, e);
        }
    }

    // ============================================
    // PRESENCE WAITS (element exists in DOM)
    // ============================================

    /**
     * Wait for element to be present in DOM (not necessarily visible)
     * @param locator By locator
     * @throws ElementInteractionException if timeout
     */
    public void waitForPresence(By locator) throws ElementInteractionException {
        if (locator == null) {
            throw new IllegalArgumentException("Locator cannot be null");
        }
        try {
            logger.debug("[WAIT] Presence of locator: {}", locator);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            String message = String.format(
                "Element not found within %d seconds: %s",
                explicitWaitSeconds,
                locator
            );
            logger.error(message);
            throw new ElementInteractionException(message, e);
        }
    }

    // ============================================
    // INVISIBILITY WAITS
    // ============================================

    /**
     * Wait for element to become invisible/hidden
     * @param element WebElement to wait for
     * @throws ElementInteractionException if timeout
     */
    public void waitForInvisible(WebElement element) throws ElementInteractionException {
        if (element == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        try {
            logger.debug("[WAIT] Invisibility of element: {}", describeElement(element));
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            String message = String.format(
                "Element still visible after %d seconds: %s",
                explicitWaitSeconds,
                describeElement(element)
            );
            logger.error(message);
            throw new ElementInteractionException(message, e);
        }
    }

    /**
     * Wait for element to become invisible by locator
     * @param locator By locator
     * @throws ElementInteractionException if timeout
     */
    public void waitForInvisible(By locator) throws ElementInteractionException {
        if (locator == null) {
            throw new IllegalArgumentException("Locator cannot be null");
        }
        try {
            logger.debug("[WAIT] Invisibility of locator: {}", locator);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            String message = String.format(
                "Locator still visible after %d seconds: %s",
                explicitWaitSeconds,
                locator
            );
            logger.error(message);
            throw new ElementInteractionException(message, e);
        }
    }

    // ============================================
    // CUSTOM CONDITION WAITS
    // ============================================

    /**
     * Wait for page to be fully loaded (document.readyState = "complete")
     * @throws ElementInteractionException if timeout
     */
    public void waitForPageLoaded() throws ElementInteractionException {
        try {
            logger.debug("[WAIT] Page loaded (document.readyState = complete)");
            wait.until(driver -> {
                Object readyState = ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("return document.readyState");
                return "complete".equals(readyState);
            });
        } catch (TimeoutException e) {
            String message = String.format(
                "Page did not load within %d seconds",
                explicitWaitSeconds
            );
            logger.error(message);
            throw new ElementInteractionException(message, e);
        }
    }

    /**
     * Wait for JavaScript to complete (jQuery AJAX calls)
     * Useful when page uses jQuery for AJAX requests
     * @throws ElementInteractionException if timeout
     */
    public void waitForJQueryAjaxComplete() throws ElementInteractionException {
        try {
            logger.debug("[WAIT] jQuery AJAX complete");
            wait.until(driver -> {
                Object jQueryActive = ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("return (typeof jQuery != 'undefined') ? jQuery.active == 0 : true");
                return jQueryActive instanceof Boolean ? (Boolean) jQueryActive : false;
            });
        } catch (TimeoutException e) {
            String message = String.format(
                "jQuery AJAX not complete within %d seconds",
                explicitWaitSeconds
            );
            logger.error(message);
            throw new ElementInteractionException(message, e);
        }
    }

    /**
     * Wait for Angular (if applicable) - useful for Angular apps
     * @throws ElementInteractionException if timeout
     */
    public void waitForAngularLoad() throws ElementInteractionException {
        try {
            logger.debug("[WAIT] Angular load complete");
            wait.until(driver -> {
                Object angular = ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("return (typeof angular != 'undefined') " +
                        "? angular.element(document).injector().get('$http').pendingRequests.length === 0 " +
                        ": true");
                return angular instanceof Boolean ? (Boolean) angular : false;
            });
        } catch (TimeoutException e) {
            String message = String.format(
                "Angular not loaded within %d seconds",
                explicitWaitSeconds
            );
            logger.error(message);
            throw new ElementInteractionException(message, e);
        }
    }

    // ============================================
    // GETTERS
    // ============================================

    /**
     * Get the underlying WebDriverWait instance
     * (Use only if WaitUtil methods are insufficient)
     * @return WebDriverWait instance
     */
    public WebDriverWait getWait() {
        return this.wait;
    }

    /**
     * Get configured explicit wait timeout in seconds
     * @return timeout in seconds
     */
    public int getExplicitWaitSeconds() {
        return explicitWaitSeconds;
    }

    /**
     * Get WebDriver instance
     * @return WebDriver instance
     */
    public WebDriver getDriver() {
        return driver;
    }

    // ============================================
    // HELPER METHODS
    // ============================================

    /**
     * Helper to describe element for logging
     * @param element WebElement to describe
     * @return descriptive string
     */
    private String describeElement(WebElement element) {
        try {
            String text = element.getText();
            String id = element.getAttribute("id");
            
            String identifier = (text != null && !text.isEmpty()) ? text : 
                               (id != null && !id.isEmpty()) ? id : "unknown";
            
            return String.format("%s[%s]", element.getTagName(), identifier);
        } catch (Exception e) {
            return element.toString();
        }
    }
}
