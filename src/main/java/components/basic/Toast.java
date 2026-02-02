package components.basic;

import base.BaseComponent;
import components.visual.ToastVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Toast Component
 * Represents a toast/notification message
 */
public class Toast extends BaseComponent {

    public Toast(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Toast(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public String getMessage() {
        return getElement().getText();
    }

    public boolean isSuccess() {
        return hasClass("success");
    }

    public boolean isError() {
        return hasClass("error");
    }

    public boolean isWarning() {
        return hasClass("warning");
    }

    private boolean hasClass(String className) {
        String classes = getAttribute("class");
        return classes != null && classes.contains(className);
    }

    /* ===== VISUAL ASSERTION ===== */

    public ToastVisualAssert should() {
        setElement(getElement());
        return new ToastVisualAssert(this);
    }
}
