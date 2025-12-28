package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;

public class Scenario_002_Login_Test extends BaseClass{
	
	@Test(groups= {"Sanity","Master"})
	public void TC001_LoginSuccessfully_Test() {
		logger.info("***** Start Scenario_002_Login_Test - Test TC001_LoginSuccessfully_Test *****");
		
		try {
			// Home Page
			logger.info("Open Home Page.");
			HomePage homepage = new HomePage(driver);
			homepage.clickOnMyAccountMenu();
			homepage.clickOnLogin();
			logger.info("Clicked on Login link.");
			
			// Login Page
			LoginPage loginpage = new LoginPage(driver);
			loginpage.inputUserName(properties.getProperty("email"));
			logger.info("Inputed User Name.");
			loginpage.inputPassword(properties.getProperty("password"));
			logger.info("Inputed Password.");
			loginpage.clickLoginButton();
			logger.info("Clicked on Login button.");
			
			// Myaccount page
			logger.info("Validating at My Account Page.");
			MyAccountPage myAccountPage = new MyAccountPage(driver);
			boolean myAccoungPageIsDisplayed = myAccountPage.checkTitleDisplayed();			
			Assert.assertTrue(myAccoungPageIsDisplayed, "My Account Page is not displayed, login failed");
			logger.info("Test Pass.");
			
		} catch (Exception e) {
			Assert.fail("Error happened. Please check again.");
			logger.error("Error happened. Please check again.");
			e.printStackTrace();
		}
		finally {
			logger.info("***** Finished Scenario_002_Login_Test - Test TC001_LoginSuccessfully_Test *****");
		}
		
	}

}
