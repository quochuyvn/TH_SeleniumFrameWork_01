package components.basic;

import base.BaseComponent;
import components.visual.TableRowVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * TableRow Component
 * Represents a table row element
 */
public class TableRow extends BaseComponent {

    public TableRow(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public TableRow(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public List<WebElement> getCells() {
        return getElement().findElements(By.tagName("td"));
    }

    public String getCellContent(int cellIndex) {
        List<WebElement> cells = getCells();
        if (cellIndex < cells.size()) {
            return cells.get(cellIndex).getText();
        }
        return "";
    }

    public void click() {
        getElement().click();
    }

    public int getCellCount() {
        return getCells().size();
    }

    /* ===== VISUAL ASSERTION ===== */

    public TableRowVisualAssert should() {
        setElement(getElement());
        return new TableRowVisualAssert(this);
    }
}
