package components.visual;

import components.basic.Card;

/**
 * CardVisualAssert
 * Fluent assertions for Card component
 */
public class CardVisualAssert extends VisualAssert<CardVisualAssert> {

    private final Card card;

    public CardVisualAssert(Card card) {
        super(card);
        this.card = card;
    }

    /* ===== CARD-SPECIFIC ASSERTIONS ===== */

    public CardVisualAssert hasTitle(String title) {
        String actualTitle = card.getTitle();
        assert actualTitle.equals(title) : "Title mismatch";
        return this;
    }

    public CardVisualAssert titleContains(String text) {
        String actualTitle = card.getTitle();
        assert actualTitle.contains(text) : "Title doesn't contain: " + text;
        return this;
    }

    public CardVisualAssert hasContent(String content) {
        String actualContent = card.getContent();
        assert actualContent.equals(content) : "Content mismatch";
        return this;
    }

    public CardVisualAssert contentContains(String text) {
        String actualContent = card.getContent();
        assert actualContent.contains(text) : "Content doesn't contain: " + text;
        return this;
    }

    public CardVisualAssert isClickable() {
        card.click();
        return this;
    }

    public CardVisualAssert hasImage() {
        boolean hasImage = card.getElement().findElements(org.openqa.selenium.By.tagName("img")).size() > 0;
        assert hasImage : "Card doesn't have image";
        return this;
    }

    public CardVisualAssert hasFooter() {
        boolean hasFooter = card.getElement().findElements(org.openqa.selenium.By.cssSelector("[class*='footer']")).size() > 0;
        assert hasFooter : "Card doesn't have footer";
        return this;
    }
}
