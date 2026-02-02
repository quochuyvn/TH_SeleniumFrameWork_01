package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class MouseUtil {

    private final Actions actions;

    public MouseUtil(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.actions = new Actions(driver);
    }

    /* ================= BASIC ================= */

    public void hover(WebElement element) {
        if (element == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        actions.moveToElement(element).perform();
    }

    public void rightClick(WebElement element) {
        if (element == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        actions.contextClick(element).perform();
    }

    public void doubleClick(WebElement element) {
        if (element == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        actions.doubleClick(element).perform();
    }

    public void clickAndHold(WebElement element) {
        if (element == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        actions.clickAndHold(element).perform();
    }

    public void release() {
        actions.release().perform();
    }

    /* ================= SCROLL ================= */

    public void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollByOffset(int xOffset, int yOffset) {
        actions.moveByOffset(xOffset, yOffset).perform();
    }

    /* ================= DRAG & DROP ================= */

    /**
     * Standard drag and drop (may be flaky with some elements)
     */
    public void dragAndDrop(WebElement source, WebElement target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target elements cannot be null");
        }
        actions.dragAndDrop(source, target).perform();
    }

    /**
     * Stable custom drag & drop (clickAndHold -> move -> release)
     */
    public void dragAndDropCustom(WebElement source, WebElement target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target elements cannot be null");
        }
        clickAndHold(source);
        actions.moveToElement(target).perform();
        release();
    }

    /**
     * Drag element by pixel offset
     */
    public void dragAndDropByOffset(WebElement source, int xOffset, int yOffset) {
        clickAndHold(source);
        actions.moveByOffset(xOffset, yOffset).perform();
        release();
    }
}

