package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	// Locators
	
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

	
	public void setFirstName(String firtName) throws InterruptedException {
		sendKeys(txtFirstName, firtName);
	}
	
	public void setLastName(String lastName) throws InterruptedException {
		sendKeys(txtLastName, lastName);
	}
	
	public void setEmail(String email) throws InterruptedException {
		sendKeys(txtEmail, email);
	}
	
	public void setTelephone(String tel) throws InterruptedException {
		sendKeys(txtTelephone, tel);
	}
	
	public void setPassword(String password) throws InterruptedException {
		sendKeys(txtPassword, password);
	}
	
	public void setConfirmPassword(String confirmPassword) throws InterruptedException {
		sendKeys(txtConfirmPassword, confirmPassword);
	}

	public void checkSubscribeYes() throws InterruptedException {
		radioNewsletterYes.click();
	}
	
	public void checkSubscribeNo() throws InterruptedException {
		click(radioNewsletterNo);
	}
	
	public void checkPrivacyPolicy() throws InterruptedException {
		click(chkPrivacyPolicy);
	}
	
	
	public void clickContinueButton() throws InterruptedException {
		clickAndNavigate(btnContinue);
	}
	
	public void registerUser(String firstName, String lastName, String email, String telephone, String password, String confirmPassword) throws InterruptedException {
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setTelephone(telephone);
		setPassword(confirmPassword);
		setConfirmPassword(confirmPassword);
		
		
		checkSubscribeYes();
		checkPrivacyPolicy();
		clickContinueButton();	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
