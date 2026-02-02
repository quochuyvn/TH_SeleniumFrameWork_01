package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.ConfigReader;
import utils.DriverFactory;

/**
 * =====================================================
 * BaseClass (Enterprise Standard - NO WAIT)
 * =====================================================
 * - ThreadLocal WebDriver (parallel safe)
 * - ONLY manage driver lifecycle
 * - NO implicit wait / pageLoadTimeout here
 * - All waits must live in BasePage (explicit wait)
 * =====================================================
 */
public abstract class BaseClass {

    /*
     * ======================
     * THREAD-LOCAL DRIVER
     * ======================
     */

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public WebDriver getDriver() {
        return DRIVER.get();
    }

    protected void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    protected void removeDriver() {
        DRIVER.remove();
    }

    /*
     * ======================
     * LOGGER CONFIGURATION
     * ======================
     */

    // [KEEP] Logger dùng cho Suite/Test level
    private static final Logger CORE_LOGGER = LogManager.getLogger(BaseClass.class);

    // Logger cho từng Test Class
    protected Logger logger;

    /*
     * =====================================================
     * SUITE LEVEL
     * =====================================================
     */

    @BeforeSuite(alwaysRun = true)
    protected void beforeSuite() {
        CORE_LOGGER.info("===== START TEST SUITE =====");
    }

    @AfterSuite(alwaysRun = true)
    protected void afterSuite() {
        CORE_LOGGER.info("===== END TEST SUITE =====");
    }

    /*
     * =====================================================
     * TEST LEVEL (<test> in testng.xml)
     * =====================================================
     */

    @BeforeTest(alwaysRun = true)
    protected void beforeTest() {

        CORE_LOGGER.info("----- START <test> BLOCK -----");

        // [KEEP] Log global config 1 lần duy nhất
        String env = ConfigReader.getRequired("env");
        String baseUrl = ConfigReader.getBaseUrl();

        CORE_LOGGER.info("-------------------------------------------------");
        CORE_LOGGER.info(">>> GLOBAL CONFIGURATION:");
        CORE_LOGGER.info(">>> Environment : {}", env);
        CORE_LOGGER.info(">>> Base URL    : {}", baseUrl);
        CORE_LOGGER.info("-------------------------------------------------");
    }

    @AfterTest(alwaysRun = true)
    protected void afterTest() {
        CORE_LOGGER.info("----- END <test> BLOCK -----");
    }

    /*
     * =====================================================
     * CLASS LEVEL
     * =====================================================
     */

    @BeforeClass(alwaysRun = true)
    protected void beforeClass() {
        logger = LogManager.getLogger(this.getClass());
        logger.info("=== START TEST CLASS: {} ===",
                this.getClass().getSimpleName());
    }

    @AfterClass(alwaysRun = true)
    protected void afterClass() {
        if (logger != null) {
            logger.info("=== END TEST CLASS: {} ===",
                    this.getClass().getSimpleName());
        }
    }

    /*
     * =====================================================
     * METHOD LEVEL (WEB DRIVER SETUP)
     * =====================================================
     */

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser", "os" })
    protected void setUp(
            @Optional("") String browser,
            @Optional("") String os) {

        /*
         * ======================
         * Lazy logger init (Cucumber safe)
         * ======================
         */
        if (logger == null) {
            logger = LogManager.getLogger(this.getClass());
        }

        logger.info("---- START TEST METHOD ----");

        /*
         * ======================
         * Resolve configs
         * ======================
         */
        String currentBrowser = resolveBrowser(browser);
        String currentOs = resolveOs(os);
        String runMode = resolveRunMode();

        logger.info("Launching Browser : {} | OS: {} | Mode: {}",
                currentBrowser, currentOs, runMode);

        /*
         * ======================
         * Create WebDriver
         * ======================
         */
        WebDriver driver = DriverFactory.createDriver(currentBrowser, os, runMode);
        setDriver(driver);

        /*
         * ======================
         * Browser basic setup
         * ======================
         */
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        /*
         * ======================
         * Navigate
         * ======================
         */
        String baseUrl = ConfigReader.getBaseUrl();
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Base URL cannot be null or empty");
        }
        
        logger.info("Navigating to: {}", baseUrl);
        driver.get(baseUrl);
        logger.info("Successfully navigated to: {}", baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    protected void tearDown() {

        if (getDriver() != null) {
            logger.info("Quit WebDriver & End Method");
            getDriver().quit();
            removeDriver();
        } else {
            logger.info("---- END TEST METHOD (Driver was null) ----");
        }
    }

    /*
     * =====================================================
     * RESOLVE METHODS
     * =====================================================
     */

    private String resolveBrowser(String browserFromXml) {

        String browser = System.getProperty("browser");

        if (browser == null || browser.isBlank()) {
            browser = browserFromXml;
        }

        if (browser == null || browser.isBlank()) {
            browser = ConfigReader.get("browser");
        }

        return (browser == null || browser.isBlank())
                ? "chrome"
                : browser.toLowerCase();
    }

    private String resolveOs(String osFromXml) {

        // 1. Ưu tiên cao nhất: Lấy từ dòng lệnh (Ví dụ: mvn test -Dos=mac)
        String os = System.getProperty("os");

        // 2. Nếu không có, lấy từ tham số TestNG XML
        if (os == null || os.isBlank()) {
            os = osFromXml;
        }

        // 3. Nếu không có, lấy từ file Config
        if (os == null || os.isBlank()) {
            os = ConfigReader.get("os");
        }

        // 4. Trả về kết quả (Nếu vẫn null thì tự động lấy OS hiện tại của máy)
        return (os == null || os.isBlank())
                ? System.getProperty("os.name").toLowerCase() // Default: Auto-detect
                : os.toLowerCase();
    }

   private String resolveRunMode() {
        String runMode = System.getProperty("run.mode");

        // 1. Nếu Maven truyền vào chuỗi rác "${run.mode}", coi như là null
        if (runMode != null && runMode.contains("${")) {
            runMode = null;
        }

        // 2. Nếu không có tham số dòng lệnh, đọc từ file Config
        if (runMode == null || runMode.isBlank()) {
            runMode = ConfigReader.get("run.mode");
            logger.info("[CONFIG] Reading run.mode from Config File: '{}'", runMode);
        } else {
            logger.info("[MAVEN] Reading run.mode from Command Line: '{}'", runMode);
        }

        // 3. Fallback cuối cùng: Nếu file config cũng null/rỗng thì về "local"
        // Quan trọng: dùng .trim() để xóa khoảng trắng thừa
        return (runMode == null || runMode.isBlank())
                ? "local"
                : runMode.trim().toLowerCase();
    }
}
