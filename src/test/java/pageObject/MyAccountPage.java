package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BasePage;

/**
 * Represents the My Account Page, which is displayed after a successful login.
 * This class provides methods to interact with elements on the My Account page.
 */
public class MyAccountPage extends BasePage {

    /**
     * Constructor for MyAccountPage.
     * @param driver The WebDriver instance.
     */
    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    private WebElement myAccountTitle;

    @FindBy(xpath = "//a[normalize-space()='Logout']")
    private WebElement btnLogout;

    /**
     * Checks if the 'My Account' title is displayed on the page.
     * @return true if the title is displayed, false otherwise.
     */
    public boolean isMyAccountPageDisplayed() {
        logStep("Verify My Account page is displayed");
        return isDisplayed(myAccountTitle);
    }

    /**
     * Clicks the 'Logout' button to log the user out.
     */
    public void logout() {
        logStep("Logout from My Account");
        click(btnLogout);
    }
}
