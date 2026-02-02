package components.visual;

import components.basic.Banner;

/**
 * BannerVisualAssert
 * Fluent assertions for Banner component
 */
public class BannerVisualAssert extends VisualAssert<BannerVisualAssert> {

    private final Banner banner;

    public BannerVisualAssert(Banner banner) {
        super(banner);
        this.banner = banner;
    }

    /* ===== BANNER-SPECIFIC ASSERTIONS ===== */

    public BannerVisualAssert isDisplayed() {
        assert banner.isVisible() : "Banner is not visible";
        return this;
    }

    public BannerVisualAssert hasTitle(String title) {
        String actualTitle = banner.getTitle();
        assert actualTitle.equals(title) : "Title mismatch";
        return this;
    }

    public BannerVisualAssert titleContains(String text) {
        String actualTitle = banner.getTitle();
        assert actualTitle.contains(text) : "Title doesn't contain: " + text;
        return this;
    }

    public BannerVisualAssert isPromotional() {
        return hasClass("promotional");
    }

    public BannerVisualAssert isHeader() {
        return hasClass("header");
    }

    public BannerVisualAssert hasClosable() {
        return hasClass("closable");
    }
}
