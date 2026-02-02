package base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.ConfigReader;
import utils.DriverFactory;
import utils.ScreenshotUtil;

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
public abstract class BaseClassFlow {

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

    /* * =====================================================
     * CLASS LEVEL - KHỞI TẠO DRIVER TẠI ĐÂY
     * =====================================================
     */
    @BeforeClass(alwaysRun = true)
    @Parameters({ "browser", "os" })
    public void setUp(@Optional("") String browser, @Optional("") String os) {
        // 1. Init Logger cho Class
        if (logger == null) {
            logger = LogManager.getLogger(this.getClass());
        }

        // 2. Setup Driver (Code cũ từ BeforeMethod chuyển lên đây)
        logger.info("========== STARTING CLASS: {} ==========", this.getClass().getSimpleName());
        
        String currentBrowser = resolveBrowser(browser); // Hàm resolve giữ nguyên
        String currentOs = resolveOs(os);
        String runMode = resolveRunMode();

        // Tạo driver
        WebDriver driver = DriverFactory.createDriver(currentBrowser, currentOs, runMode);
        setDriver(driver);

        // Config cơ bản
        driver.manage().window().maximize();
        
        String baseUrl = ConfigReader.getBaseUrl();
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Base URL cannot be null or empty");
        }
        driver.get(baseUrl);
    }

   /* * =====================================================
     * CLASS LEVEL - ĐÓNG DRIVER TẠI ĐÂY
     * =====================================================
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        logger.info("========== ENDING CLASS: {} ==========", this.getClass().getSimpleName());
        
        if (getDriver() != null) {
            logger.info("Quit WebDriver (Cleaning up session)");
            getDriver().quit();
            removeDriver();
        }
    }

   /* * =====================================================
     * METHOD LEVEL - CHỈ GHI LOG, KHÔNG TẠO DRIVER
     * =====================================================
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        // Tại đây bạn làm gì?
        // 1. Ghi log tên Test Case đang chạy để dễ debug
        logger.info("-------------------------------------------------------");
        logger.info(">>> START TEST METHOD: '{}'", method.getName());
        logger.info("-------------------------------------------------------");
        
        // 2. Nếu bạn dùng ExtentReport, đây là chỗ bạn tạo node test:
        // ExtentTestManager.startTest(method.getName(), "Mô tả test...");
    }
   @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        // Tại đây bạn làm gì?
        // 1. Ghi log kết quả (Pass/Fail)
        if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info(">>> [PASSED]: {}", result.getName());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            logger.error(">>> [FAILED]: {}", result.getName());
            logger.error(">>> ERROR: {}", result.getThrowable().getMessage());
            
            // Chụp màn hình (Screenshot) nếu lỗi (Quan trọng!)
            String screenshotPath = ScreenshotUtil.captureViewport(getDriver(), result.getName());
            logger.info(">>> Screenshot captured: {}", screenshotPath);
        } else {
            logger.warn(">>> [SKIPPED]: {}", result.getName());
        }
        
        // LƯU Ý: KHÔNG gọi driver.quit() ở đây nữa!
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
