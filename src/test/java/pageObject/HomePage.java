package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BasePage;

/**
 * Represents the Home Page of the application.
 * Contains locators and methods to interact with elements on the Home Page.
 */
public class HomePage extends BasePage {

    /**
     * Constructor to initialize the HomePage with a WebDriver instance.
     * @param driver The WebDriver instance to be used.
     */
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[normalize-space()='My Account']")
    private WebElement myAccountMenu;

    @FindBy(linkText = "Login")
    private WebElement loginLink;

    @FindBy(linkText = "Register")
    private WebElement registerLink;

    /**
     * Clicks on the 'My Account' menu dropdown.
     */
    public void openMyAccountMenu() {
        logStep("Open My Account menu");
        click(myAccountMenu);
    }

    /**
     * Clicks on the 'Login' link from the 'My Account' dropdown to navigate to the Login page.
     * @return A new instance of the LoginPage.
     */
    public LoginPage navigateToLoginPage() {
        logStep("Navigate to Login page");
        click(loginLink);
        return new LoginPage(driver);
    }

    /**
     * Clicks on the 'Register' link from the 'My Account' dropdown to navigate to the registration page.
     * @return A new instance of the AccountRegistrationPage.
     */
    public AccountRegistrationPage navigateToRegisterPage() {
        logStep("Navigate to Register page");
        click(registerLink);
        return new AccountRegistrationPage(driver);
    }
}
