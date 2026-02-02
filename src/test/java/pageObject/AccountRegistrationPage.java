package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BasePage;

/**
 * Represents the Account Registration page.
 * Contains all locators and actions related to user registration.
 */
public class AccountRegistrationPage extends BasePage {

    /**
     * Constructor to initialize the PageFactory and WebDriver.
     * @param driver The WebDriver instance from the test class.
     */
    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================

    @FindBy(xpath = "//label[normalize-space()='First Name']/following-sibling::div/input")
    private WebElement txtFirstName;

    @FindBy(xpath = "//label[normalize-space()='Last Name']/following-sibling::div/input")
    private WebElement txtLastName;

    @FindBy(xpath = "//label[normalize-space()='E-Mail']/following-sibling::div/input")
    private WebElement txtEmail;

    @FindBy(xpath = "//label[normalize-space()='Telephone']/following-sibling::div/input")
    private WebElement txtTelephone;

    @FindBy(xpath = "//label[normalize-space()='Password']/following-sibling::div/input")
    private WebElement txtPassword;

    @FindBy(xpath = "//label[normalize-space()='Password Confirm']/following-sibling::div/input")
    private WebElement txtConfirmPassword;

    @FindBy(xpath = "//label[normalize-space()='Subscribe']/following-sibling::div//label[normalize-space()='Yes']/input")
    private WebElement radioNewsletterYes;

    @FindBy(xpath = "//label[normalize-space()='Subscribe']/following-sibling::div//label[normalize-space()='No']/input")
    private WebElement radioNewsletterNo;

    @FindBy(xpath = "//*[contains(normalize-space(),'Privacy Policy')]/input[@type='checkbox']")
    private WebElement chkPrivacyPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement btnContinue;

    // ================= ACTIONS =================

    /**
     * Enters the first name into the corresponding field.
     * @param firstName The first name to be entered.
     */
    public void setFirstName(String firstName) {
        typeWithRetry(txtFirstName, firstName);
    }

    /**
     * Enters the last name into the corresponding field.
     * @param lastName The last name to be entered.
     */
    public void setLastName(String lastName) {
        typeWithRetry(txtLastName, lastName);
    }

    /**
     * Enters the email into the corresponding field.
     * @param email The email address to be entered.
     */
    public void setEmail(String email) {
        typeWithRetry(txtEmail, email);
    }

    /**
     * Enters the telephone number into the corresponding field.
     * @param telephone The telephone number to be entered.
     */
    public void setTelephone(String telephone) {
        typeWithRetry(txtTelephone, telephone);
    }

    /**
     * Enters the password into the corresponding field.
     * @param password The password to be entered.
     */
    public void setPassword(String password) {
        typeWithRetry(txtPassword, password);
    }

    /**
     * Enters the password confirmation into the corresponding field.
     * @param confirmPassword The password confirmation.
     */
    public void setConfirmPassword(String confirmPassword) {
        typeWithRetry(txtConfirmPassword, confirmPassword);
    }

    /**
     * Selects the 'Yes' option for the newsletter subscription.
     */
    public void selectSubscribeYes() {
        clickWithRetry(radioNewsletterYes);
    }

    /**
     * Selects the 'No' option for the newsletter subscription.
     */
    public void selectSubscribeNo() {
        clickWithRetry(radioNewsletterNo);
    }

    /**
     * Checks the Privacy Policy checkbox.
     */
    public void acceptPrivacyPolicy() {
        clickWithRetry(chkPrivacyPolicy);
    }

    /**
     * Clicks the 'Continue' button to submit the registration form.
     */
    public void clickContinue() {
        clickWithRetry(btnContinue);
        waitForPageLoaded();
    }

    // ================= BUSINESS FLOW =================

    /**
     * A business flow method to complete the user registration process.
     * @param firstName User's first name.
     * @param lastName User's last name.
     * @param email User's email.
     * @param telephone User's telephone number.
     * @param password User's password.
     */
    public void registerUser(
            String firstName,
            String lastName,
            String email,
            String telephone,
            String password) {

        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setTelephone(telephone);
        setPassword(password);
        setConfirmPassword(password);

        selectSubscribeYes();
        acceptPrivacyPolicy();
        clickContinue();
    }
}
