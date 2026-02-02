package components.visual;

import components.basic.Alert;

/**
 * AlertVisualAssert
 * Fluent assertions for Alert component
 */
public class AlertVisualAssert extends VisualAssert<AlertVisualAssert> {

    private final Alert alert;

    public AlertVisualAssert(Alert alert) {
        super(alert);
        this.alert = alert;
    }

    /* ===== ALERT-SPECIFIC ASSERTIONS ===== */

    public AlertVisualAssert hasMessage(String expectedMessage) {
        String actualMessage = alert.getAlertMessage();
        assert actualMessage.equals(expectedMessage) : "Alert message mismatch";
        return this;
    }

    public AlertVisualAssert messageContains(String text) {
        String actualMessage = alert.getAlertMessage();
        assert actualMessage.contains(text) : "Alert message doesn't contain: " + text;
        return this;
    }

    public AlertVisualAssert isSuccess() {
        assert alert.isSuccess() : "Alert is not success";
        return this;
    }

    public AlertVisualAssert isError() {
        assert alert.isError() : "Alert is not error";
        return this;
    }

    public AlertVisualAssert isWarning() {
        assert alert.isWarning() : "Alert is not warning";
        return this;
    }

    public AlertVisualAssert isInfo() {
        assert alert.isInfo() : "Alert is not info";
        return this;
    }

    public AlertVisualAssert hasDismissButton() {
        return hasClass("alert-dismissible");
    }

    public AlertVisualAssert isPrimary() {
        return hasClass("alert-primary");
    }

    public AlertVisualAssert hasIcon() {
        boolean hasIcon = alert.getElement().findElements(org.openqa.selenium.By.cssSelector("[class*='icon']")).size() > 0;
        assert hasIcon : "Alert doesn't have icon";
        return this;
    }
}
