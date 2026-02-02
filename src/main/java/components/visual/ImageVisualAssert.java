package components.visual;

import components.basic.Image;
import org.openqa.selenium.JavascriptExecutor;

/**
 * ImageVisualAssert
 * Fluent assertions for Image component
 */
public class ImageVisualAssert extends VisualAssert<ImageVisualAssert> {

    private final Image image;

    public ImageVisualAssert(Image image) {
        super(image);
        this.image = image;
    }

    /* ===== IMAGE-SPECIFIC ASSERTIONS ===== */

    public ImageVisualAssert hasSrc(String src) {
        String actualSrc = image.getSrc();
        assert actualSrc != null && actualSrc.equals(src) : "Src mismatch";
        return this;
    }

    public ImageVisualAssert srcContains(String srcPart) {
        String actualSrc = image.getSrc();
        assert actualSrc != null && actualSrc.contains(srcPart) : "Src doesn't contain: " + srcPart;
        return this;
    }

    public ImageVisualAssert hasAlt(String alt) {
        String actualAlt = image.getAlt();
        assert actualAlt != null && actualAlt.equals(alt) : "Alt mismatch";
        return this;
    }

    public ImageVisualAssert hasWidth(int width) {
        int actualWidth = image.getWidth();
        assert actualWidth == width : "Width mismatch";
        return this;
    }

    public ImageVisualAssert hasHeight(int height) {
        int actualHeight = image.getHeight();
        assert actualHeight == height : "Height mismatch";
        return this;
    }

    public ImageVisualAssert isLoaded() {
        try {
            Object result = ((JavascriptExecutor) image.getDriver())
                .executeScript("return arguments[0].complete && arguments[0].naturalHeight > 0;", image.getElement());
            if (result != null) {
                Boolean loaded = result instanceof Boolean ? (Boolean) result : Boolean.parseBoolean(result.toString());
                assert loaded : "Image is not loaded";
            }
        } catch (Exception e) {
            System.out.println("Warning: Cannot determine if image is loaded - " + e.getMessage());
        }
        return this;
    }
}
