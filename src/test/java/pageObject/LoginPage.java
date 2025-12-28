package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	// Locator
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtUserName;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement btnLogin;
	
	// Actions
	
	public void inputUserName(String userName) throws InterruptedException {
		sendKeys(txtUserName, userName);
	}
	
	public void inputPassword(String password) throws InterruptedException {
		sendKeys(txtPassword, password);
	}
	
	public void clickLoginButton() throws InterruptedException {
		clickAndNavigate(btnLogin);
	}
	
	public void login(String userName, String password) throws InterruptedException {
		inputUserName(userName);
	    inputPassword(password);
	    clickLoginButton();
	}

}
