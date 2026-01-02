package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HighlightUtil {

    private static boolean isHighlightOn() {
        return Boolean.parseBoolean(
                ConfigReader.get("highlight", "false"));
    }

    private static int getDelay() {
        return Integer.parseInt(
                ConfigReader.get("highlight.delay", "300"));
    }

    private static int getTimes() {
        return Integer.parseInt(
                ConfigReader.get("highlight.times", "2"));
    }

    private static String getColor() {
        return ConfigReader.get("highlight.color", "#0000FF");
    }

    public static void blink(WebDriver driver, WebElement element) {
        if (!isHighlightOn() || element == null) return;

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String originalStyle = element.getAttribute("style");

        for (int i = 0; i < getTimes(); i++) {

            // Bật border theo màu config
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element,
                    originalStyle + "; border: 2px solid " + getColor() + ";"
            );
            sleep(getDelay());

            // Tắt border
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element,
                    originalStyle
            );
            sleep(getDelay());
        }

        // Restore style gốc
        js.executeScript(
                "arguments[0].setAttribute('style', arguments[1]);",
                element,
                originalStyle
        );
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
