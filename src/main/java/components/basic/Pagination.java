package components.basic;

import base.BaseComponent;
import components.visual.PaginationVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Pagination Component
 * Represents a pagination/page navigation element
 */
public class Pagination extends BaseComponent {

    public Pagination(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Pagination(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void goToPage(int pageNumber) {
        List<WebElement> buttons = getPageButtons();
        if (pageNumber <= buttons.size()) {
            buttons.get(pageNumber - 1).click();
        }
    }

    public void nextPage() {
        try {
            getElement().findElement(By.xpath(".//button[contains(text(), 'Next')]")).click();
        } catch (Exception e) {
            logger.warn("Next button not found");
        }
    }

    public void previousPage() {
        try {
            getElement().findElement(By.xpath(".//button[contains(text(), 'Prev')]")).click();
        } catch (Exception e) {
            logger.warn("Previous button not found");
        }
    }

    private List<WebElement> getPageButtons() {
        return getElement().findElements(By.xpath(".//button[not(contains(@class, 'next') or contains(@class, 'prev'))]"));
    }

    public int getCurrentPage() {
        try {
            return Integer.parseInt(getElement().findElement(By.xpath(".//button[@aria-current]")).getText());
        } catch (Exception e) {
            return 1;
        }
    }

    /* ===== VISUAL ASSERTION ===== */

    public PaginationVisualAssert should() {
        setElement(getElement());
        return new PaginationVisualAssert(this);
    }
}
