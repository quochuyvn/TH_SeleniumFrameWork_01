package components.visual;

import components.basic.Button;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * ButtonVisualAssert
 * Fluent assertions for Button component
 */
public class ButtonVisualAssert extends VisualAssert<ButtonVisualAssert> {

    private final Button button;

    public ButtonVisualAssert(Button button) {
        super(button);
        this.button = button;
    }

    /* ===== BUTTON-SPECIFIC ASSERTIONS ===== */

    public ButtonVisualAssert isClickable() {
        WebDriver driver = button.getDriver();
        WebElement element = button.getElement();
        if (driver != null && element != null) {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(element));
        }
        return this;
    }

    public ButtonVisualAssert hasText(String text) {
        String buttonText = button.getElement().getText();
        assert buttonText != null && buttonText.contains(text) : "Button text doesn't contain: " + text;
        return this;
    }

    public ButtonVisualAssert isPrimary() {
        return hasClass("primary");
    }

    public ButtonVisualAssert isSecondary() {
        return hasClass("secondary");
    }

    public ButtonVisualAssert isDanger() {
        return hasClass("danger");
    }

    public ButtonVisualAssert isDisabled() {
        boolean disabled = button.getElement().getAttribute("disabled") != null;
        assert disabled : "Button is not disabled";
        return this;
    }

    public ButtonVisualAssert isEnabled() {
        boolean disabled = button.getElement().getAttribute("disabled") != null;
        assert !disabled : "Button is disabled";
        return this;
    }
}
