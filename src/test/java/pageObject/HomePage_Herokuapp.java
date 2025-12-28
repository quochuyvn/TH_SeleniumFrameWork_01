package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage_Herokuapp extends BasePage {

	public HomePage_Herokuapp(WebDriver driver) {
		super(driver);
	}
	
	
	
	
	
	
	
	/*
	 * ********** This is for herokuapp **********
	 * https://the-internet.herokuapp.com/
	 */
	
	@FindBy(xpath="//a[normalize-space()='Form Authentication']")
	WebElement lnkFormAuthentication;
	
	public void clickFormAuthentication() {
		lnkFormAuthentication.click();
	}
	

}
