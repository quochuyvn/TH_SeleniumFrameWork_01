package components.visual;

import components.basic.Pagination;

/**
 * PaginationVisualAssert
 * Fluent assertions for Pagination component
 */
public class PaginationVisualAssert extends VisualAssert<PaginationVisualAssert> {

    private final Pagination pagination;

    public PaginationVisualAssert(Pagination pagination) {
        super(pagination);
        this.pagination = pagination;
    }

    /* ===== PAGINATION-SPECIFIC ASSERTIONS ===== */

    public PaginationVisualAssert currentPageIs(int expectedPage) {
        int actualPage = pagination.getCurrentPage();
        assert actualPage == expectedPage : "Current page mismatch. Expected: " + expectedPage + ", Actual: " + actualPage;
        return this;
    }

    public PaginationVisualAssert hasPageButtons(int expectedCount) {
        int actualCount = pagination.getElement().findElements(org.openqa.selenium.By.cssSelector("li[class='page-item']")).size();
        assert actualCount == expectedCount : "Page button count mismatch";
        return this;
    }

    public PaginationVisualAssert hasNextButton() {
        boolean hasNext = pagination.getElement().findElements(org.openqa.selenium.By.cssSelector("[aria-label*='Next']")).size() > 0;
        assert hasNext : "Pagination doesn't have next button";
        return this;
    }

    public PaginationVisualAssert hasPreviousButton() {
        boolean hasPrevious = pagination.getElement().findElements(org.openqa.selenium.By.cssSelector("[aria-label*='Previous']")).size() > 0;
        assert hasPrevious : "Pagination doesn't have previous button";
        return this;
    }

    public PaginationVisualAssert nextButtonIsEnabled() {
        org.openqa.selenium.WebElement nextBtn = pagination.getElement().findElement(org.openqa.selenium.By.cssSelector("[aria-label*='Next']"));
        String classAttr = nextBtn.getAttribute("class");
        assert classAttr != null && !classAttr.contains("disabled") : "Next button is disabled";
        return this;
    }

    public PaginationVisualAssert previousButtonIsEnabled() {
        org.openqa.selenium.WebElement prevBtn = pagination.getElement().findElement(org.openqa.selenium.By.cssSelector("[aria-label*='Previous']"));
        String classAttr = prevBtn.getAttribute("class");
        assert classAttr != null && !classAttr.contains("disabled") : "Previous button is disabled";
        return this;
    }
}
