package components.visual;

import components.basic.ProgressBar;

/**
 * ProgressBarVisualAssert
 * Fluent assertions for ProgressBar component
 */
public class ProgressBarVisualAssert extends VisualAssert<ProgressBarVisualAssert> {

    private final ProgressBar progressBar;

    public ProgressBarVisualAssert(ProgressBar progressBar) {
        super(progressBar);
        this.progressBar = progressBar;
    }

    /* ===== PROGRESS BAR-SPECIFIC ASSERTIONS ===== */

    public ProgressBarVisualAssert progressIs(int expectedProgress) {
        int actualProgress = progressBar.getProgress();
        assert actualProgress == expectedProgress : "Progress mismatch. Expected: " + expectedProgress + "%, Actual: " + actualProgress + "%";
        return this;
    }

    public ProgressBarVisualAssert progressIsAtLeast(int minProgress) {
        int actualProgress = progressBar.getProgress();
        assert actualProgress >= minProgress : "Progress is less than expected";
        return this;
    }

    public ProgressBarVisualAssert hasLabel(String expectedLabel) {
        String actualLabel = progressBar.getLabel();
        assert actualLabel.equals(expectedLabel) : "Label mismatch";
        return this;
    }

    public ProgressBarVisualAssert labelContains(String text) {
        String actualLabel = progressBar.getLabel();
        assert actualLabel.contains(text) : "Label doesn't contain: " + text;
        return this;
    }

    public ProgressBarVisualAssert isAnimated() {
        return hasClass("animated");
    }

    public ProgressBarVisualAssert isStriped() {
        return hasClass("striped");
    }

    public ProgressBarVisualAssert isSuccess() {
        return hasClass("success");
    }

    public ProgressBarVisualAssert isWarning() {
        return hasClass("warning");
    }

    public ProgressBarVisualAssert isDanger() {
        return hasClass("danger");
    }
}
