package components.basic;

import base.BaseComponent;
import components.visual.TableVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Table Component
 * Represents a data table element
 */
public class Table extends BaseComponent {

    public Table(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Table(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public List<WebElement> getRows() {
        return getElement().findElements(By.tagName("tr"));
    }

    public List<WebElement> getCells(int rowIndex) {
        if (rowIndex < getRows().size()) {
            return getRows().get(rowIndex).findElements(By.tagName("td"));
        }
        return List.of();
    }

    public String getCellContent(int rowIndex, int cellIndex) {
        List<WebElement> cells = getCells(rowIndex);
        if (cellIndex < cells.size()) {
            return cells.get(cellIndex).getText();
        }
        return "";
    }

    public int getRowCount() {
        return getRows().size();
    }

    /* ===== VISUAL ASSERTION ===== */

    public TableVisualAssert should() {
        setElement(getElement());
        return new TableVisualAssert(this);
    }
}
