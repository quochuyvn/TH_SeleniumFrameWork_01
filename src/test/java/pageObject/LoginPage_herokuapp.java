package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage_herokuapp extends BasePage{

	public LoginPage_herokuapp(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='username']")
	WebElement txtUserName;
	
	@FindBy(xpath="//input[@id='password']")
	WebElement txtPassWord;
	
	@FindBy(xpath="//button[@type='submit']")
	WebElement btnLogin;

	public void enterUserName (String userName) {
		txtUserName.sendKeys(userName);
	}
	
	public void enterPassword(String password) {
		txtPassWord.sendKeys(password);
	}
	
	public void clickLoginButton() {
		btnLogin.click();
	}
	
	public void loginWithUserNameAndPassword(String userName, String password) {
		txtUserName.sendKeys(userName);
		txtPassWord.sendKeys(password);
		btnLogin.click();
	}
	
	
	
	
	
	
	
	
	
	
	
}
