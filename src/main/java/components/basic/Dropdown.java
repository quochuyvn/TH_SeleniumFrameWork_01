package components.basic;

import base.BaseComponent;
import components.visual.DropdownVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Dropdown Component
 * Represents a select dropdown element
 */
public class Dropdown extends BaseComponent {

    public Dropdown(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Dropdown(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void selectByValue(String value) {
        new Select(getElement()).selectByValue(value);
    }

    public void selectByText(String text) {
        new Select(getElement()).selectByVisibleText(text);
    }

    public void selectByIndex(int index) {
        new Select(getElement()).selectByIndex(index);
    }

    public String getSelectedValue() {
        return new Select(getElement()).getFirstSelectedOption().getAttribute("value");
    }

    public String getSelectedText() {
        return new Select(getElement()).getFirstSelectedOption().getText();
    }

    /* ===== VISUAL ASSERTION ===== */

    public DropdownVisualAssert should() {
        setElement(getElement());
        return new DropdownVisualAssert(this);
    }
}
