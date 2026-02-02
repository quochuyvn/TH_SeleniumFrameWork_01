package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClass;
import config.ConfigReader;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.AccountPage;
import pageObject.EditAccountPage;
import utils.RandomDataUtil;


/**
 * This class contains test cases for the "Edit Account" functionality.
 * It verifies that a logged-in user can successfully update their account details.
 */
public class EditAccount_Test extends BaseClass {

    /**
     * Test case to verify the process of editing user account information.
     * <p>
     * Test Steps:
     * <ol>
     * <li>Login with valid credentials.</li>
     * <li>Navigate to the 'Edit Account' page.</li>
     * <li>Update First Name, Last Name, and Telephone with new random data.</li>
     * <li>Save the changes.</li>
     * <li>Verify that the success message "Success: Your account has been successfully updated." is displayed.</li>
     * </ol>
     */
    @Test(groups = { "Regression", "Master" })
    public void TC001_EditAccount_Test() {

        logger.info("========== START TEST CASE: TC001_EditAccount_Test ==========");

        try {
            // STEP 1: LOGIN
            logger.info("STEP 1: User logs in with valid credentials");
            HomePage homePage = new HomePage(getDriver());
            homePage.openMyAccountMenu();
            homePage.navigateToLoginPage();

            LoginPage loginPage = new LoginPage(getDriver());
            String email = ConfigReader.getRequired("email");
            String password = ConfigReader.getRequired("password");
            loginPage.login(email, password);

            // STEP 2: NAVIGATE TO EDIT ACCOUNT PAGE
            logger.info("STEP 2: User navigates to Edit Account page");
            AccountPage accountPage = new AccountPage(getDriver());
            accountPage.clickEditAccount();

            // STEP 3: EDIT ACCOUNT DETAILS
            logger.info("STEP 3: User edits account details");
            EditAccountPage editAccountPage = new EditAccountPage(getDriver());
            
            String newFirstName = "John-" + RandomDataUtil.randomAlphabetic(4);
            String newLastName = "Doe-" + RandomDataUtil.randomAlphabetic(4);
            String newTelephone = RandomDataUtil.randomNumber(10);
            
            logger.debug("Updating First Name to: {}", newFirstName);
            logger.debug("Updating Last Name to: {}", newLastName);
            logger.debug("Updating Telephone to: {}", newTelephone);

            editAccountPage.setFirstName(newFirstName);
            editAccountPage.setLastName(newLastName);
            editAccountPage.setTelephone(newTelephone);
            
            // STEP 4: SAVE CHANGES
            logger.info("STEP 4: User saves the changes");
            editAccountPage.clickContinue();

            // STEP 5: VERIFY SUCCESS MESSAGE
            logger.info("STEP 5: Verify success message is displayed");
            AccountPage afterEditAccountPage = new AccountPage(getDriver());
            String successMessage = afterEditAccountPage.getSuccessMessageText();
            
            logger.debug("Actual success message: {}", successMessage);
            String expectedMessage = "Success: Your account has been successfully updated.";
            
            Assert.assertNotNull(successMessage, "Success message is not displayed.");
            Assert.assertTrue(
                successMessage.contains(expectedMessage),
                "Success message does not match. Expected: '" + expectedMessage + "', Actual: '" + successMessage + "'"
            );

            logger.info("✅ TEST PASSED: Account edited successfully");

        } catch (Exception e) {
            logger.error("❌ TEST FAILED at TC001_EditAccount_Test", e);
            Assert.fail("Test failed due to exception", e);

        } finally {
            logger.info("========== END TEST CASE: TC001_EditAccount_Test ==========");
        }
    }
}
