package components.visual;

import components.basic.Modal;

/**
 * ModalVisualAssert
 * Fluent assertions for Modal component
 */
public class ModalVisualAssert extends VisualAssert<ModalVisualAssert> {

    private final Modal modal;

    public ModalVisualAssert(Modal modal) {
        super(modal);
        this.modal = modal;
    }

    /* ===== MODAL-SPECIFIC ASSERTIONS ===== */

    public ModalVisualAssert isDisplayed() {
        assert modal.isDisplayed() : "Modal is not displayed";
        return this;
    }

    public ModalVisualAssert isHidden() {
        assert !modal.isDisplayed() : "Modal is displayed";
        return this;
    }

    public ModalVisualAssert hasBackdrop() {
        return hasClass("modal-backdrop");
    }

    public ModalVisualAssert hasCenterAlignment() {
        return hasClass("modal-dialog-centered");
    }

    public ModalVisualAssert isLarge() {
        return hasClass("modal-lg");
    }

    public ModalVisualAssert isSmall() {
        return hasClass("modal-sm");
    }
}
