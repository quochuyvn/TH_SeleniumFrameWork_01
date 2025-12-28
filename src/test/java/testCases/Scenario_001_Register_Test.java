package testCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.AccountRegistrationPage;
import pageObject.HomePage;
import testBase.BaseClass;
import utilities.RandomDataUtil;

public class Scenario_001_Register_Test extends BaseClass{
	
	@Test(groups= {"Regression","Master"})
	public void verify_Register_successfully() throws InterruptedException {
		
		logger.info("****** TC_001_Register_Test ***** ");
		
		String firstName = RandomDataUtil.randomFirstName();
		String lastName = RandomDataUtil.randomLastName();
		String email = RandomDataUtil.randomEmail();
		String telephone = RandomDataUtil.randomVietnamPhone();
		String password = RandomDataUtil.randomPassword(8);
		
		try {
			
			
			HomePage homePage = new HomePage(driver);
			logger.info("Openned Home Page.");
			
			homePage.clickOnMyAccountMenu();
			logger.info("Clicked on My Account Menu.");
			Thread.sleep(1000);
			
			homePage.clickOnRegister();
			logger.info("Clicked on Register link.");
			Thread.sleep(1000);
			
			AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
			accountRegistrationPage.registerUser(firstName, lastName, email, telephone, password, password);
			logger.info("Input information and submit");
			
			logger.info("Validating expected URL");
			//h1[normalize-space()='Your Account Has Been Created!']
			String text = driver.findElement(By.xpath("//h1[normalize-space()='Your Account Has Been Created!']")).getText();
			if (text.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
				logger.info("Test Passed");
			}
			else
			{
				logger.info("Test failed. Expected url is: Your Account Has Been Created! " +text);
				Assert.fail();
			}
			//Assert.assertEquals("https://tutorialsninja.com/demo/index.php?route=account/register", driver.getCurrentUrl());
			//logger.info("Test Passed");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			logger.error("Test failed: " + e.getMessage());
			Assert.fail("Test failed: " + e.getMessage());
		}
		finally {
			logger.info("***** Finished TC_001_Register_Test *****");
		}

	}
	
	
}
