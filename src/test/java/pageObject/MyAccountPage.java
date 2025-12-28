package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	// Locators
	@FindBy(xpath = "//h2[normalize-space()='My Account']")
	WebElement myAccountTitle;
	
	@FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Logout']")
	WebElement btnLogout;
	
	// Action
	
	// Validate page is displayed
	public boolean checkTitleDisplayed() {
		try {
			return (isDisplayed(myAccountTitle));
		} catch (Exception e) {
			e.printStackTrace();
			return (false);
		}
	}
	
	// Log out 
	public void logout() {
		click(btnLogout);
	}

}
