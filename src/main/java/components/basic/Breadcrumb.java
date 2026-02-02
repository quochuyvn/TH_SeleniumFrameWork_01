package components.basic;

import base.BaseComponent;
import components.visual.BreadcrumbVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Breadcrumb Component
 * Represents a breadcrumb navigation path
 */
public class Breadcrumb extends BaseComponent {

    public Breadcrumb(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Breadcrumb(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public List<WebElement> getItems() {
        return getElement().findElements(By.xpath(".//a | .//span"));
    }

    public void clickBreadcrumb(String text) {
        List<WebElement> items = getItems();
        for (WebElement item : items) {
            if (item.getText().equalsIgnoreCase(text)) {
                item.click();
                break;
            }
        }
    }

    public String getCurrentPath() {
        StringBuilder path = new StringBuilder();
        List<WebElement> items = getItems();
        for (WebElement item : items) {
            if (!path.isEmpty()) path.append(" > ");
            path.append(item.getText());
        }
        return path.toString();
    }

    /* ===== VISUAL ASSERTION ===== */

    public BreadcrumbVisualAssert should() {
        setElement(getElement());
        return new BreadcrumbVisualAssert(this);
    }
}
