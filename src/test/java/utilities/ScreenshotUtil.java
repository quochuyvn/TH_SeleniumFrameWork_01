package utilities;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/**
 * =========================================================
 * ScreenshotUtil
 * ---------------------------------------------------------
 * Chức năng:
 * 1. Chụp viewport (màn hình đang thấy)
 * 2. Chụp full page (AShot)
 * 3. Tự động fallback về viewport nếu full page bị lỗi
 * 4. Chụp WebElement
 * 5. Chụp WebElement có highlight
 *
 * Áp dụng tốt cho:
 * - TestNG Listener
 * - Extent Report
 * - Selenium Grid / CI
 * =========================================================
 */
public class ScreenshotUtil {

    /**
     * Thư mục lưu screenshot
     */
    private static final String SCREENSHOT_FOLDER =
            System.getProperty("user.dir") + "/screenshots/";

    /* =====================================================
     * COMMON METHOD
     * ===================================================== */

    /**
     * Sinh ra đường dẫn file screenshot theo timestamp
     */
    private static String generatePath(String name) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        return SCREENSHOT_FOLDER + name + "_" + timeStamp + ".png";
    }

    /* =====================================================
     * 1. VIEWPORT SCREENSHOT
     * ===================================================== */

    /**
     * Chụp màn hình hiện tại (viewport)
     * → Cách này LUÔN an toàn, Selenium native
     */
    public static String captureViewport(WebDriver driver, String testName)
            throws IOException {

        String path = generatePath(testName);

        // Ép driver sang TakesScreenshot
        TakesScreenshot ts = (TakesScreenshot) driver;

        // Chụp screenshot dưới dạng file
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Copy file sang thư mục screenshots
        FileUtils.copyFile(source, new File(path));

        return path;
    }

    /* =====================================================
     * 2. FULL PAGE SCREENSHOT (WITH FALLBACK)
     * ===================================================== */

    /**
     * Chụp toàn bộ trang (scroll từ trên xuống dưới)
     *
     * ⚠ Có thể fail trong các trường hợp:
     * - Selenium Grid
     * - Page quá nặng
     * - JS error
     *
     * 👉 Nếu FAIL → tự động fallback sang viewport
     */
    public static String captureFullPageWithFallback(
            WebDriver driver,
            String testName) {

        String path = generatePath(testName);

        try {
            // ===============================
            // Dùng AShot để chụp full page
            // ===============================
        	Screenshot screenshot = new AShot()
        	        .shootingStrategy(
        	                ShootingStrategies.viewportPasting(300))
        	        .takeScreenshot(driver);

            // Ghi ảnh ra file
            ImageIO.write(screenshot.getImage(), "PNG", new File(path));

            return path;

        } catch (Exception e) {

            // ===============================
            // Nếu FULL PAGE FAIL
            // → fallback về viewport
            // ===============================
            System.out.println(
                    "⚠ Full page screenshot failed. Fallback to viewport."
            );
            System.out.println("Reason: " + e.getMessage());

            try {
                return captureViewport(driver, testName + "_VIEWPORT");
            } catch (IOException ioException) {
                throw new RuntimeException(
                        "❌ Cannot capture any screenshot", ioException);
            }
        }
    }

    /* =====================================================
     * 3. WEBELEMENT SCREENSHOT
     * ===================================================== */

    /**
     * Chụp screenshot riêng 1 WebElement
     * Selenium 4 hỗ trợ trực tiếp
     */
    public static String captureElement(
            WebElement element,
            String elementName)
            throws IOException {

        String path = generatePath(elementName);

        // Selenium 4: WebElement có thể chụp screenshot trực tiếp
        File source = element.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File(path));

        return path;
    }

    /* =====================================================
     * 4. WEBELEMENT SCREENSHOT WITH HIGHLIGHT
     * ===================================================== */

    /**
     * Highlight element → chụp → remove highlight
     * Rất hữu ích khi debug & report
     */
    public static String captureElementWithHighlight(
            WebDriver driver,
            WebElement element,
            String elementName)
            throws IOException {

        // Highlight element trước
        highlightElement(driver, element);

        String path = generatePath(elementName);

        File source = element.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File(path));

        // Restore lại style
        removeHighlight(driver, element);

        return path;
    }

    /* =====================================================
     * 5. HIGHLIGHT UTILITY
     * ===================================================== */

    /**
     * Highlight WebElement bằng JavaScript
     */
    private static void highlightElement(
            WebDriver driver,
            WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].setAttribute('style', " +
                        "'border: 3px solid red; background: yellow;');",
                element);
    }

    /**
     * Xóa highlight để tránh ảnh hưởng UI
     */
    private static void removeHighlight(
            WebDriver driver,
            WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].setAttribute('style', '');",
                element);
    }
}
