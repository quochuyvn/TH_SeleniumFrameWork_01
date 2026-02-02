package utils;

import config.ConfigKeys;
import config.ConfigReader;
import org.openqa.selenium.*;

public class HighlightUtil {

    private static final boolean ENABLED =
            ConfigReader.getBoolean(ConfigKeys.HIGHLIGHT_ENABLED, true);

    private static final String BORDER_COLOR =
            ConfigReader.get(ConfigKeys.HIGHLIGHT_BORDER_COLOR);

    private static final int TIMES =
            ConfigReader.getInt(ConfigKeys.HIGHLIGHT_TIMES, 1);

    private static final long DELAY =
            ConfigReader.getInt(ConfigKeys.HIGHLIGHT_DELAY, 200);

    private HighlightUtil() {
    }

    public static void highlight(WebDriver driver, WebElement element) {
        if (!ENABLED) return;

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String originalStyle = element.getAttribute("style");

            String borderStyle = "3px solid " + BORDER_COLOR;

            for (int i = 0; i < TIMES; i++) {
                js.executeScript(
                        "arguments[0].setAttribute('style', arguments[1]);",
                        element,
                        "border:" + borderStyle + ";"
                );
                sleep(DELAY);

                js.executeScript(
                        "arguments[0].setAttribute('style', arguments[1]);",
                        element,
                        originalStyle
                );
                sleep(DELAY);
            }
        } catch (StaleElementReferenceException ignored) {
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
