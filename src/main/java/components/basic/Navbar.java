package components.basic;

import base.BaseComponent;
import components.visual.NavbarVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Navbar Component
 * Represents a navigation bar with menu items
 */
public class Navbar extends BaseComponent {

    public Navbar(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Navbar(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void clickMenuItem(String menuText) {
        List<WebElement> items = getMenuItems();
        for (WebElement item : items) {
            if (item.getText().equalsIgnoreCase(menuText)) {
                item.click();
                break;
            }
        }
    }

    public List<WebElement> getMenuItems() {
        return getElement().findElements(By.xpath(".//a | .//button"));
    }

    public void toggleMenu() {
        try {
            getElement().findElement(By.xpath(".//button[contains(@class, 'toggle')]")).click();
        } catch (Exception e) {
            logger.warn("Toggle button not found");
        }
    }

    /* ===== VISUAL ASSERTION ===== */

    public NavbarVisualAssert should() {
        setElement(getElement());
        return new NavbarVisualAssert(this);
    }
}
