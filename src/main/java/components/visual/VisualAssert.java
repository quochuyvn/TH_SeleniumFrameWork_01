package components.visual;

import base.BaseComponent;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * =====================================================
 * VisualAssert - Fluent API for Visual Testing
 * =====================================================
 * Generic class hỗ trợ:
 * ✅ 1. Responsive (isDisplayed)
 * ✅ 2. Position/Alignment/Size (leftOf, verticallyAlignedWith)
 * ✅ 3. Gradient/Shadow/Border Radius
 * ✅ 4. Not Covered (elementFromPoint)
 * ✅ 5. Collision Detection (overlapping)
 * ✅ 6. Font Properties (fontSize, fontFamily)
 * ✅ 7. Focus State (focused)
 * ✅ 8. Animation/Transition (hasTransition)
 * ✅ 9. Pixel Perfect Distance (pixelDistanceX)
 * ✅ 10. Multi-Resolution Support (minWidth)
 * 
 * Fluent API cho method chaining
 * =====================================================
 */
public class VisualAssert<T extends VisualAssert<T>> {

    protected final BaseComponent component;
    protected Logger logger;

    /**
     * Constructor
     * @param component BaseComponent instance để test
     */
    public VisualAssert(BaseComponent component) {
        this.component = component;
        this.logger = LogManager.getLogger(this.getClass());
    }

    /**
     * Self reference cho fluent API
     */
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    // =========================================================
    // 1️⃣ RESPONSIVE - isDisplayed()
    // =========================================================
    /**
     * Assert element is visible (displayed)
     * 📌 Dùng cho responsive design testing
     */
    public T visible() {
        try {
            boolean isVisible = component.getElement().isDisplayed();
            Assert.assertTrue(isVisible, "❌ Element không hiển thị");
            logger.debug("✅ Element visible: " + component.getElement().getTagName());
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra visible: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element is NOT visible (hidden)
     */
    public T notVisible() {
        try {
            boolean isVisible = component.getElement().isDisplayed();
            Assert.assertFalse(isVisible, "❌ Element vẫn hiển thị");
            logger.debug("✅ Element hidden");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra hidden: " + e.getMessage());
            throw e;
        }
        return self();
    }

    // =========================================================
    // 2️⃣ POSITION / ALIGNMENT / SIZE
    // =========================================================
    /**
     * Assert element is to the LEFT of another element
     * 📌 Kiểm tra vị trí theo trục X
     */
    public T leftOf(BaseComponent other) {
        try {
            int myX = component.getRect().x;
            int otherX = other.getRect().x;
            Assert.assertTrue(myX < otherX,
                    "❌ Element không ở bên trái. myX=" + myX + ", otherX=" + otherX);
            logger.debug("✅ Element ở bên trái: myX=" + myX + " < otherX=" + otherX);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra leftOf: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element is ABOVE another element
     * 📌 Kiểm tra vị trí theo trục Y
     */
    public T above(BaseComponent other) {
        try {
            int myY = component.getRect().y;
            int otherY = other.getRect().y;
            Assert.assertTrue(myY < otherY,
                    "❌ Element không ở trên. myY=" + myY + ", otherY=" + otherY);
            logger.debug("✅ Element ở trên: myY=" + myY + " < otherY=" + otherY);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra above: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element is VERTICALLY aligned with another element
     * 📌 Kiểm tra căn chỉnh dọc (Y coordinates gần nhau)
     */
    public T verticallyAlignedWith(BaseComponent other, int tolerance) {
        try {
            int myY = component.getRect().y;
            int otherY = other.getRect().y;
            int diff = Math.abs(myY - otherY);
            Assert.assertTrue(diff <= tolerance,
                    "❌ Không căn chỉnh dọc. Chênh lệch=" + diff + "px, tolerance=" + tolerance + "px");
            logger.debug("✅ Căn chỉnh dọc: chênh lệch=" + diff + "px (tolerance=" + tolerance + "px)");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra verticallyAlignedWith: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element is HORIZONTALLY aligned with another element
     * 📌 Kiểm tra căn chỉnh ngang (X coordinates gần nhau)
     */
    public T horizontallyAlignedWith(BaseComponent other, int tolerance) {
        try {
            int myX = component.getRect().x;
            int otherX = other.getRect().x;
            int diff = Math.abs(myX - otherX);
            Assert.assertTrue(diff <= tolerance,
                    "❌ Không căn chỉnh ngang. Chênh lệch=" + diff + "px, tolerance=" + tolerance + "px");
            logger.debug("✅ Căn chỉnh ngang: chênh lệch=" + diff + "px (tolerance=" + tolerance + "px)");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra horizontallyAlignedWith: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has minimum width
     * 📌 Dùng cho responsive design (multi-resolution)
     */
    public T minWidth(int px) {
        try {
            int width = component.getRect().width;
            Assert.assertTrue(width >= px,
                    "❌ Chiều rộng quá nhỏ. width=" + width + "px, minWidth=" + px + "px");
            logger.debug("✅ Chiều rộng đủ: width=" + width + "px >= minWidth=" + px + "px");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra minWidth: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has specific width (pixel-perfect)
     */
    public T width(int px) {
        try {
            int width = component.getRect().width;
            Assert.assertEquals(width, px,
                    "❌ Chiều rộng không khớp. width=" + width + "px, expected=" + px + "px");
            logger.debug("✅ Chiều rộng đúng: width=" + width + "px");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra width: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has specific height
     */
    public T height(int px) {
        try {
            int height = component.getRect().height;
            Assert.assertEquals(height, px,
                    "❌ Chiều cao không khớp. height=" + height + "px, expected=" + px + "px");
            logger.debug("✅ Chiều cao đúng: height=" + height + "px");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra height: " + e.getMessage());
            throw e;
        }
        return self();
    }

    // =========================================================
    // 3️⃣ GRADIENT / SHADOW / BORDER RADIUS
    // =========================================================
    /**
     * Assert element has gradient background
     * 📌 Kiểm tra CSS background-image
     */
    public T hasGradient() {
        try {
            String bgImage = component.getCss("background-image");
            Assert.assertTrue(bgImage.contains("gradient"),
                    "❌ Không có gradient. background-image=" + bgImage);
            logger.debug("✅ Có gradient: " + bgImage);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra hasGradient: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has shadow
     * 📌 Kiểm tra box-shadow CSS
     */
    public T hasShadow() {
        try {
            String boxShadow = component.getCss("box-shadow");
            Assert.assertNotEquals(boxShadow, "none",
                    "❌ Không có shadow. box-shadow=" + boxShadow);
            Assert.assertFalse(boxShadow.isEmpty(),
                    "❌ box-shadow trống");
            logger.debug("✅ Có shadow: " + boxShadow);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra hasShadow: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has specific border radius
     * 📌 Kiểm tra border-radius CSS
     */
    public T borderRadius(int px) {
        try {
            String borderRadius = component.getCss("border-radius");
            Assert.assertTrue(borderRadius.contains(px + "px"),
                    "❌ Border radius không khớp. border-radius=" + borderRadius + ", expected=" + px + "px");
            logger.debug("✅ Border radius đúng: " + borderRadius);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra borderRadius: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has NO border
     */
    public T noBorder() {
        try {
            String border = component.getCss("border");
            Assert.assertTrue(border.contains("none") || border.isEmpty(),
                    "❌ Element có border. border=" + border);
            logger.debug("✅ Không có border");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra noBorder: " + e.getMessage());
            throw e;
        }
        return self();
    }

    // =========================================================
    // 4️⃣ NOT COVERED - elementFromPoint()
    // =========================================================
    /**
     * Assert element is NOT covered by other elements
     * 📌 Dùng elementFromPoint để kiểm tra element có phải là top layer
     */
    public T notCovered() {
        try {
            WebElement elem = component.getElement();
            Rectangle rect = component.getRect();

            // Lấy element ở giữa (center) của component
            WebElement topElement = (WebElement) ((JavascriptExecutor) component.getDriver())
                    .executeScript(
                            "return document.elementFromPoint(arguments[0], arguments[1]);",
                            rect.x + rect.width / 2,
                            rect.y + rect.height / 2
                    );

            Assert.assertEquals(topElement, elem,
                    "❌ Element bị che phủ bởi element khác");
            logger.debug("✅ Element không bị che phủ");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra notCovered: " + e.getMessage());
            throw e;
        }
        return self();
    }

    // =========================================================
    // 5️⃣ COLLISION DETECTION - overlapping()
    // =========================================================
    /**
     * Assert element is NOT overlapping with another element
     * 📌 Kiểm tra collision detection giữa 2 element
     */
    public T notOverlapping(BaseComponent other) {
        try {
            Rectangle a = component.getRect();
            Rectangle b = other.getRect();

            // AABB (Axis-Aligned Bounding Box) collision detection
            boolean overlap = a.x < b.x + b.width &&
                    a.x + a.width > b.x &&
                    a.y < b.y + b.height &&
                    a.y + a.height > b.y;

            Assert.assertFalse(overlap,
                    "❌ Element bị chồng lấn. A=" + a + ", B=" + b);
            logger.debug("✅ Element không chồng lấn");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra notOverlapping: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element IS overlapping with another element
     */
    public T overlappingWith(BaseComponent other) {
        try {
            Rectangle a = component.getRect();
            Rectangle b = other.getRect();

            boolean overlap = a.x < b.x + b.width &&
                    a.x + a.width > b.x &&
                    a.y < b.y + b.height &&
                    a.y + a.height > b.y;

            Assert.assertTrue(overlap,
                    "❌ Element không chồng lấn. A=" + a + ", B=" + b);
            logger.debug("✅ Element chồng lấn");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra overlappingWith: " + e.getMessage());
            throw e;
        }
        return self();
    }

    // =========================================================
    // 6️⃣ FONT PROPERTIES
    // =========================================================
    /**
     * Assert element has specific font size
     * 📌 Kiểm tra font-size CSS
     */
    public T fontSize(int px) {
        try {
            String fontSize = component.getCss("font-size");
            String expectedStr = px + "px";
            Assert.assertEquals(fontSize, expectedStr,
                    "❌ Font size không khớp. fontSize=" + fontSize + ", expected=" + expectedStr);
            logger.debug("✅ Font size đúng: " + fontSize);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra fontSize: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has specific font family
     * 📌 Kiểm tra font-family CSS
     */
    public T fontFamily(String font) {
        try {
            String fontFamily = component.getCss("font-family");
            Assert.assertTrue(fontFamily.contains(font),
                    "❌ Font family không khớp. fontFamily=" + fontFamily + ", expected=" + font);
            logger.debug("✅ Font family đúng: " + fontFamily);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra fontFamily: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has specific font weight
     */
    public T fontWeight(String weight) {
        try {
            String fontWeightCss = component.getCss("font-weight");
            Assert.assertEquals(fontWeightCss, weight,
                    "❌ Font weight không khớp. fontWeight=" + fontWeightCss + ", expected=" + weight);
            logger.debug("✅ Font weight đúng: " + fontWeightCss);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra fontWeight: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has specific color
     */
    public T color(String color) {
        try {
            String colorCss = component.getCss("color");
            Assert.assertTrue(colorCss.contains(color) || colorCss.equals(color),
                    "❌ Color không khớp. color=" + colorCss + ", expected=" + color);
            logger.debug("✅ Color đúng: " + colorCss);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra color: " + e.getMessage());
            throw e;
        }
        return self();
    }

    // =========================================================
    // 7️⃣ FOCUS STATE
    // =========================================================
    /**
     * Assert element is FOCUSED
     * 📌 Kiểm tra document.activeElement
     */
    public T focused() {
        try {
            WebElement activeElement = component.getDriver().switchTo().activeElement();
            Assert.assertEquals(activeElement, component.getElement(),
                    "❌ Element không có focus");
            logger.debug("✅ Element có focus");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra focused: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element is NOT focused
     */
    public T notFocused() {
        try {
            WebElement activeElement = component.getDriver().switchTo().activeElement();
            Assert.assertNotEquals(activeElement, component.getElement(),
                    "❌ Element có focus");
            logger.debug("✅ Element không có focus");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra notFocused: " + e.getMessage());
            throw e;
        }
        return self();
    }

    // =========================================================
    // 8️⃣ ANIMATION / TRANSITION
    // =========================================================
    /**
     * Assert element has CSS transition/animation
     * 📌 Kiểm tra transition CSS property
     */
    public T hasTransition() {
        try {
            String transition = component.getCss("transition");
            Assert.assertFalse(transition.isEmpty() || transition.contains("none"),
                    "❌ Không có transition. transition=" + transition);
            logger.debug("✅ Có transition: " + transition);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra hasTransition: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has animation
     */
    public T hasAnimation() {
        try {
            String animation = component.getCss("animation");
            Assert.assertFalse(animation.isEmpty() || animation.contains("none"),
                    "❌ Không có animation. animation=" + animation);
            logger.debug("✅ Có animation: " + animation);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra hasAnimation: " + e.getMessage());
            throw e;
        }
        return self();
    }

    // =========================================================
    // 9️⃣ PIXEL PERFECT - exact distance
    // =========================================================
    /**
     * Assert exact pixel distance between 2 elements (X axis)
     * 📌 Kiểm tra khoảng cách pixel hoàn toàn chính xác
     */
    public T pixelDistanceX(BaseComponent other, int px) {
        try {
            int distance = Math.abs(component.getRect().x - other.getRect().x);
            Assert.assertEquals(distance, px,
                    "❌ Khoảng cách X không chính xác. distance=" + distance + "px, expected=" + px + "px");
            logger.debug("✅ Khoảng cách X chính xác: " + distance + "px");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra pixelDistanceX: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert exact pixel distance between 2 elements (Y axis)
     */
    public T pixelDistanceY(BaseComponent other, int px) {
        try {
            int distance = Math.abs(component.getRect().y - other.getRect().y);
            Assert.assertEquals(distance, px,
                    "❌ Khoảng cách Y không chính xác. distance=" + distance + "px, expected=" + px + "px");
            logger.debug("✅ Khoảng cách Y chính xác: " + distance + "px");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra pixelDistanceY: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert pixel distance with tolerance
     */
    public T pixelDistanceX(BaseComponent other, int px, int tolerance) {
        try {
            int distance = Math.abs(component.getRect().x - other.getRect().x);
            int diff = Math.abs(distance - px);
            Assert.assertTrue(diff <= tolerance,
                    "❌ Khoảng cách X ngoài tolerance. distance=" + distance + "px, expected=" + px + "px, tolerance=" + tolerance + "px");
            logger.debug("✅ Khoảng cách X chính xác: " + distance + "px (tolerance=" + tolerance + "px)");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra pixelDistanceX: " + e.getMessage());
            throw e;
        }
        return self();
    }

    // =========================================================
    // 🔟 MULTI-RESOLUTION SUPPORT
    // =========================================================
    /**
     * Assert element has MINIMUM width (responsive)
     * 📌 Dùng cho testing multiple resolutions
     */
    public T hasMinWidth(int px) {
        try {
            int width = component.getRect().width;
            Assert.assertTrue(width >= px,
                    "❌ Chiều rộng quá nhỏ. width=" + width + "px, minWidth=" + px + "px");
            logger.debug("✅ Chiều rộng phù hợp: width=" + width + "px >= minWidth=" + px + "px");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra hasMinWidth: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has MAXIMUM width
     */
    public T hasMaxWidth(int px) {
        try {
            int width = component.getRect().width;
            Assert.assertTrue(width <= px,
                    "❌ Chiều rộng quá lớn. width=" + width + "px, maxWidth=" + px + "px");
            logger.debug("✅ Chiều rộng phù hợp: width=" + width + "px <= maxWidth=" + px + "px");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra hasMaxWidth: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has specific aspect ratio
     */
    public T aspectRatio(double ratio, double tolerance) {
        try {
            Rectangle rect = component.getRect();
            double actualRatio = (double) rect.width / rect.height;
            double diff = Math.abs(actualRatio - ratio);
            Assert.assertTrue(diff <= tolerance,
                    "❌ Aspect ratio không khớp. actual=" + actualRatio + ", expected=" + ratio + ", tolerance=" + tolerance);
            logger.debug("✅ Aspect ratio đúng: " + actualRatio);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra aspectRatio: " + e.getMessage());
            throw e;
        }
        return self();
    }

    // =========================================================
    // BONUS UTILITIES
    // =========================================================

    /**
     * Assert element has specific CSS class
     */
    public T hasClass(String className) {
        try {
            String classes = component.getAttribute("class");
            Assert.assertTrue(classes.contains(className),
                    "❌ Không có class '" + className + "'. classes=" + classes);
            logger.debug("✅ Có class: " + className);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra hasClass: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element has specific attribute
     */
    public T hasAttribute(String attrName) {
        try {
            String attrValue = component.getAttribute(attrName);
            Assert.assertNotNull(attrValue,
                    "❌ Attribute '" + attrName + "' không tồn tại");
            logger.debug("✅ Có attribute: " + attrName + "=" + attrValue);
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra hasAttribute: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Assert element is ENABLED
     */
    public T enabled() {
        try {
            boolean isEnabled = component.getElement().isEnabled();
            Assert.assertTrue(isEnabled,
                    "❌ Element bị disable");
            logger.debug("✅ Element enabled");
        } catch (Exception e) {
            logger.error("❌ Lỗi kiểm tra enabled: " + e.getMessage());
            throw e;
        }
        return self();
    }

    /**
     * Print summary info của element (để debug)
     */
    public T printInfo() {
        try {
            Rectangle rect = component.getRect();
            logger.info("📦 Element Info:");
            logger.info("   Position: x=" + rect.x + ", y=" + rect.y);
            logger.info("   Size: width=" + rect.width + "px, height=" + rect.height + "px");
            logger.info("   CSS background-color: " + component.getCss("background-color"));
            logger.info("   CSS color: " + component.getCss("color"));
            logger.info("   CSS font-size: " + component.getCss("font-size"));
        } catch (Exception e) {
            logger.error("❌ Lỗi printInfo: " + e.getMessage());
        }
        return self();
    }
}

