package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;

/**
 * Represents the Edit Account page of the application.
 * This class contains all the WebElements and methods to interact with the Edit Account page.
 */
public class EditAccountPage extends BasePage {

    /**
     * Constructor for EditAccountPage.
     * It initializes the WebDriver and PageFactory elements.
     * @param driver The WebDriver instance to be used.
     */
    public EditAccountPage(WebDriver driver) {
        super(driver);
    }

    // WebElements on the Edit Account page
    @FindBy(id = "input-firstname")
    private WebElement txtFirstName;

    @FindBy(id = "input-lastname")
    private WebElement txtLastName;

    @FindBy(id = "input-email")
    private WebElement txtEmail;

    @FindBy(id = "input-telephone")
    private WebElement txtTelephone;

    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement btnContinue;

    @FindBy(xpath = "//a[normalize-space()='Back']")
    private WebElement lnkBack;

    /**
     * Enters the given first name into the 'First Name' text field.
     * It first clears the field before entering the new value.
     * @param fname The first name to be entered.
     */
    public void setFirstName(String fname) {
        txtFirstName.clear();
        txtFirstName.sendKeys(fname);
    }

    /**
     * Enters the given last name into the 'Last Name' text field.
     * It first clears the field before entering the new value.
     * @param lname The last name to be entered.
     */
    public void setLastName(String lname) {
        txtLastName.clear();
        txtLastName.sendKeys(lname);
    }

    /**
     * Enters the given email into the 'E-Mail' text field.
     * It first clears the field before entering the new value.
     * @param email The email to be entered.
     */
    public void setEmail(String email) {
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }

    /**
     * Enters the given telephone number into the 'Telephone' text field.
     * It first clears the field before entering the new value.
     * @param tel The telephone number to be entered.
     */
    public void setTelephone(String tel) {
        txtTelephone.clear();
        txtTelephone.sendKeys(tel);
    }

    /**
     * Clicks the 'Continue' button to save the account details.
     */
    public void clickContinue() {
        btnContinue.click();
    }

    /**
     * Clicks the 'Back' link to return to the previous page.
     */
    public void clickBack() {
        lnkBack.click();
    }
}

