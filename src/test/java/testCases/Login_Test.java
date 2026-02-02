package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClass;
import config.ConfigReader;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;

/**
 * Test class for login scenarios.
 */
public class Login_Test extends BaseClass {

    /**
     * Test case for successful login.
     */
    @Test(groups = { "Sanity", "Master" })
    public void TC001_LoginSuccessfully_Test() {

        logger.info("========== START TEST CASE: TC001_LoginSuccessfully_Test ==========");

        try {
            /* ======================
             * STEP 1: OPEN LOGIN PAGE
             * ====================== */
            logger.info("STEP 1: User navigates to Login page from Home page");

            HomePage homePage = new HomePage(getDriver());
            homePage.openMyAccountMenu();
            homePage.navigateToLoginPage();

            /* ======================
             * STEP 2: LOGIN
             * ====================== */
            logger.info("STEP 2: User logs in with valid credentials");

            LoginPage loginPage = new LoginPage(getDriver());

            String email = ConfigReader.getRequired("email");
            String password = ConfigReader.getRequired("password");

            logger.debug("Login with email: {}", email);
            loginPage.login(email, password);

            /* ======================
             * STEP 3: VERIFY LOGIN SUCCESS
             * ====================== */
            logger.info("STEP 3: Verify My Account page is displayed");

            MyAccountPage myAccountPage = new MyAccountPage(getDriver());

            boolean isMyAccountDisplayed =
                    myAccountPage.isMyAccountPageDisplayed();

            Assert.assertTrue(
                    isMyAccountDisplayed,
                    "My Account Page is not displayed, login failed"
            );

            logger.info("✅ TEST PASSED: User logged in successfully");

        } catch (Exception e) {
            logger.error("❌ TEST FAILED at TC001_LoginSuccessfully_Test", e);
            Assert.fail("Test failed due to exception", e);

        } finally {
            logger.info("========== END TEST CASE: TC001_LoginSuccessfully_Test ==========");
        }
    }
}
