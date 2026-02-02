package components.visual;

import components.basic.Toast;

/**
 * ToastVisualAssert
 * Fluent assertions for Toast component
 */
public class ToastVisualAssert extends VisualAssert<ToastVisualAssert> {

    private final Toast toast;

    public ToastVisualAssert(Toast toast) {
        super(toast);
        this.toast = toast;
    }

    /* ===== TOAST-SPECIFIC ASSERTIONS ===== */

    public ToastVisualAssert hasMessage(String message) {
        String actualMessage = toast.getMessage();
        assert actualMessage.equals(message) : "Message mismatch";
        return this;
    }

    public ToastVisualAssert messageContains(String text) {
        String actualMessage = toast.getMessage();
        assert actualMessage.contains(text) : "Message doesn't contain: " + text;
        return this;
    }

    public ToastVisualAssert isSuccess() {
        assert toast.isSuccess() : "Toast is not success";
        return this;
    }

    public ToastVisualAssert isError() {
        assert toast.isError() : "Toast is not error";
        return this;
    }

    public ToastVisualAssert isWarning() {
        assert toast.isWarning() : "Toast is not warning";
        return this;
    }

    public ToastVisualAssert isDismissible() {
        return hasClass("dismissible");
    }
}
