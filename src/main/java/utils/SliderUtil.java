package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * =====================================================
 * SliderUtil
 * =====================================================
 * - Chịu trách nhiệm toàn bộ logic Slider
 * - Không chứa assert framework-specific (TestNG/JUnit)
 * - Có thể reuse cross-project
 * =====================================================
 */
public class SliderUtil {

    private final WebDriver driver;
    private final Actions actions;

    public SliderUtil(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    /**
     * 🔥 Set slider value bằng UI (drag & drop)
     */
    public void setSliderValue(WebElement slider, int value) {

        String minAttr = slider.getAttribute("min");
        String maxAttr = slider.getAttribute("max");
        
        if (minAttr == null || maxAttr == null) {
            throw new IllegalArgumentException("Slider must have min and max attributes");
        }
        
        int min = Integer.parseInt(minAttr);
        int max = Integer.parseInt(maxAttr);

        if (value < min || value > max) {
            throw new IllegalArgumentException(
                    "Slider value must be between " + min + " and " + max
            );
        }

        int percentage = (value - min) * 100 / (max - min);
        moveSliderByPercentage(slider, percentage);
    }

    /**
     * 🔥 Move slider by percentage (0–100)
     */
    public void moveSliderByPercentage(WebElement slider, int percentage) {

        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException(
                    "Percentage must be between 0 and 100"
            );
        }

        int width = slider.getSize().getWidth();
        int xOffset = (int) Math.round(width * (percentage / 100.0));

        actions.moveToElement(slider, 1, slider.getSize().getHeight() / 2)
               .clickAndHold()
               .moveByOffset(xOffset, 0)
               .release()
               .perform();
    }

    /**
     * 🔥 Set slider value bằng JavaScript
     */
    public void setSliderValueByJS(WebElement slider, int value) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "arguments[0].value = arguments[1];" +
            "arguments[0].dispatchEvent(new Event('input'));" +
            "arguments[0].dispatchEvent(new Event('change'));",
            slider, value
        );
    }

    /**
     * 🔥 Assert slider value (absolute)
     */
    public void assertSliderValue(WebElement slider, int expected) {

        String valueAttr = slider.getAttribute("value");
        if (valueAttr == null) {
            throw new AssertionError("❌ Slider does not have a value attribute");
        }
        
        int actual = Integer.parseInt(valueAttr);

        if (actual != expected) {
            throw new AssertionError(
                "❌ Slider value mismatch. Expected: "
                + expected + ", Actual: " + actual
            );
        }
    }
    
    /**
     * 🔥 Verify slider value by percentage
     */
    public void assertSliderPercentage(WebElement slider, int expectedPercentage) {

        String minAttr = slider.getAttribute("min");
        String maxAttr = slider.getAttribute("max");
        String valueAttr = slider.getAttribute("value");
        
        if (minAttr == null || maxAttr == null || valueAttr == null) {
            throw new AssertionError("Slider must have min, max, and value attributes");
        }
        
        int min = Integer.parseInt(minAttr);
        int max = Integer.parseInt(maxAttr);
        int value = Integer.parseInt(valueAttr);

        int actualPercentage = (value - min) * 100 / (max - min);

        if (actualPercentage != expectedPercentage) {
            throw new AssertionError(
                    "Slider percentage mismatch. Expected: " + expectedPercentage +
                    ", Actual: " + actualPercentage
            );
        }
    }


    /**
     * 🔥 Assert slider value with tolerance
     */
    public void assertSliderValue(WebElement slider, int expected, int tolerance) {

        String valueAttr = slider.getAttribute("value");
        if (valueAttr == null) {
            throw new AssertionError("❌ Slider does not have a value attribute");
        }
        
        int actual = Integer.parseInt(valueAttr);

        if (Math.abs(actual - expected) > tolerance) {
            throw new AssertionError(
                "❌ Slider value mismatch. Expected: "
                + expected + " ±" + tolerance +
                ", Actual: " + actual
            );
        }
    }
}
