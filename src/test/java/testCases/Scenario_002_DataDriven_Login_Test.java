package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class Scenario_002_DataDriven_Login_Test extends BaseClass{
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class)
	public void TC001_LoginSuccessfully_Test(String email, String password, String exp) {
		logger.info("***** Start Scenario_002_DataDriven_Login_Test - Test TC001_LoginSuccessfully_Test *****");
		
		try {
			// Home Page
			logger.info("Open Home Page.");
			HomePage homepage = new HomePage(driver);
			homepage.clickOnMyAccountMenu();
			homepage.clickOnLogin();
			logger.info("Clicked on Login link.");
			
			// Login Page
			LoginPage loginpage = new LoginPage(driver);
			loginpage.inputUserName(email);
			logger.info("Inputed User Name.");
			loginpage.inputPassword(password);
			logger.info("Inputed Password.");
			loginpage.clickLoginButton();
			logger.info("Clicked on Login button.");
			
			// Myaccount page
			logger.info("Validating");
			MyAccountPage myAccountPage = new MyAccountPage(driver);
			boolean myAccountPageIsDisplayed = myAccountPage.checkTitleDisplayed();
			
			if(exp.equalsIgnoreCase("Valid")) {
				if(myAccountPageIsDisplayed==true) {
					myAccountPage.logout();
					logger.info("Test Pass.");
					Assert.assertTrue(true);
				}
				else {
					Assert.assertTrue(false);
					logger.info("Test Failed.");
				}
			}
			else if (exp.equalsIgnoreCase("Invalid")) {
				if(myAccountPageIsDisplayed==true) {
					myAccountPage.logout();
					logger.info("Test Failed.");
					Assert.assertTrue(false);
				}
				else {
					Assert.assertTrue(true);
					logger.info("Test Pass.");
				}
			}
			
		} catch (Exception e) {
			Assert.fail("Error happened. Please check again.");
			logger.error("Error happened. Please check again.");
			e.printStackTrace();
		}
		finally {
			logger.info("***** Finished Scenario_002_DataDriven_Login_Test - Test TC001_LoginSuccessfully_Test *****");
		}
		
	}

}
