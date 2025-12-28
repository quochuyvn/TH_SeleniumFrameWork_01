package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	// Locator
	
	@FindBy(xpath = "//span[normalize-space()='My Account']")
	WebElement menuMyAccount;
	
	@FindBy(xpath = "//a[normalize-space()='Register']")
	WebElement lnkRegister;
	
	@FindBy(xpath = "//a[normalize-space()='Login']")
	WebElement lnkLogin;
	
	
	// Action
	
	public void clickOnMyAccountMenu() throws InterruptedException {
		click(menuMyAccount);
	}
	
	public void clickOnRegister() throws InterruptedException {
		clickAndNavigate(lnkRegister);
	}
	
	public void clickOnLogin() throws InterruptedException {
		clickAndNavigate(lnkLogin);
	}
		
}
