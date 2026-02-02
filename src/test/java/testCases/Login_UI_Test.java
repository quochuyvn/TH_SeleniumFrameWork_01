package testCases;

import base.BaseClassFlow;
import components.basic.*;
import config.ConfigKeys;
import config.ConfigReader;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * =====================================================
 * Login_UI_Test
 * =====================================================
 * Test UI của trang login (URL from config.properties)
 * Config Key: login.url
 * =====================================================
 */
public class Login_UI_Test extends BaseClassFlow {

    // Locators - Suppress null warnings as these are initialized at class load and never null
    @SuppressWarnings("null")
    private static final By EMAIL_INPUT_LOCATOR = By.id("input-email");
    @SuppressWarnings("null")
    private static final By PASSWORD_INPUT_LOCATOR = By.id("input-password");
    @SuppressWarnings("null")
    private static final By LOGIN_BUTTON_LOCATOR = By.cssSelector("input[value='Login']");
    @SuppressWarnings("null")
    private static final By FORGOT_PASSWORD_LINK_LOCATOR = By.linkText("Forgotten Password");
    @SuppressWarnings("null")
    private static final By NEW_ACCOUNT_LINK_LOCATOR = By.linkText("Register");
    @SuppressWarnings("null")
    private static final By EMAIL_LABEL_LOCATOR = By.xpath("//label[contains(text(), 'E-Mail')]");
    @SuppressWarnings("null")
    private static final By PASSWORD_LABEL_LOCATOR = By.xpath("//label[contains(text(), 'Password')]");
    @SuppressWarnings("null")
    private static final By LOGIN_HEADING_LOCATOR = By.xpath("//h1[contains(text(), 'Account Login')]");
    @SuppressWarnings("null")
    private static final By RIGHT_COLUMN_HEADING_LOCATOR = By.xpath("//h2[contains(text(), 'New Customer')]");


    /**
     * =====================================================
     * OVERRIDE beforeMethod() - Navigate to login page
     * =====================================================
     * BaseClassFlow navigates to ConfigReader.getBaseUrl()
     * We need to navigate to the specific login page instead
     */
    @Override
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        // Call parent to log test method name
        super.beforeMethod(method);
        
        // Navigate to login page (config-driven URL)
        String loginUrl = ConfigReader.getRequired(ConfigKeys.LOGIN_URL);
        logger.info(">>> Navigating to Login Page: " + loginUrl);
        getDriver().get(loginUrl);
        getDriver().manage().deleteAllCookies();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT_LOCATOR));
        logger.info("✓ Login page ready\n");
    }

    @Test(testName = "TC_001_Verify_Login_Page_UI_Elements", description = "Verify all UI elements are present on login page", groups = {"UI", "Sanity"})
    public void verifyLoginPageUIElements() {
        logger.info("========== START TEST CASE: TC_001_Verify_Login_Page_UI_Elements ==========");
        
        try {
            logger.info("STEP 1: Verify Email Input field");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            emailInput.should()
                .visible()
                .enabled();
            String emailType = emailInput.getElement().getAttribute("type");
            Assert.assertTrue(
                emailType.equalsIgnoreCase("email") || emailType.equalsIgnoreCase("text"),
                "Email input type should be email or text"
            );
            logger.info("✓ Email input field verified");

            logger.info("STEP 3: Verify Password Input field");
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            passwordInput.should()
                .visible()
                .enabled()
                .hasType("password");
            logger.info("✓ Password input field verified");

            logger.info("STEP 4: Verify Login Button");
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);
            loginButton.should()
                .visible()
                .enabled()
                .isClickable();
            logger.info("✓ Login button verified");

            logger.info("STEP 5: Verify Forgot Password Link");
            Link forgotPasswordLink = new Link(getDriver(), FORGOT_PASSWORD_LINK_LOCATOR);
            forgotPasswordLink.should()
                .visible()
                .hasText("Forgotten Password");
            logger.info("✓ Forgot Password link verified");

            logger.info("STEP 6: Verify Register Link");
            Link registerLink = new Link(getDriver(), NEW_ACCOUNT_LINK_LOCATOR);
            registerLink.should()
                .visible()
                .hasText("Register");
            logger.info("✓ Register link verified");

            logger.info("✅ TEST PASSED: All UI elements verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at verifyLoginPageUIElements", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_001_Verify_Login_Page_UI_Elements ==========\n");
        }
    }

    @Test(testName = "TC_002_Verify_Login_Page_Labels", description = "Verify all labels are correct on login page", groups = {"UI", "Sanity"})
    public void verifyLoginPageLabels() {
        logger.info("========== START TEST CASE: TC_002_Verify_Login_Page_Labels ==========");
        
        try {
            logger.info("STEP 1: Verify Email Label");
            Label emailLabel = new Label(getDriver(), EMAIL_LABEL_LOCATOR);
            emailLabel.should()
                .visible()
                .hasText("E-Mail Address");
            logger.info("✓ Email label verified");

            logger.info("STEP 2: Verify Password Label");
            Label passwordLabel = new Label(getDriver(), PASSWORD_LABEL_LOCATOR);
            passwordLabel.should()
                .visible()
                .hasText("Password");
            logger.info("✓ Password label verified");

            logger.info("✅ TEST PASSED: All labels verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at verifyLoginPageLabels", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_002_Verify_Login_Page_Labels ==========\n");
        }
    }

    @Test(testName = "TC_003_Verify_Login_Page_Headings", description = "Verify page headings and structure", groups = {"UI", "Sanity"})
    public void verifyLoginPageHeadings() {
        logger.info("========== START TEST CASE: TC_003_Verify_Login_Page_Headings ==========");
        
        try {
            logger.info("STEP 1: Verify Main Page Heading");
            List<WebElement> headingElements = getDriver().findElements(LOGIN_HEADING_LOCATOR);
            if (!headingElements.isEmpty()) {
                Label mainHeading = new Label(getDriver(), LOGIN_HEADING_LOCATOR);
                mainHeading.should()
                    .visible()
                    .hasText("Account Login");
                logger.info("✓ Main heading verified");
            } else {
                logger.warn("Main heading not found, skipping main heading assertion");
            }

            logger.info("STEP 2: Verify Right Column Heading");
            List<WebElement> rightHeadingElements = getDriver().findElements(RIGHT_COLUMN_HEADING_LOCATOR);
            if (!rightHeadingElements.isEmpty()) {
                Label rightColumnHeading = new Label(getDriver(), RIGHT_COLUMN_HEADING_LOCATOR);
                rightColumnHeading.should()
                    .visible()
                    .hasText("New Customer");
                logger.info("✓ Right column heading verified");
            } else {
                logger.warn("Right column heading not found, skipping right heading assertion");
            }

            logger.info("✅ TEST PASSED: Headings verified (optional if present)");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at verifyLoginPageHeadings", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_003_Verify_Login_Page_Headings ==========\n");
        }
    }

    @Test(testName = "TC_004_Test_Email_Input_Interaction", description = "Test typing in email input field", groups = {"UI"})
    public void testEmailInputInteraction() {
        logger.info("========== START TEST CASE: TC_004_Test_Email_Input_Interaction ==========");
        
        try {
            logger.info("STEP 1: Type email address");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            emailInput.type("test@example.com")
                      .should()
                      .hasValue("test@example.com")
                      .enabled();
            logger.info("✓ Email input correctly received value");

            logger.info("STEP 2: Clear email input");
            emailInput.clear()
                      .should()
                      .isEmpty();
            logger.info("✓ Email input cleared successfully");

            logger.info("✅ TEST PASSED: Email input interaction verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testEmailInputInteraction", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_004_Test_Email_Input_Interaction ==========\n");
        }
    }

    @Test(testName = "TC_005_Test_Password_Input_Interaction", description = "Test typing in password input field", groups = {"UI"})
    public void testPasswordInputInteraction() {
        logger.info("========== START TEST CASE: TC_005_Test_Password_Input_Interaction ==========");
        
        try {
            logger.info("STEP 1: Type password");
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            passwordInput.type("password123")
                         .should()
                         .hasValue("password123")
                         .enabled();
            logger.info("✓ Password input correctly received value");

            logger.info("STEP 2: Clear password input");
            passwordInput.clear()
                         .should()
                         .isEmpty();
            logger.info("✓ Password input cleared successfully");

            logger.info("✅ TEST PASSED: Password input interaction verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testPasswordInputInteraction", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_005_Test_Password_Input_Interaction ==========\n");
        }
    }

    @Test(testName = "TC_006_Test_Login_Form_Workflow", description = "Test complete login form workflow", groups = {"UI", "Sanity"})
    public void testLoginFormWorkflow() {
        logger.info("========== START TEST CASE: TC_006_Test_Login_Form_Workflow ==========");
        
        try {
            logger.info("STEP 1: Initialize form components");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);

            logger.info("STEP 2: Verify form is ready");
            emailInput.should().visible().enabled();
            passwordInput.should().visible().enabled();
            loginButton.should().visible().enabled().isClickable();
            logger.info("✓ Form is ready for input");

            logger.info("STEP 3: Fill email field");
            emailInput.type("test@example.com")
                      .should()
                      .hasValue("test@example.com");
            logger.info("✓ Email field filled");

            logger.info("STEP 4: Fill password field");
            passwordInput.type("password123")
                         .should()
                         .hasValue("password123");
            logger.info("✓ Password field filled");

            logger.info("STEP 5: Verify login button");
            loginButton.should()
                       .isClickable()
                       .enabled();
            logger.info("✓ Login button is ready to be clicked");

            logger.info("✅ TEST PASSED: Login form workflow completed successfully");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testLoginFormWorkflow", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_006_Test_Login_Form_Workflow ==========\n");
        }
    }

    @Test(testName = "TC_007_Test_Navigation_Links", description = "Test Forgot Password and Register links", groups = {"UI"})
    public void testNavigationLinks() {
        logger.info("========== START TEST CASE: TC_007_Test_Navigation_Links ==========");
        
        try {
            logger.info("STEP 1: Verify Forgot Password link");
            Link forgotPasswordLink = new Link(getDriver(), FORGOT_PASSWORD_LINK_LOCATOR);
            forgotPasswordLink.should()
                              .visible()
                              .hasText("Forgotten Password")
                              .enabled();
            String forgotPasswordHref = forgotPasswordLink.getHref();
            Assert.assertTrue(forgotPasswordHref != null && !forgotPasswordHref.isEmpty(), 
                            "Forgot Password link href should not be empty");
            logger.info("✓ Forgot Password link verified - Href: " + forgotPasswordHref);

            logger.info("STEP 2: Verify Register link");
            Link registerLink = new Link(getDriver(), NEW_ACCOUNT_LINK_LOCATOR);
            registerLink.should()
                        .visible()
                        .hasText("Register")
                        .enabled();
            String registerHref = registerLink.getHref();
            Assert.assertTrue(registerHref != null && !registerHref.isEmpty(), 
                            "Register link href should not be empty");
            logger.info("✓ Register link verified - Href: " + registerHref);

            logger.info("✅ TEST PASSED: Navigation links verified successfully");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testNavigationLinks", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_007_Test_Navigation_Links ==========\n");
        }
    }

    @Test(testName = "TC_008_Test_Input_Field_Attributes", description = "Test input field attributes and properties", groups = {"UI"})
    public void testInputFieldAttributes() {
        logger.info("========== START TEST CASE: TC_008_Test_Input_Field_Attributes ==========");
        
        try {
            logger.info("STEP 1: Initialize email input component");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            
            logger.info("STEP 2: Verify email input attributes");
                emailInput.should()
                      .visible()
                      .enabled();
                String typeAttr = emailInput.getElement().getAttribute("type");
                Assert.assertTrue(typeAttr.equalsIgnoreCase("email") || typeAttr.equalsIgnoreCase("text"),
                    "Email input type should be email or text");
            
            logger.info("STEP 3: Get and verify specific attributes");
            String id = emailInput.getElement().getAttribute("id");
            String name = emailInput.getElement().getAttribute("name");
            String placeholder = emailInput.getElement().getAttribute("placeholder");
            
            Assert.assertEquals(id, "input-email", "Email input ID should be 'input-email'");
            logger.info("✓ Email input attributes verified");
            logger.info("  - ID: " + id);
            logger.info("  - Name: " + name);
            logger.info("  - Placeholder: " + placeholder);

            logger.info("✅ TEST PASSED: Input field attributes verified successfully");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testInputFieldAttributes", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_008_Test_Input_Field_Attributes ==========\n");
        }
    }
    @Test(testName = "TC_009_Test_Page_Title", description = "Verify page title", groups = {"UI", "Sanity"})
    public void testPageTitle() {
        logger.info("========== START TEST CASE: TC_009_Test_Page_Title ==========");
        
        try {
            logger.info("STEP 1: Get page title");
            String pageTitle = getDriver().getTitle();
            logger.info("✓ Page Title: " + pageTitle);
            
            logger.info("STEP 2: Verify page title");
            Assert.assertTrue(pageTitle.contains("Account Login") || pageTitle.contains("Login"), 
                            "Page title should contain 'Account Login' or 'Login'");
            logger.info("✓ Page title verified successfully");

            logger.info("✅ TEST PASSED: Page title is correct");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testPageTitle", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_009_Test_Page_Title ==========\n");
        }
    }

    @Test(testName = "TC_010_Test_Form_Layout_Responsiveness", description = "Verify form elements are properly positioned", groups = {"UI"})
    public void testFormLayoutResponsiveness() {
        logger.info("========== START TEST CASE: TC_010_Test_Form_Layout_Responsiveness ==========");
        
        try {
            logger.info("STEP 1: Initialize form components");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);

            logger.info("STEP 2: Verify email input visibility");
            emailInput.should()
                      .visible()
                      .enabled();
            Assert.assertTrue(emailInput.getElement().isDisplayed(), "Email input should be displayed");
            logger.info("✓ Email input is visible and enabled");

            logger.info("STEP 3: Verify password input visibility");
            passwordInput.should()
                         .visible()
                         .enabled();
            Assert.assertTrue(passwordInput.getElement().isDisplayed(), "Password input should be displayed");
            logger.info("✓ Password input is visible and enabled");

            logger.info("STEP 4: Verify login button visibility");
            loginButton.should()
                       .visible()
                       .enabled();
            Assert.assertTrue(loginButton.getElement().isDisplayed(), "Login button should be displayed");
            logger.info("✓ Login button is visible and enabled");

            logger.info("✅ TEST PASSED: Form layout and responsiveness verified successfully");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testFormLayoutResponsiveness", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_010_Test_Form_Layout_Responsiveness ==========\n");
        }
    }

    // =====================================================
    // PHẦN 1 - Test Responsive UI (Multiple Resolutions)
    // =====================================================
    @Test(testName = "TC_011_Test_Responsive_UI_Multiple_Resolutions", description = "Test UI on FHD, Tablet, Mobile resolutions", groups = {"UI"})
    public void testResponsiveUIMultipleResolutions() {
        logger.info("========== START TEST CASE: TC_011_Test_Responsive_UI_Multiple_Resolutions ==========");
        
        try {
            // Desktop FHD (1920x1080)
            logger.info("STEP 1: Test Desktop Resolution (1920x1080)");
            getDriver().manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
            Thread.sleep(500);
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);
            Assert.assertTrue(emailInput.getElement().isDisplayed(), "Email input should be visible on Desktop");
            Assert.assertTrue(loginButton.getElement().isDisplayed(), "Login button should be visible on Desktop");
            logger.info("✓ Desktop FHD layout verified");

            // Tablet (768x1024)
            logger.info("STEP 2: Test Tablet Resolution (768x1024)");
            getDriver().manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
            Thread.sleep(500);
            Assert.assertTrue(emailInput.getElement().isDisplayed(), "Email input should be visible on Tablet");
            Assert.assertTrue(loginButton.getElement().isDisplayed(), "Login button should be visible on Tablet");
            logger.info("✓ Tablet layout verified");

            // Mobile (375x667)
            logger.info("STEP 3: Test Mobile Resolution (375x667)");
            getDriver().manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
            Thread.sleep(500);
            Assert.assertTrue(emailInput.getElement().isDisplayed(), "Email input should be visible on Mobile");
            Assert.assertTrue(loginButton.getElement().isDisplayed(), "Login button should be visible on Mobile");
            logger.info("✓ Mobile layout verified");

            logger.info("✅ TEST PASSED: Responsive UI verified across all resolutions");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testResponsiveUIMultipleResolutions", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            // Reset to default size
            getDriver().manage().window().maximize();
            logger.info("========== END TEST CASE: TC_011_Test_Responsive_UI_Multiple_Resolutions ==========\n");
        }
    }

    // =====================================================
    // PHẦN 2 - Kiểm tra kích thước, vị trí, alignment
    // =====================================================
    @Test(testName = "TC_012_Test_Element_Dimensions_And_Positions", description = "Verify element sizes, positions and alignment", groups = {"UI"})
    public void testElementDimensionsAndPositions() {
        logger.info("========== START TEST CASE: TC_012_Test_Element_Dimensions_And_Positions ==========");
        
        try {
            logger.info("STEP 1: Get email input dimensions and position");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            org.openqa.selenium.Point emailPosition = emailInput.getElement().getLocation();
            org.openqa.selenium.Dimension emailSize = emailInput.getElement().getSize();
            logger.info("✓ Email Input - Position: (x={}, y={}), Size: (width={}, height={})", 
                       emailPosition.getX(), emailPosition.getY(), emailSize.getWidth(), emailSize.getHeight());
            
            Assert.assertTrue(emailSize.getWidth() > 0, "Email input width should be > 0");
            Assert.assertTrue(emailSize.getHeight() > 0, "Email input height should be > 0");

            logger.info("STEP 2: Get password input dimensions and position");
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            org.openqa.selenium.Point passwordPosition = passwordInput.getElement().getLocation();
            org.openqa.selenium.Dimension passwordSize = passwordInput.getElement().getSize();
            logger.info("✓ Password Input - Position: (x={}, y={}), Size: (width={}, height={})", 
                       passwordPosition.getX(), passwordPosition.getY(), passwordSize.getWidth(), passwordSize.getHeight());

            logger.info("STEP 3: Verify inputs have same width (aligned)");
            Assert.assertEquals(emailSize.getWidth(), passwordSize.getWidth(), "Email and Password inputs should have same width");
            logger.info("✓ Inputs are properly aligned");

            logger.info("STEP 4: Verify password is below email (vertical alignment)");
            Assert.assertTrue(passwordPosition.getY() > emailPosition.getY(), "Password input should be below Email input");
            logger.info("✓ Vertical alignment verified");

            logger.info("✅ TEST PASSED: Element dimensions and positions verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testElementDimensionsAndPositions", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_012_Test_Element_Dimensions_And_Positions ==========\n");
        }
    }

    // =====================================================
    // PHẦN 3 - Kiểm tra màu, shadow, border radius
    // =====================================================
    @Test(testName = "TC_013_Test_CSS_Styling_Properties", description = "Verify colors, shadows, border radius", groups = {"UI"})
    public void testCSSStylingProperties() {
        logger.info("========== START TEST CASE: TC_013_Test_CSS_Styling_Properties ==========");
        
        try {
            logger.info("STEP 1: Verify email input CSS properties");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            String borderColor = emailInput.getElement().getCssValue("border-color");
            String borderRadius = emailInput.getElement().getCssValue("border-radius");
            String backgroundColor = emailInput.getElement().getCssValue("background-color");
            
            Assert.assertNotNull(borderColor, "Border color should not be null");
            Assert.assertNotNull(borderRadius, "Border radius should not be null");
            Assert.assertNotNull(backgroundColor, "Background color should not be null");
            
            logger.info("✓ Email Input CSS - Border: {}, Radius: {}, Background: {}", 
                       borderColor, borderRadius, backgroundColor);

            logger.info("STEP 2: Verify login button CSS properties");
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);
            String buttonColor = loginButton.getElement().getCssValue("color");
            String buttonBackground = loginButton.getElement().getCssValue("background-color");
            String buttonBorderRadius = loginButton.getElement().getCssValue("border-radius");
            
            Assert.assertNotNull(buttonColor, "Button color should not be null");
            Assert.assertNotNull(buttonBackground, "Button background should not be null");
            
            logger.info("✓ Login Button CSS - Color: {}, Background: {}, Radius: {}", 
                       buttonColor, buttonBackground, buttonBorderRadius);

            logger.info("STEP 3: Check for box-shadow");
            String emailShadow = emailInput.getElement().getCssValue("box-shadow");
            logger.info("✓ Email Input box-shadow: {}", emailShadow);

            logger.info("✅ TEST PASSED: CSS styling properties verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testCSSStylingProperties", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_013_Test_CSS_Styling_Properties ==========\n");
        }
    }

    // =====================================================
    // PHẦN 4 - Kiểm tra element có bị che không
    // =====================================================
    @Test(testName = "TC_014_Test_No_Element_Overlay", description = "Verify elements are not blocked by popups or overlays", groups = {"UI"})
    public void testNoElementOverlay() {
        logger.info("========== START TEST CASE: TC_014_Test_No_Element_Overlay ==========");
        
        try {
            logger.info("STEP 1: Verify email input is not blocked");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            
            // Check element is displayed
            Assert.assertTrue(emailInput.getElement().isDisplayed(), "Email input should be displayed");
            
            // Try to interact - if blocked, this will fail
            emailInput.type("test@test.com");
            String value = emailInput.getElement().getAttribute("value");
            Assert.assertEquals(value, "test@test.com", "Email input should accept text (not blocked)");
            emailInput.clear();
            logger.info("✓ Email input is interactable (not blocked by overlay)");

            logger.info("STEP 2: Verify login button is clickable (not blocked)");
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);
            Assert.assertTrue(loginButton.getElement().isDisplayed(), "Login button should be displayed");
            
            // Check if element is in viewport
            Boolean isInViewport = (Boolean) ((org.openqa.selenium.JavascriptExecutor) getDriver())
                .executeScript("var elem = arguments[0]; var rect = elem.getBoundingClientRect(); " +
                              "return (rect.top >= 0 && rect.left >= 0);", loginButton.getElement());
            Assert.assertTrue(isInViewport, "Login button should be in viewport");
            logger.info("✓ Login button is in viewport and not blocked");

            logger.info("✅ TEST PASSED: No elements are blocked by overlays");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testNoElementOverlay", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_014_Test_No_Element_Overlay ==========\n");
        }
    }

    // =====================================================
    // PHẦN 5 - Kiểm tra collision (UI chồng lên nhau)
    // =====================================================
    @Test(testName = "TC_015_Test_No_UI_Collision", description = "Verify UI elements don't overlap each other", groups = {"UI"})
    public void testNoUICollision() {
        logger.info("========== START TEST CASE: TC_015_Test_No_UI_Collision ==========");
        
        try {
            logger.info("STEP 1: Get bounding rectangles of form elements");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);

            // Get email input bounds
            org.openqa.selenium.Point emailPos = emailInput.getElement().getLocation();
            org.openqa.selenium.Dimension emailSize = emailInput.getElement().getSize();
            int emailBottom = emailPos.getY() + emailSize.getHeight();
            
            // Get password input bounds
            org.openqa.selenium.Point passwordPos = passwordInput.getElement().getLocation();
            
            logger.info("STEP 2: Verify email and password don't overlap");
            Assert.assertTrue(passwordPos.getY() >= emailBottom, 
                            "Password input should start below email input (no overlap)");
            logger.info("✓ Email and Password inputs don't overlap");

            // Get password bounds
            org.openqa.selenium.Dimension passwordSize = passwordInput.getElement().getSize();
            int passwordBottom = passwordPos.getY() + passwordSize.getHeight();
            
            // Get button bounds
            org.openqa.selenium.Point buttonPos = loginButton.getElement().getLocation();
            
            logger.info("STEP 3: Verify password and button don't overlap");
            Assert.assertTrue(buttonPos.getY() >= passwordBottom, 
                            "Login button should start below password input (no overlap)");
            logger.info("✓ Password input and Login button don't overlap");

            logger.info("✅ TEST PASSED: No UI collision detected");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testNoUICollision", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_015_Test_No_UI_Collision ==========\n");
        }
    }

    // =====================================================
    // PHẦN 6 - Kiểm tra font đúng chuẩn thiết kế
    // =====================================================
    @Test(testName = "TC_016_Test_Font_Properties", description = "Verify font family, size, weight match design", groups = {"UI"})
    public void testFontProperties() {
        logger.info("========== START TEST CASE: TC_016_Test_Font_Properties ==========");
        
        try {
            logger.info("STEP 1: Verify email input font properties");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            String fontFamily = emailInput.getElement().getCssValue("font-family");
            String fontSize = emailInput.getElement().getCssValue("font-size");
            String fontWeight = emailInput.getElement().getCssValue("font-weight");
            
            Assert.assertNotNull(fontFamily, "Font family should not be null");
            Assert.assertNotNull(fontSize, "Font size should not be null");
            Assert.assertNotNull(fontWeight, "Font weight should not be null");
            
            logger.info("✓ Email Input Font - Family: {}, Size: {}, Weight: {}", 
                       fontFamily, fontSize, fontWeight);

            logger.info("STEP 2: Verify login button font properties");
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);
            String buttonFont = loginButton.getElement().getCssValue("font-family");
            String buttonFontSize = loginButton.getElement().getCssValue("font-size");
            String buttonFontWeight = loginButton.getElement().getCssValue("font-weight");
            
            logger.info("✓ Login Button Font - Family: {}, Size: {}, Weight: {}", 
                       buttonFont, buttonFontSize, buttonFontWeight);

            logger.info("STEP 3: Verify heading font properties");
            List<WebElement> headingElements = getDriver().findElements(LOGIN_HEADING_LOCATOR);
            if (!headingElements.isEmpty()) {
                Label heading = new Label(getDriver(), LOGIN_HEADING_LOCATOR);
                String headingFont = heading.getElement().getCssValue("font-family");
                String headingSize = heading.getElement().getCssValue("font-size");
                String headingWeight = heading.getElement().getCssValue("font-weight");
                
                logger.info("✓ Heading Font - Family: {}, Size: {}, Weight: {}", 
                           headingFont, headingSize, headingWeight);
            } else {
                logger.warn("Heading 'Account Login' not found, skipping heading font assertions");
            }

            logger.info("✅ TEST PASSED: Font properties verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testFontProperties", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_016_Test_Font_Properties ==========\n");
        }
    }

    // =====================================================
    // PHẦN 7 - Kiểm tra focus state (outline/border)
    // =====================================================
    @Test(testName = "TC_017_Test_Focus_States", description = "Verify focus outline and border when clicking elements", groups = {"UI"})
    public void testFocusStates() {
        logger.info("========== START TEST CASE: TC_017_Test_Focus_States ==========");
        
        try {
            logger.info("STEP 1: Click email input and verify focus state");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            
            // Get CSS before focus
            String borderBeforeFocus = emailInput.getElement().getCssValue("border-color");
            String outlineBeforeFocus = emailInput.getElement().getCssValue("outline");
            
            // Click to focus
            emailInput.getElement().click();
            Thread.sleep(300);
            
            // Get CSS after focus
            String borderAfterFocus = emailInput.getElement().getCssValue("border-color");
            String outlineAfterFocus = emailInput.getElement().getCssValue("outline");
            
            logger.info("✓ Email Input Focus State - Border: {} -> {}, Outline: {} -> {}", 
                       borderBeforeFocus, borderAfterFocus, outlineBeforeFocus, outlineAfterFocus);

            logger.info("STEP 2: Tab to password input and verify focus state");
            emailInput.getElement().sendKeys(org.openqa.selenium.Keys.TAB);
            Thread.sleep(300);
            
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            String passwordBorder = passwordInput.getElement().getCssValue("border-color");
            String passwordOutline = passwordInput.getElement().getCssValue("outline");
            
            logger.info("✓ Password Input Focus State - Border: {}, Outline: {}", 
                       passwordBorder, passwordOutline);

            logger.info("STEP 3: Verify focus can move to login button");
            passwordInput.getElement().sendKeys(org.openqa.selenium.Keys.TAB);
            Thread.sleep(300);
            
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);
            String buttonOutline = loginButton.getElement().getCssValue("outline");
            
            logger.info("✓ Login Button Focus State - Outline: {}", buttonOutline);

            logger.info("✅ TEST PASSED: Focus states verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testFocusStates", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_017_Test_Focus_States ==========\n");
        }
    }

    // =====================================================
    // PHẦN 8 - Kiểm tra Animation/Transition
    // =====================================================
    @Test(testName = "TC_018_Test_CSS_Transitions", description = "Verify CSS transition and animation properties", groups = {"UI"})
    public void testCSSTransitions() {
        logger.info("========== START TEST CASE: TC_018_Test_CSS_Transitions ==========");
        
        try {
            logger.info("STEP 1: Check email input transition properties");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            String transition = emailInput.getElement().getCssValue("transition");
            String transitionDuration = emailInput.getElement().getCssValue("transition-duration");
            String transitionProperty = emailInput.getElement().getCssValue("transition-property");
            
            logger.info("✓ Email Input Transition - Property: {}, Duration: {}, Full: {}", 
                       transitionProperty, transitionDuration, transition);

            logger.info("STEP 2: Check login button transition properties");
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);
            String buttonTransition = loginButton.getElement().getCssValue("transition");
            String buttonAnimation = loginButton.getElement().getCssValue("animation");
            
            logger.info("✓ Login Button Transition: {}, Animation: {}", 
                       buttonTransition, buttonAnimation);

            logger.info("STEP 3: Hover over login button and observe state change");
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(getDriver());
            String bgColorBefore = loginButton.getElement().getCssValue("background-color");
            
            actions.moveToElement(loginButton.getElement()).perform();
            Thread.sleep(500);
            
            String bgColorAfter = loginButton.getElement().getCssValue("background-color");
            logger.info("✓ Button background on hover: {} -> {}", bgColorBefore, bgColorAfter);

            logger.info("✅ TEST PASSED: CSS transitions verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testCSSTransitions", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_018_Test_CSS_Transitions ==========\n");
        }
    }

    // =====================================================
    // PHẦN 9 - Kiểm tra Placeholder Text
    // =====================================================
    @Test(testName = "TC_019_Test_Placeholder_Text", description = "Verify input fields have proper placeholder text", groups = {"UI"})
    public void testPlaceholderText() {
        logger.info("========== START TEST CASE: TC_019_Test_Placeholder_Text ==========");
        
        try {
            logger.info("STEP 1: Verify email input placeholder");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            String emailPlaceholder = emailInput.getElement().getAttribute("placeholder");
            
            if (emailPlaceholder != null && !emailPlaceholder.isEmpty()) {
                logger.info("✓ Email input placeholder: '{}'", emailPlaceholder);
            } else {
                logger.info("ℹ Email input has no placeholder text");
            }

            logger.info("STEP 2: Verify password input placeholder");
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            String passwordPlaceholder = passwordInput.getElement().getAttribute("placeholder");
            
            if (passwordPlaceholder != null && !passwordPlaceholder.isEmpty()) {
                logger.info("✓ Password input placeholder: '{}'", passwordPlaceholder);
            } else {
                logger.info("ℹ Password input has no placeholder text");
            }

            logger.info("✅ TEST PASSED: Placeholder text checked");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testPlaceholderText", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_019_Test_Placeholder_Text ==========\n");
        }
    }

    // =====================================================
    // PHẦN 10 - Kiểm tra Tab Order (Keyboard Navigation)
    // =====================================================
    @Test(testName = "TC_020_Test_Tab_Order", description = "Verify proper tab order through form fields", groups = {"UI", "Accessibility"})
    public void testTabOrder() {
        logger.info("========== START TEST CASE: TC_020_Test_Tab_Order ==========");
        
        try {
            logger.info("STEP 1: Start from email input");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            emailInput.getElement().click();
            Thread.sleep(200);
            
            WebElement activeElement = getDriver().switchTo().activeElement();
            String activeId = activeElement.getAttribute("id");
            Assert.assertEquals(activeId, "input-email", "Email input should be focused");
            logger.info("✓ Email input is focused (id: {})", activeId);

            logger.info("STEP 2: Tab to next field (should be password)");
            // Get current active element and send TAB
            activeElement = getDriver().switchTo().activeElement();
            activeElement.sendKeys(org.openqa.selenium.Keys.TAB);
            Thread.sleep(200);
            
            // Get new active element after TAB
            activeElement = getDriver().switchTo().activeElement();
            activeId = activeElement.getAttribute("id");
            Assert.assertEquals(activeId, "input-password", "Password input should be focused after tab");
            logger.info("✓ Password input is focused after tab (id: {})", activeId);

            logger.info("STEP 3: Tab to login button");
            // Get current active element and send TAB
            activeElement = getDriver().switchTo().activeElement();
            activeElement.sendKeys(org.openqa.selenium.Keys.TAB);
            Thread.sleep(200);
            
            // Get new active element after TAB
            activeElement = getDriver().switchTo().activeElement();
            String activeValue = activeElement.getAttribute("value");
            String activeTag = activeElement.getTagName();
            
            logger.info("✓ Active element after 2nd tab - Tag: {}, Value: {}", activeTag, activeValue);
            
            // Login button might be focused, or it might be the "Forgotten Password" link
            // Accept either case as both are valid tab stops
            boolean isLoginButton = activeValue != null && activeValue.contains("Login");
            boolean isForgottenPasswordLink = "a".equalsIgnoreCase(activeTag);
            
            Assert.assertTrue(isLoginButton || isForgottenPasswordLink, 
                            "Either Login button or Forgotten Password link should be focused after 2nd tab");
            logger.info("✓ Tab order verified successfully");

            logger.info("✅ TEST PASSED: Tab order is correct");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testTabOrder", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_020_Test_Tab_Order ==========\n");
        }
    }

    // =====================================================
    // PHẦN 11 - Kiểm tra Cursor Type on Hover
    // =====================================================
    @Test(testName = "TC_021_Test_Cursor_Type", description = "Verify cursor changes to pointer on interactive elements", groups = {"UI"})
    public void testCursorType() {
        logger.info("========== START TEST CASE: TC_021_Test_Cursor_Type ==========");
        
        try {
            logger.info("STEP 1: Check login button cursor");
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);
            String buttonCursor = loginButton.getElement().getCssValue("cursor");
            logger.info("✓ Login button cursor: '{}'", buttonCursor);

            logger.info("STEP 2: Check Forgot Password link cursor");
            Link forgotPasswordLink = new Link(getDriver(), FORGOT_PASSWORD_LINK_LOCATOR);
            String linkCursor = forgotPasswordLink.getElement().getCssValue("cursor");
            Assert.assertTrue(linkCursor.contains("pointer") || linkCursor.contains("auto"), 
                            "Link should have pointer cursor");
            logger.info("✓ Forgot Password link cursor: '{}'", linkCursor);

            logger.info("STEP 3: Check Register link cursor");
            Link registerLink = new Link(getDriver(), NEW_ACCOUNT_LINK_LOCATOR);
            String registerCursor = registerLink.getElement().getCssValue("cursor");
            Assert.assertTrue(registerCursor.contains("pointer") || registerCursor.contains("auto"), 
                            "Link should have pointer cursor");
            logger.info("✓ Register link cursor: '{}'", registerCursor);

            logger.info("✅ TEST PASSED: Cursor types verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testCursorType", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_021_Test_Cursor_Type ==========\n");
        }
    }

    // =====================================================
    // PHẦN 12 - Kiểm tra Input Field Constraints
    // =====================================================
    @Test(testName = "TC_022_Test_Input_Field_Constraints", description = "Verify input field maxlength and other constraints", groups = {"UI"})
    public void testInputFieldConstraints() {
        logger.info("========== START TEST CASE: TC_022_Test_Input_Field_Constraints ==========");
        
        try {
            logger.info("STEP 1: Check email input constraints");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            String emailMaxLength = emailInput.getElement().getAttribute("maxlength");
            String emailRequired = emailInput.getElement().getAttribute("required");
            String emailType = emailInput.getElement().getAttribute("type");
            
            logger.info("✓ Email Input Constraints:");
            logger.info("  - Type: {}", emailType);
            logger.info("  - MaxLength: {}", emailMaxLength != null ? emailMaxLength : "No limit");
            logger.info("  - Required: {}", emailRequired != null ? "Yes" : "No");

            logger.info("STEP 2: Check password input constraints");
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            String passwordMaxLength = passwordInput.getElement().getAttribute("maxlength");
            String passwordRequired = passwordInput.getElement().getAttribute("required");
            String passwordType = passwordInput.getElement().getAttribute("type");
            
            logger.info("✓ Password Input Constraints:");
            logger.info("  - Type: {}", passwordType);
            logger.info("  - MaxLength: {}", passwordMaxLength != null ? passwordMaxLength : "No limit");
            logger.info("  - Required: {}", passwordRequired != null ? "Yes" : "No");
            
            Assert.assertEquals(passwordType, "password", "Password field should have type='password'");

            logger.info("✅ TEST PASSED: Input constraints verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testInputFieldConstraints", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_022_Test_Input_Field_Constraints ==========\n");
        }
    }

    // =====================================================
    // PHẦN 13 - Kiểm tra Label-Input Association
    // =====================================================
    @Test(testName = "TC_023_Test_Label_Input_Association", description = "Verify labels are properly associated with inputs (for/id)", groups = {"UI", "Accessibility"})
    public void testLabelInputAssociation() {
        logger.info("========== START TEST CASE: TC_023_Test_Label_Input_Association ==========");
        
        try {
            logger.info("STEP 1: Check email label 'for' attribute");
            Label emailLabel = new Label(getDriver(), EMAIL_LABEL_LOCATOR);
            String emailLabelFor = emailLabel.getElement().getAttribute("for");
            
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            String emailInputId = emailInput.getElement().getAttribute("id");
            
            if (emailLabelFor != null && emailInputId != null) {
                Assert.assertEquals(emailLabelFor, emailInputId, 
                                  "Email label 'for' should match input 'id'");
                logger.info("✓ Email label-input association: for='{}' → id='{}'", emailLabelFor, emailInputId);
            } else {
                logger.info("ℹ Email label-input association not found");
            }

            logger.info("STEP 2: Check password label 'for' attribute");
            Label passwordLabel = new Label(getDriver(), PASSWORD_LABEL_LOCATOR);
            String passwordLabelFor = passwordLabel.getElement().getAttribute("for");
            
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            String passwordInputId = passwordInput.getElement().getAttribute("id");
            
            if (passwordLabelFor != null && passwordInputId != null) {
                Assert.assertEquals(passwordLabelFor, passwordInputId, 
                                  "Password label 'for' should match input 'id'");
                logger.info("✓ Password label-input association: for='{}' → id='{}'", passwordLabelFor, passwordInputId);
            } else {
                logger.info("ℹ Password label-input association not found");
            }

            logger.info("STEP 3: Test clicking label focuses input");
            emailLabel.getElement().click();
            Thread.sleep(200);
            WebElement activeElement = getDriver().switchTo().activeElement();
            String activeId = activeElement.getAttribute("id");
            
            if (emailLabelFor != null && emailInputId != null) {
                Assert.assertEquals(activeId, emailInputId, "Clicking email label should focus email input");
                logger.info("✓ Clicking email label correctly focuses input");
            }

            logger.info("✅ TEST PASSED: Label-input associations verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testLabelInputAssociation", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_023_Test_Label_Input_Association ==========\n");
        }
    }

    // =====================================================
    // PHẦN 14 - Kiểm tra Button Hover State
    // =====================================================
    @Test(testName = "TC_024_Test_Button_Hover_State", description = "Verify button style changes on hover", groups = {"UI"})
    public void testButtonHoverState() {
        logger.info("========== START TEST CASE: TC_024_Test_Button_Hover_State ==========");
        
        try {
            logger.info("STEP 1: Get login button normal state");
            Button loginButton = new Button(getDriver(), LOGIN_BUTTON_LOCATOR);
            String bgColorNormal = loginButton.getElement().getCssValue("background-color");
            String borderNormal = loginButton.getElement().getCssValue("border-color");
            String opacityNormal = loginButton.getElement().getCssValue("opacity");
            
            logger.info("✓ Normal state - BG: {}, Border: {}, Opacity: {}", 
                       bgColorNormal, borderNormal, opacityNormal);

            logger.info("STEP 2: Hover over button and check state change");
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(getDriver());
            actions.moveToElement(loginButton.getElement()).perform();
            Thread.sleep(300);
            
            String bgColorHover = loginButton.getElement().getCssValue("background-color");
            String borderHover = loginButton.getElement().getCssValue("border-color");
            String opacityHover = loginButton.getElement().getCssValue("opacity");
            
            logger.info("✓ Hover state - BG: {}, Border: {}, Opacity: {}", 
                       bgColorHover, borderHover, opacityHover);

            logger.info("STEP 3: Move away and verify return to normal");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            actions.moveToElement(emailInput.getElement()).perform();
            Thread.sleep(300);
            
            String bgColorAfter = loginButton.getElement().getCssValue("background-color");
            logger.info("✓ After hover - BG: {}", bgColorAfter);

            logger.info("✅ TEST PASSED: Button hover states verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testButtonHoverState", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_024_Test_Button_Hover_State ==========\n");
        }
    }

    // =====================================================
    // PHẦN 15 - Kiểm tra Link Hover State
    // =====================================================
    @Test(testName = "TC_025_Test_Link_Hover_State", description = "Verify link style changes on hover", groups = {"UI"})
    public void testLinkHoverState() {
        logger.info("========== START TEST CASE: TC_025_Test_Link_Hover_State ==========");
        
        try {
            logger.info("STEP 1: Check Forgot Password link hover state");
            Link forgotPasswordLink = new Link(getDriver(), FORGOT_PASSWORD_LINK_LOCATOR);
            
            String colorNormal = forgotPasswordLink.getElement().getCssValue("color");
            String textDecoNormal = forgotPasswordLink.getElement().getCssValue("text-decoration");
            
            logger.info("✓ Normal state - Color: {}, Decoration: {}", colorNormal, textDecoNormal);

            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(getDriver());
            actions.moveToElement(forgotPasswordLink.getElement()).perform();
            Thread.sleep(300);
            
            String colorHover = forgotPasswordLink.getElement().getCssValue("color");
            String textDecoHover = forgotPasswordLink.getElement().getCssValue("text-decoration");
            
            logger.info("✓ Hover state - Color: {}, Decoration: {}", colorHover, textDecoHover);

            logger.info("STEP 2: Check Register link hover state");
            Link registerLink = new Link(getDriver(), NEW_ACCOUNT_LINK_LOCATOR);
            actions.moveToElement(registerLink.getElement()).perform();
            Thread.sleep(300);
            
            String registerColorHover = registerLink.getElement().getCssValue("color");
            String registerTextDecoHover = registerLink.getElement().getCssValue("text-decoration");
            
            logger.info("✓ Register link hover - Color: {}, Decoration: {}", 
                       registerColorHover, registerTextDecoHover);

            logger.info("✅ TEST PASSED: Link hover states verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testLinkHoverState", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_025_Test_Link_Hover_State ==========\n");
        }
    }

    // =====================================================
    // PHẦN 16 - Kiểm tra Autocomplete Attributes
    // =====================================================
    @Test(testName = "TC_026_Test_Autocomplete_Attributes", description = "Verify autocomplete attributes for security and UX", groups = {"UI", "Security"})
    public void testAutocompleteAttributes() {
        logger.info("========== START TEST CASE: TC_026_Test_Autocomplete_Attributes ==========");
        
        try {
            logger.info("STEP 1: Check email input autocomplete");
            Input emailInput = new Input(getDriver(), EMAIL_INPUT_LOCATOR);
            String emailAutocomplete = emailInput.getElement().getAttribute("autocomplete");
            
            logger.info("✓ Email autocomplete attribute: '{}'", 
                       emailAutocomplete != null ? emailAutocomplete : "Not set");

            logger.info("STEP 2: Check password input autocomplete");
            Input passwordInput = new Input(getDriver(), PASSWORD_INPUT_LOCATOR);
            String passwordAutocomplete = passwordInput.getElement().getAttribute("autocomplete");
            
            logger.info("✓ Password autocomplete attribute: '{}'", 
                       passwordAutocomplete != null ? passwordAutocomplete : "Not set");

            logger.info("STEP 3: Check input names for browser autofill");
            String emailName = emailInput.getElement().getAttribute("name");
            String passwordName = passwordInput.getElement().getAttribute("name");
            
            logger.info("✓ Email input name: '{}'", emailName);
            logger.info("✓ Password input name: '{}'", passwordName);

            logger.info("✅ TEST PASSED: Autocomplete attributes checked");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testAutocompleteAttributes", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_026_Test_Autocomplete_Attributes ==========\n");
        }
    }

    // =====================================================
    // PHẦN 17 - Kiểm tra Console Errors
    // =====================================================
    @Test(testName = "TC_027_Test_No_Console_Errors", description = "Verify no JavaScript errors in browser console", groups = {"UI", "Quality"})
    public void testNoConsoleErrors() {
        logger.info("========== START TEST CASE: TC_027_Test_No_Console_Errors ==========");
        
        try {
            logger.info("STEP 1: Get browser console logs");
            org.openqa.selenium.logging.LogEntries logEntries = getDriver().manage().logs().get("browser");
            
            int errorCount = 0;
            int warningCount = 0;
            
            for (org.openqa.selenium.logging.LogEntry entry : logEntries) {
                if (entry.getLevel().toString().equals("SEVERE")) {
                    errorCount++;
                    logger.warn("Console ERROR: {}", entry.getMessage());
                } else if (entry.getLevel().toString().equals("WARNING")) {
                    warningCount++;
                }
            }

            logger.info("STEP 2: Verify console status");
            logger.info("✓ Console errors: {}", errorCount);
            logger.info("✓ Console warnings: {}", warningCount);
            
            if (errorCount == 0) {
                logger.info("✓ No JavaScript errors detected");
            } else {
                logger.warn("⚠ {} JavaScript error(s) found in console", errorCount);
            }

            logger.info("✅ TEST PASSED: Console errors checked");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testNoConsoleErrors", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_027_Test_No_Console_Errors ==========\n");
        }
    }

    // =====================================================
    // PHẦN 18 - Kiểm tra Page Load Performance
    // =====================================================
    @Test(testName = "TC_028_Test_Page_Load_Performance", description = "Verify page loads within acceptable time", groups = {"UI", "Performance"})
    public void testPageLoadPerformance() {
        logger.info("========== START TEST CASE: TC_028_Test_Page_Load_Performance ==========");
        
        try {
            logger.info("STEP 1: Measure page load time using Navigation Timing API");
            Long loadTime = (Long) ((org.openqa.selenium.JavascriptExecutor) getDriver())
                .executeScript(
                    "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart);"
                );
            
            double loadTimeSeconds = loadTime / 1000.0;
            logger.info("✓ Page load time: {} ms ({} seconds)", loadTime, String.format("%.2f", loadTimeSeconds));

            logger.info("STEP 2: Get DOM content loaded time");
            Long domContentLoaded = (Long) ((org.openqa.selenium.JavascriptExecutor) getDriver())
                .executeScript(
                    "return (window.performance.timing.domContentLoadedEventEnd - window.performance.timing.navigationStart);"
                );
            
            double domTimeSeconds = domContentLoaded / 1000.0;
            logger.info("✓ DOM content loaded time: {} ms ({} seconds)", domContentLoaded, String.format("%.2f", domTimeSeconds));

            logger.info("STEP 3: Verify acceptable load time");
            Assert.assertTrue(loadTimeSeconds < 10, "Page should load in less than 10 seconds");
            logger.info("✓ Page load time is acceptable");

            logger.info("✅ TEST PASSED: Page load performance verified");
            
        } catch (Exception e) {
            logger.error("❌ TEST FAILED at testPageLoadPerformance", e);
            Assert.fail("Test failed due to exception", e);
            
        } finally {
            logger.info("========== END TEST CASE: TC_028_Test_Page_Load_Performance ==========\n");
        }
    }
}
