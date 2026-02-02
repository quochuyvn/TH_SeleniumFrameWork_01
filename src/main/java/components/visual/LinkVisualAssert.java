package components.visual;

import components.basic.Link;

/**
 * LinkVisualAssert
 * Fluent assertions for Link component
 */
public class LinkVisualAssert extends VisualAssert<LinkVisualAssert> {

    private final Link link;

    public LinkVisualAssert(Link link) {
        super(link);
        this.link = link;
    }

    /* ===== LINK-SPECIFIC ASSERTIONS ===== */

    public LinkVisualAssert hasText(String text) {
        String actualText = link.getText();
        assert actualText != null && actualText.equals(text) : "Text mismatch";
        return this;
    }

    public LinkVisualAssert hasHref(String href) {
        String actualHref = link.getHref();
        assert actualHref != null && actualHref.equals(href) : "Href mismatch";
        return this;
    }

    public LinkVisualAssert hrefContains(String urlPart) {
        String actualHref = link.getHref();
        assert actualHref != null && actualHref.contains(urlPart) : "Href doesn't contain: " + urlPart;
        return this;
    }

    public LinkVisualAssert hasTarget(String target) {
        String actualTarget = link.getElement().getAttribute("target");
        assert actualTarget != null && actualTarget.equals(target) : "Target mismatch";
        return this;
    }

    public LinkVisualAssert isExternal() {
        String href = link.getHref();
        assert href != null && href.startsWith("http") : "Link is not external";
        return this;
    }

    public LinkVisualAssert isInternal() {
        String href = link.getHref();
        assert href != null && !href.startsWith("http") : "Link is external";
        return this;
    }
}
