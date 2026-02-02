package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClass;
import config.ConfigReader;
import pageObject.AccountPage;
import pageObject.HomePage;
import pageObject.LoginPage;

/**
 * Test class for logout scenarios.
 */
public class Logout_Test extends BaseClass {

    /**
     * Test case for successful logout.
     */
    @Test(groups = { "Sanity", "Master" })
    public void TC001_LogoutSuccessfully_Test() {

        logger.info("========== START TEST CASE: TC001_LogoutSuccessfully_Test ==========");

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
            logger.info("STEP 3: Verify Account page is displayed after login");

            AccountPage accountPage = new AccountPage(getDriver());

            boolean isAccountPageDisplayed = accountPage.isMyAccountPageExists();

            Assert.assertTrue(
                    isAccountPageDisplayed,
                    "Account Page is not displayed after login"
            );

            logger.info("✅ Login successful, Account page is displayed");

            /* ======================
             * STEP 4: LOGOUT
             * ====================== */
            logger.info("STEP 4: User logs out from Account page");

            accountPage.clickLogout();

            /* ======================
             * STEP 5: VERIFY LOGOUT SUCCESS
             * ====================== */
            logger.info("STEP 5: Verify Home page is displayed after logout");

            // Wait for redirect to home page and verify we're back to home
            homePage = new HomePage(getDriver());
            
            // After logout, user should be redirected to home page
            // Verify by checking the page URL or checking if My Account menu is available
            String currentUrl = getDriver().getCurrentUrl();
            String baseUrl = ConfigReader.getRequired("base.url");
            
            logger.debug("Current URL after logout: {}", currentUrl);
            logger.debug("Base URL: {}", baseUrl);

            boolean isLogoutSuccessful = currentUrl.contains(baseUrl) && 
                                        !currentUrl.contains("account/account");

            Assert.assertTrue(
                    isLogoutSuccessful,
                    "User was not redirected to home page after logout"
            );

            logger.info("✅ TEST PASSED: User logged out successfully and redirected to home page");

        } catch (Exception e) {
            logger.error("❌ TEST FAILED at TC001_LogoutSuccessfully_Test", e);
            Assert.fail("Test failed due to exception", e);

        } finally {
            logger.info("========== END TEST CASE: TC001_LogoutSuccessfully_Test ==========");
        }
    }
}
