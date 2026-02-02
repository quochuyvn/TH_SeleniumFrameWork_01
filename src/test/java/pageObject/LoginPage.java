package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BasePage;

/**
 * Represents the Login Page of the application.
 * Provides methods to interact with the elements on the login page.
 */
public class LoginPage extends BasePage {

    /**
     * Constructor for LoginPage.
     * @param driver The WebDriver instance.
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "input-email")
    private WebElement txtUserName;

    @FindBy(id = "input-password")
    private WebElement txtPassword;

    @FindBy(xpath = "//input[@value='Login']")
    private WebElement btnLogin;

    /**
     * Enters the username in the username field.
     * @param userName The username to enter.
     */
    public void enterUserName(String userName) {
        logStep("Enter username");
        type(txtUserName, userName);
    }

    /**
     * Enters the password in the password field.
     * @param password The password to enter.
     */
    public void enterPassword(String password) {
        logStep("Enter password");
        type(txtPassword, password);
    }

    /**
     * Clicks the login button.
     */
    public void submitLogin() {
        logStep("Click Login button");
        click(btnLogin);
    }

    /**
     * Composite business action to log in with a username and password.
     * @param userName The username to enter.
     * @param password The password to enter.
     */
    public void login(String userName, String password) {
        logStep("Login with valid credentials");
        enterUserName(userName);
        enterPassword(password);
        submitLogin();
    }
}
