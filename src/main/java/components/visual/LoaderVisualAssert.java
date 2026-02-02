package components.visual;

import components.basic.Loader;

/**
 * LoaderVisualAssert
 * Fluent assertions for Loader component
 */
public class LoaderVisualAssert extends VisualAssert<LoaderVisualAssert> {

    private final Loader loader;

    public LoaderVisualAssert(Loader loader) {
        super(loader);
        this.loader = loader;
    }

    /* ===== LOADER-SPECIFIC ASSERTIONS ===== */

    public LoaderVisualAssert isLoading() {
        assert loader.isLoading() : "Loader is not loading";
        return this;
    }

    public LoaderVisualAssert isNotLoading() {
        assert !loader.isLoading() : "Loader is still loading";
        return this;
    }

    public LoaderVisualAssert hasLoadingText(String expectedText) {
        String actualText = loader.getLoadingText();
        assert actualText.equals(expectedText) : "Loading text mismatch";
        return this;
    }

    public LoaderVisualAssert loadingTextContains(String text) {
        String actualText = loader.getLoadingText();
        assert actualText.contains(text) : "Loading text doesn't contain: " + text;
        return this;
    }

    public LoaderVisualAssert isSpinner() {
        return hasClass("spinner");
    }

    public LoaderVisualAssert isDots() {
        return hasClass("dots");
    }

    public LoaderVisualAssert isBar() {
        return hasClass("bar");
    }
}
