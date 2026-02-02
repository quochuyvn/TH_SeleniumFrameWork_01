package components.visual;

import components.basic.Carousel;

/**
 * CarouselVisualAssert
 * Fluent assertions for Carousel component
 */
public class CarouselVisualAssert extends VisualAssert<CarouselVisualAssert> {

    private final Carousel carousel;

    public CarouselVisualAssert(Carousel carousel) {
        super(carousel);
        this.carousel = carousel;
    }

    /* ===== CAROUSEL-SPECIFIC ASSERTIONS ===== */

    public CarouselVisualAssert hasSlides(int expectedCount) {
        int actualCount = carousel.getSlides().size();
        assert actualCount == expectedCount : "Slides count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount;
        return this;
    }

    public CarouselVisualAssert currentSlideIndexIs(int expectedIndex) {
        int actualIndex = carousel.getCurrentSlideIndex();
        assert actualIndex == expectedIndex : "Current slide index mismatch";
        return this;
    }

    public CarouselVisualAssert hasSlideIndicators(int expectedCount) {
        int actualCount = carousel.getSlideIndicators().size();
        assert actualCount == expectedCount : "Slide indicators count mismatch";
        return this;
    }

    public CarouselVisualAssert hasNextButton() {
        boolean hasNext = carousel.getElement().findElements(org.openqa.selenium.By.cssSelector("[class*='next']")).size() > 0;
        assert hasNext : "Carousel doesn't have next button";
        return this;
    }

    public CarouselVisualAssert hasPreviousButton() {
        boolean hasPrev = carousel.getElement().findElements(org.openqa.selenium.By.cssSelector("[class*='prev']")).size() > 0;
        assert hasPrev : "Carousel doesn't have previous button";
        return this;
    }

    public CarouselVisualAssert isAutoplay() {
        return hasClass("autoplay");
    }

    public CarouselVisualAssert hasMultipleItems() {
        return hasClass("multiple-items");
    }
}
