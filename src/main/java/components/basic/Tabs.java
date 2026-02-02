package components.basic;

import base.BaseComponent;
import components.visual.TabsVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Tabs Component
 * Represents a tab navigation element
 */
public class Tabs extends BaseComponent {

    public Tabs(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Tabs(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void switchToTab(String tabName) {
        List<WebElement> tabs = getTabs();
        for (WebElement tab : tabs) {
            if (tab.getText().equalsIgnoreCase(tabName)) {
                tab.click();
                break;
            }
        }
    }

    public void switchToTab(int tabIndex) {
        List<WebElement> tabs = getTabs();
        if (tabIndex < tabs.size()) {
            tabs.get(tabIndex).click();
        }
    }

    public List<WebElement> getTabs() {
        return getElement().findElements(By.xpath(".//button[@role='tab'] | .//a[@role='tab']"));
    }

    public String getActiveTabName() {
        try {
            return getElement().findElement(By.xpath(".//button[@aria-selected='true'] | .//a[@aria-selected='true']")).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public int getActiveTabIndex() {
        List<WebElement> tabs = getTabs();
        for (int i = 0; i < tabs.size(); i++) {
            String ariaSelected = tabs.get(i).getAttribute("aria-selected");
            if (ariaSelected != null && ariaSelected.equals("true")) {
                return i;
            }
        }
        return 0;
    }

    /* ===== VISUAL ASSERTION ===== */

    public TabsVisualAssert should() {
        setElement(getElement());
        return new TabsVisualAssert(this);
    }
}
