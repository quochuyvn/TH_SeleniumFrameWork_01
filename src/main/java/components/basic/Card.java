package components.basic;

import base.BaseComponent;
import components.visual.CardVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Card/Panel Component
 * Represents a card or panel UI block
 */
public class Card extends BaseComponent {

    public Card(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Card(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public String getTitle() {
        try {
            return getElement().findElement(By.cssSelector("[class*='title']")).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getContent() {
        try {
            return getElement().findElement(By.cssSelector("[class*='content']")).getText();
        } catch (Exception e) {
            return getElement().getText();
        }
    }

    public void click() {
        getElement().click();
    }

    /* ===== VISUAL ASSERTION ===== */

    public CardVisualAssert should() {
        setElement(getElement());
        return new CardVisualAssert(this);
    }
}
