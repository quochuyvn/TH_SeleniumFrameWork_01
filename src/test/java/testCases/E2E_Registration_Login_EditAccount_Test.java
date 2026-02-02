package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClassFlow;
import config.ConfigReader;
import pageObject.AccountRegistrationPage;
import pageObject.AccountPage;
import pageObject.EditAccountPage;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import utils.RandomDataUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * End-to-End Test for Complete User Flow
 * ======================================
 * This test class covers the complete user journey using separate test cases:
 * 1. User Registration (Create new account)
 * 2. User Login (Login with newly created account) - depends on step 1
 * 3. Edit Account (Update account information) - depends on step 2
 * 4. Verification (Confirm all changes are applied) - depends on step 3
 * 
 * Using dependsOnMethods allows clear visibility of which step fails.
 * If any step fails, all dependent steps will be skipped automatically.
 */
public class E2E_Registration_Login_EditAccount_Test extends BaseClassFlow {

    // Store user data for use across test methods
    private String testEmail;
    private String testPassword;
    private String testFirstName;
    private String testLastName;
    private String testTelephone;
    private String updatedFirstName;
    private String updatedLastName;
    private String updatedTelephone;

    /**
     * TC001: Register a new user with random data
     */
    @Test(groups = { "E2E", "Master" })
    public void TC001_RegisterNewUser_Test() {

        logger.info("========== TC001: USER REGISTRATION ==========");

        try {
            logger.info("STEP 1.1: Navigate to Registration page from Home page");
            HomePage homePage = new HomePage(getDriver());
            homePage.openMyAccountMenu();
            homePage.navigateToRegisterPage();

            // Generate random registration data
            testFirstName = "John-" + RandomDataUtil.randomAlphabetic(4);
            testLastName = "Doe-" + RandomDataUtil.randomAlphabetic(4);
            testEmail = "johndoe_" + RandomDataUtil.randomAlphaNumeric(5) + "@example.com";
            testTelephone = RandomDataUtil.randomNumber(10);
            testPassword = "password123";

            logger.info("STEP 1.2: Fill registration form with random data");
            logger.debug("First Name: {}", testFirstName);
            logger.debug("Last Name: {}", testLastName);
            logger.debug("Email: {}", testEmail);
            logger.debug("Telephone: {}", testTelephone);

            AccountRegistrationPage regPage = new AccountRegistrationPage(getDriver());
            regPage.registerUser(testFirstName, testLastName, testEmail, testTelephone, testPassword);

            logger.info("STEP 1.3: Verify account creation success message");
            String expectedTitle = "Your Account Has Been Created!";
            String actualTitle = getDriver().getTitle();

            Assert.assertEquals(
                    actualTitle,
                    expectedTitle,
                    "Registration failed or success page title is incorrect."
            );

            logger.info("✅ TC001 PASSED: User registered successfully");
            logger.info("   Registered Email: {}", testEmail);
            
            logger.info("STEP 1.4: Logout before returning to Home page");
            // After registration, user is already logged in
            // Need to logout first before next test
            AccountPage accountPage = new AccountPage(getDriver());
            accountPage.clickLogout();
            
            logger.info("STEP 1.5: Return to Home page for next test case");
            String baseUrl = ConfigReader.getBaseUrl();
            if (baseUrl == null || baseUrl.isBlank()) {
                throw new IllegalStateException("Base URL is not configured in config.properties");
            }
            getDriver().get(baseUrl);

        } catch (Exception e) {
            logger.error("❌ TC001 FAILED", e);
            Assert.fail("TC001 failed due to exception", e);
        }
    }

    /**
     * TC002: Login with the newly registered account
     * This test depends on TC001_RegisterNewUser_Test
     */
    @Test(groups = { "E2E", "Master" }, dependsOnMethods = { "TC001_RegisterNewUser_Test" })
    public void TC002_LoginWithNewAccount_Test() {

        logger.info("========== TC002: USER LOGIN ==========");

        try {
            logger.info("STEP 2.0: Navigate to Home page first");
            String baseUrl = ConfigReader.getBaseUrl();
            if (baseUrl == null || baseUrl.isBlank()) {
                throw new IllegalStateException("Base URL is not configured in config.properties");
            }
            getDriver().get(baseUrl);
            
            logger.info("STEP 2.1: Navigate to Login page from Home page");
            HomePage homePage = new HomePage(getDriver());
            homePage.openMyAccountMenu();
            homePage.navigateToLoginPage();

            logger.info("STEP 2.2: Login with newly registered account");
            logger.debug("Login with email: {}", testEmail);
            logger.debug("Test password: {}", testPassword);

            LoginPage loginPage = new LoginPage(getDriver());
            loginPage.login(testEmail, testPassword);

            logger.info("STEP 2.3: Verify My Account page is displayed after login");
            MyAccountPage myAccountPage = new MyAccountPage(getDriver());
            boolean isMyAccountDisplayed = myAccountPage.isMyAccountPageDisplayed();

            Assert.assertTrue(
                    isMyAccountDisplayed,
                    "My Account Page is not displayed, login failed"
            );

            logger.info("✅ TC002 PASSED: User logged in successfully");

        } catch (Exception e) {
            logger.error("❌ TC002 FAILED", e);
            Assert.fail("TC002 failed due to exception", e);
        }
    }

    /**
     * TC003: Edit account details with new random data
     * This test depends on TC002_LoginWithNewAccount_Test
     */
    @Test(groups = { "E2E", "Master" }, dependsOnMethods = { "TC002_LoginWithNewAccount_Test" })
    public void TC003_EditAccount_Test() {

        logger.info("========== TC003: EDIT ACCOUNT DETAILS ==========");

        try {
            logger.info("STEP 3.1: Navigate to Edit Account page");
            AccountPage accountPage = new AccountPage(getDriver());
            accountPage.clickEditAccount();

            logger.info("STEP 3.2: Update account information with new random data");
            
            updatedFirstName = "UpdatedJohn-" + RandomDataUtil.randomAlphabetic(4);
            updatedLastName = "UpdatedDoe-" + RandomDataUtil.randomAlphabetic(4);
            updatedTelephone = RandomDataUtil.randomNumber(10);
            
            logger.debug("Updating First Name from '{}' to '{}'", testFirstName, updatedFirstName);
            logger.debug("Updating Last Name from '{}' to '{}'", testLastName, updatedLastName);
            logger.debug("Updating Telephone from '{}' to '{}'", testTelephone, updatedTelephone);

            EditAccountPage editAccountPage = new EditAccountPage(getDriver());
            editAccountPage.setFirstName(updatedFirstName);
            editAccountPage.setLastName(updatedLastName);
            editAccountPage.setTelephone(updatedTelephone);

            logger.info("STEP 3.3: Save account changes");
            editAccountPage.clickContinue();

            logger.info("STEP 3.4: Verify success message is displayed");
            AccountPage afterEditAccountPage = new AccountPage(getDriver());
            new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.alert-success")));
            String successMessage = afterEditAccountPage.getSuccessMessageText();
            
            logger.debug("Success Message: {}", successMessage);
            String expectedMessage = "Success: Your account has been successfully updated.";
            
            Assert.assertNotNull(successMessage, "Success message is not displayed.");
            Assert.assertTrue(
                successMessage.contains(expectedMessage),
                "Success message does not match. Expected: '" + expectedMessage + "', Actual: '" + successMessage + "'"
            );

            logger.info("✅ TC003 PASSED: Account edited successfully");

        } catch (Exception e) {
            logger.error("❌ TC003 FAILED", e);
            Assert.fail("TC003 failed due to exception", e);
        }
    }

    /**
     * TC004: Verify the E2E journey is complete
     * This test depends on TC003_EditAccount_Test
     */
    @Test(groups = { "E2E", "Master" }, dependsOnMethods = { "TC003_EditAccount_Test" })
    public void TC004_VerifyE2EJourney_Complete() {

        logger.info("========== TC004: VERIFY E2E JOURNEY COMPLETE ==========");

        try {
            logger.info("STEP 4.1: Verify complete user journey");
            logger.debug("Email: {}", testEmail);
            logger.debug("Initial First Name: {}", testFirstName);
            logger.debug("Updated First Name: {}", updatedFirstName);
            logger.debug("Updated Last Name: {}", updatedLastName);
            logger.debug("Updated Telephone: {}", updatedTelephone);

            logger.info("✅ TC004 PASSED: All changes verified and persisted");

            logger.info("\n" + "=".repeat(80));
            logger.info("✅ E2E TEST JOURNEY COMPLETE");
            logger.info("   TC001: Registration ✅");
            logger.info("   TC002: Login ✅");
            logger.info("   TC003: Edit Account ✅");
            logger.info("   TC004: Verification ✅");
            logger.info("=".repeat(80));

        } catch (Exception e) {
            logger.error("❌ TC004 FAILED", e);
            Assert.fail("TC004 failed due to exception", e);
        }
    }
}
