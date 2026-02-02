package components.examples;

import base.BaseComponent;
import components.visual.VisualAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * =====================================================
 * Example Component - Demo Visual Testing
 * =====================================================
 * Ví dụ sử dụng BaseComponent + VisualAssert
 * =====================================================
 */
public class HeaderComponent extends BaseComponent {

    @FindBy(css = ".header-logo")
    private WebElement logo;

    @FindBy(css = ".header-title")
    private WebElement title;

    @FindBy(css = ".header-button")
    private WebElement button;

    /**
     * Constructor
     */
    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    /**
     * Get VisualAssert for logo testing
     */
    public VisualAssert<?> assertLogoVisual() {
        setElement(logo);
        return new VisualAssert<>(this);
    }

    /**
     * Get VisualAssert for title testing
     */
    public VisualAssert<?> assertTitleVisual() {
        setElement(title);
        return new VisualAssert<>(this);
    }

    /**
     * Get VisualAssert for button testing
     */
    public VisualAssert<?> assertButtonVisual() {
        setElement(button);
        return new VisualAssert<>(this);
    }

    /**
     * Get Component for direct element access
     */
    public BaseComponent getLogoComponent() {
        setElement(logo);
        return this;
    }

    public BaseComponent getTitleComponent() {
        setElement(title);
        return this;
    }

    public BaseComponent getButtonComponent() {
        setElement(button);
        return this;
    }
}
