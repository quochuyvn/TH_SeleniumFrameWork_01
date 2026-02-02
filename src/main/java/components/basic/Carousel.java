package components.basic;

import base.BaseComponent;
import components.visual.CarouselVisualAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Carousel/Slider Component
 * Represents a carousel or slider element
 */
public class Carousel extends BaseComponent {

    public Carousel(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Carousel(WebDriver driver) {
        super(driver);
    }

    /* ===== ACTION ===== */

    public void nextSlide() {
        try {
            getElement().findElement(By.xpath(".//button[contains(@class, 'next')]")).click();
        } catch (Exception e) {
            logger.warn("Next button not found");
        }
    }

    public void previousSlide() {
        try {
            getElement().findElement(By.xpath(".//button[contains(@class, 'prev')]")).click();
        } catch (Exception e) {
            logger.warn("Previous button not found");
        }
    }

    public void goToSlide(int slideIndex) {
        List<WebElement> indicators = getSlideIndicators();
        if (slideIndex < indicators.size()) {
            indicators.get(slideIndex).click();
        }
    }

    public List<WebElement> getSlides() {
        return getElement().findElements(By.xpath(".//div[@class*='slide']"));
    }

    public List<WebElement> getSlideIndicators() {
        return getElement().findElements(By.xpath(".//button[@class*='indicator'] | .//div[@class*='dot']"));
    }

    public int getCurrentSlideIndex() {
        List<WebElement> indicators = getSlideIndicators();
        for (int i = 0; i < indicators.size(); i++) {
            String classAttr = indicators.get(i).getAttribute("class");
            if (classAttr != null && classAttr.contains("active")) {
                return i;
            }
        }
        return 0;
    }

    /* ===== VISUAL ASSERTION ===== */

    public CarouselVisualAssert should() {
        setElement(getElement());
        return new CarouselVisualAssert(this);
    }
}
