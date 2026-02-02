package components.basic;

import base.BaseComponent;
import components.visual.SidebarVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Sidebar Component
 * Represents a sidebar navigation element
 */
public class Sidebar extends BaseComponent {

    public Sidebar(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Sidebar(WebDriver driver) {
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

    public void expand() {
        try {
            getElement().findElement(By.xpath(".//button[contains(@class, 'expand')]")).click();
        } catch (Exception e) {
            logger.warn("Expand button not found");
        }
    }

    public void collapse() {
        try {
            getElement().findElement(By.xpath(".//button[contains(@class, 'collapse')]")).click();
        } catch (Exception e) {
            logger.warn("Collapse button not found");
        }
    }

    public boolean isExpanded() {
        String classes = getAttribute("class");
        return classes != null && classes.contains("expanded");
    }

    /* ===== VISUAL ASSERTION ===== */

    public SidebarVisualAssert should() {
        setElement(getElement());
        return new SidebarVisualAssert(this);
    }
}
