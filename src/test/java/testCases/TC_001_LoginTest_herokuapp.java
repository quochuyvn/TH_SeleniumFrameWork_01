package testCases;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pageObject.HomePage_Herokuapp;
import pageObject.LoginPage_herokuapp;
import testBase.BaseClass_Herokuapp;

public class TC_001_LoginTest_herokuapp extends BaseClass_Herokuapp{
	
	@Test
	public void verify_login_successfully() throws InterruptedException {
		
		logger.info("****** TC_001_LoginTest ***** ");
		
		try {
			HomePage_Herokuapp homePage = new HomePage_Herokuapp(driver);
					
			homePage.clickFormAuthentication();
			logger.info("Clicked on Form Authentication.");
			Thread.sleep(2000);
			
			LoginPage_herokuapp loginPage = new LoginPage_herokuapp(driver);
			loginPage.loginWithUserNameAndPassword("tomsmith", "SuperSecretPassword!");
			logger.info("Logged in.");
			Thread.sleep(2000);
			
			logger.info("Validating expected URL");
			String url = driver.getCurrentUrl();
			if (url.equals("https://the-internet.herokuapp.com/secure")) {
				AssertJUnit.assertTrue(true);
				logger.info("Test Passed");
			}
			else
			{
				logger.info("Test failed. Expected url is: https://the-internet.herokuapp.com/secure... but actual url is: " +url);
				AssertJUnit.fail();
			}
			//Assert.assertEquals("https://the-internet.herokuapp.com/secure...", driver.getCurrentUrl());
			//logger.info("Test Passed");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			logger.error("Test failed: " + e.getMessage());
			AssertJUnit.fail("Test failed: " + e.getMessage());
		}
		finally {
			logger.info("***** Finished TC_001_LoginTest *****");
		}

	}
	
	
}
