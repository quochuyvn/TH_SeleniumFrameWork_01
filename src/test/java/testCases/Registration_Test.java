package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObject.AccountRegistrationPage;
import pageObject.HomePage;
import utils.RandomDataUtil;

/**
 * Test class for user registration scenarios.
 */
public class Registration_Test extends BaseClass {

    /**
     * Test case for successful new user registration.
     */
    @Test(groups = { "Sanity", "Master" })
    public void TC001_RegisterNewUser_Test() {

        logger.info("========== START TEST CASE: TC001_RegisterNewUser_Test ==========");

        try {
            /* ======================
             * STEP 1: OPEN REGISTRATION PAGE
             * ====================== */
            logger.info("STEP 1: User navigates to Registration page from Home page");

            HomePage homePage = new HomePage(getDriver());
            homePage.openMyAccountMenu();
            homePage.navigateToRegisterPage();

            /* ======================
             * STEP 2: FILL REGISTRATION FORM
             * ====================== */
            logger.info("STEP 2: User fills in the registration form with valid data");

            AccountRegistrationPage regPage = new AccountRegistrationPage(getDriver());

            // Generate random data for registration
            String firstName = "John-" + RandomDataUtil.randomAlphabetic(4);
            String lastName = "Doe-" + RandomDataUtil.randomAlphabetic(4);
            String email = "johndoe_" + RandomDataUtil.randomAlphaNumeric(5) + "@example.com";
            String telephone = RandomDataUtil.randomNumber(10);
            String password = "password123";

            logger.debug("Registering with First Name: {}", firstName);
            logger.debug("Registering with Last Name: {}", lastName);
            logger.debug("Registering with Email: {}", email);
            logger.debug("Registering with Telephone: {}", telephone);

            regPage.registerUser(firstName, lastName, email, telephone, password);

            /* ======================
             * STEP 3: VERIFY REGISTRATION SUCCESS
             * ====================== */
            logger.info("STEP 3: Verify account creation success message is displayed");

            String expectedTitle = "Your Account Has Been Created!";
            String actualTitle = getDriver().getTitle();

            Assert.assertEquals(
                    actualTitle,
                    expectedTitle,
                    "Registration failed or success page title is incorrect."
            );

            logger.info("✅ TEST PASSED: User registered successfully");

        } catch (Exception e) {
            logger.error("❌ TEST FAILED at TC001_RegisterNewUser_Test", e);
            Assert.fail("Test failed due to exception", e);

        } finally {
            logger.info("========== END TEST CASE: TC001_RegisterNewUser_Test ==========");
        }
    }
}
