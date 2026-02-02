package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class KeyboardUtil {

	private final Actions actions;

    public KeyboardUtil(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.actions = new Actions(driver);
    }

    /* ================= COMBINATION ================= */

    public void ctrlSelectAll(WebElement element) {
        element.click();
        actions.keyDown(Keys.CONTROL)
               .sendKeys("a")
               .keyUp(Keys.CONTROL)
               .perform();
    }

    public void ctrlCopy(WebElement element) {
        element.click();
        actions.keyDown(Keys.CONTROL)
               .sendKeys("c")
               .keyUp(Keys.CONTROL)
               .perform();
    }

    public void ctrlPaste(WebElement element) {
        element.click();
        actions.keyDown(Keys.CONTROL)
               .sendKeys("v")
               .keyUp(Keys.CONTROL)
               .perform();
    }

    public void shiftClick(WebElement element) {
        if (element == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        actions.keyDown(Keys.SHIFT)
               .click(element)
               .keyUp(Keys.SHIFT)
               .perform();
    }

    public void altClick(WebElement element) {
        if (element == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        actions.keyDown(Keys.ALT)
               .click(element)
               .keyUp(Keys.ALT)
               .perform();
    }

    /* ================= SINGLE KEY ================= */

    public void pressKey(Keys key) {
        actions.sendKeys(key).perform();
    }

    public void enterKey() {
        pressKey(Keys.ENTER);
    }

    public void tabKey() {
        pressKey(Keys.TAB);
    }

    public void escapeKey() {
        pressKey(Keys.ESCAPE);
    }

    public void deleteKey() {
        pressKey(Keys.DELETE);
    }

    public void backspaceKey() {
        pressKey(Keys.BACK_SPACE);
    }

    /* ================= CTRL COMBINATIONS ================= */

    public void ctrlX(WebElement element) {
        element.click();
        actions.keyDown(Keys.CONTROL)
               .sendKeys("x")
               .keyUp(Keys.CONTROL)
               .perform();
    }

    public void ctrlZ() {
        actions.keyDown(Keys.CONTROL)
               .sendKeys("z")
               .keyUp(Keys.CONTROL)
               .perform();
    }
}