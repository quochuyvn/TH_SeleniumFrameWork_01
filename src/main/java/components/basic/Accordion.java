package components.basic;

import base.BaseComponent;
import components.visual.AccordionVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Accordion Component
 * Represents an accordion/collapsible element
 */
public class Accordion extends BaseComponent {

    public Accordion(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Accordion(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void expand() {
        getElement().click();
    }

    public void collapse() {
        getElement().click();
    }

    public void toggle() {
        getElement().click();
    }

    public boolean isExpanded() {
        try {
            WebElement content = getElement().findElement(By.xpath(".//*[contains(@class, 'content')]"));
            return content.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTitle() {
        try {
            return getElement().findElement(By.xpath(".//*[contains(@class, 'title')]")).getText();
        } catch (Exception e) {
            return getElement().getText();
        }
    }

    public String getContent() {
        try {
            return getElement().findElement(By.xpath(".//*[contains(@class, 'content')]")).getText();
        } catch (Exception e) {
            return "";
        }
    }

    /* ===== VISUAL ASSERTION ===== */

    public AccordionVisualAssert should() {
        setElement(getElement());
        return new AccordionVisualAssert(this);
    }
}
