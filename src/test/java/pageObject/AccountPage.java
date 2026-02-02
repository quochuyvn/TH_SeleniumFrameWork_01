package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;

/**
 * Represents the user's account page after a successful login.
 * This page provides access to various account management features.
 */
public class AccountPage extends BasePage {

    /**
     * Initializes a new instance of the AccountPage class.
     * @param driver The WebDriver instance to use for interacting with the page.
     */
    public AccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    private WebElement msgHeading;

    @FindBy(xpath = "//div[contains(@class, 'alert-success')]")
    private WebElement alertSuccess;

    // My Account links
    @FindBy(linkText = "Edit your account information")
    private WebElement lnkEditAccount;

    @FindBy(linkText = "Change your password")
    private WebElement lnkChangePassword;

    @FindBy(linkText = "Modify your address book entries")
    private WebElement lnkModifyAddressBook;

    @FindBy(linkText = "Modify your wish list")
    private WebElement lnkWishList;

    // My Orders links
    @FindBy(linkText = "View your order history")
    private WebElement lnkOrderHistory;

    @FindBy(linkText = "Downloads")
    private WebElement lnkDownloads;

    @FindBy(linkText = "Your Reward Points")
    private WebElement lnkRewardPoints;
    
    @FindBy(linkText = "View your return requests")
    private WebElement lnkReturnRequests;

    @FindBy(linkText = "Your Transactions")
    private WebElement lnkTransactions;

    @FindBy(linkText = "Recurring payments")
    private WebElement lnkRecurringPayments;
    
    // My Affiliate Account links
    @FindBy(linkText = "Register for an affiliate account")
    private WebElement lnkRegisterAffiliate;

    // Newsletter links
    @FindBy(linkText = "Subscribe / unsubscribe to newsletter")
    private WebElement lnkNewsletter;

    // Right side menu
    @FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Logout']")
    private WebElement lnkLogout;
    
    /**
     * Checks if the "My Account" heading is displayed on the page.
     * @return true if the heading is visible, false otherwise.
     */
    public boolean isMyAccountPageExists() {
        try {
            return (msgHeading.isDisplayed());
        } catch (Exception e) {
            return (false);
        }
    }

    /**
     * Retrieves the text from the success alert message.
     * @return The text of the success message, or null if not found.
     */
    public String getSuccessMessageText() {
        try {
            return (alertSuccess.getText());
        } catch (Exception e) {
            return (null);
        }
    }

    /**
     * Clicks the "Edit your account information" link.
     */
    public void clickEditAccount() {
        lnkEditAccount.click();
    }

    /**
     * Clicks the "Change your password" link.
     */
    public void clickChangePassword() {
        lnkChangePassword.click();
    }
    
    /**
     * Clicks the "Modify your address book entries" link.
     */
    public void clickModifyAddressBook() {
        lnkModifyAddressBook.click();
    }
    
    /**
     * Clicks the "Modify your wish list" link.
     */
    public void clickWishList() {
        lnkWishList.click();
    }
    
    /**
     * Clicks the "View your order history" link.
     */
    public void clickOrderHistory() {
        lnkOrderHistory.click();
    }
    
    /**
     * Clicks the "Downloads" link.
     */
    public void clickDownloads() {
        lnkDownloads.click();
    }
    
    /**
     * Clicks the "Your Reward Points" link.
     */
    public void clickRewardPoints() {
        lnkRewardPoints.click();
    }
    
    /**
     * Clicks the "View your return requests" link.
     */
    public void clickReturnRequests() {
        lnkReturnRequests.click();
    }
    
    /**
     * Clicks the "Your Transactions" link.
     */
    public void clickTransactions() {
        lnkTransactions.click();
    }

    /**
     * Clicks the "Recurring payments" link.
     */
    public void clickRecurringPayments() {
        lnkRecurringPayments.click();
    }
    
    /**
     * Clicks the "Register for an affiliate account" link.
     */
    public void clickRegisterAffiliate() {
        lnkRegisterAffiliate.click();
    }

    /**
     * Clicks the "Subscribe / unsubscribe to newsletter" link.
     */
    public void clickNewsletter() {
        lnkNewsletter.click();
    }

    /**
     * Clicks the "Logout" link from the right side menu.
     */
    public void clickLogout() {
        lnkLogout.click();
    }
}
