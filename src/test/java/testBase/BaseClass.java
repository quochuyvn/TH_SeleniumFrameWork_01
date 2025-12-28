//package testBase;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.net.URL;
//import java.time.Duration;
//import java.util.Properties;
//import java.util.UUID;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.Platform;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Parameters;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//
//public class BaseClass {
//	public WebDriver driver;
//	public Logger logger; 
//	public Properties properties;
//	
//	public WebDriver getDriver() {
//        return driver;
//    }
//	
//	@Parameters({"os", "browser"})
//	@BeforeClass(groups= {"Sanity","Regression","Master"})
//	public void setupClass(String os, String br) throws IOException {
//		
//		// Loading properties file
//		FileReader file = new FileReader("./src/test/resources/config.properties");
//		properties = new Properties();
//		properties.load(file);
//		
//		
//		// Loading log4j2 file
//		logger = LogManager.getLogger(this.getClass()); //Log4j	
//		
//		if(properties.getProperty("execution_env").equalsIgnoreCase("remote")) {
//			DesiredCapabilities capabilities = new DesiredCapabilities();
//			
//			// Operation System
//			if(os.equalsIgnoreCase("Mac")) {
//				capabilities.setPlatform(Platform.MAC);
//			}
//			else if (os.equalsIgnoreCase("Windows") ) {
//				capabilities.setPlatform(Platform.WIN11);
//			}
//			else {
//				System.out.println("No matching os");
//			}
//			
//			// Browser
//			switch(br.toLowerCase()) {
//			case "chrome": capabilities.setBrowserName("chrome"); break;
//			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
//			default: System.out.println("No matching browser"); return;
//			}
//			
//			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
//			
//			//driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
//		}
//		
//		if (properties.getProperty("execution_env").equalsIgnoreCase("local")) {
//			switch(br.toLowerCase()) {
//			case "chrome": WebDriverManager.chromedriver().setup(); driver = new ChromeDriver(); break;
//			case "edge": WebDriverManager.edgedriver().setup(); driver = new EdgeDriver(); break;
//			case "firefox": WebDriverManager.firefoxdriver().setup(); driver = new FirefoxDriver(); break;
//			default: System.out.println("No matching any browser...");
//				return;
//			}
//		}
//		
//		driver.manage().deleteAllCookies();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
////		driver.get("https://tutorialsninja.com/demo/");
//		driver.get(properties.getProperty("appURL"));
//		driver.manage().window().maximize();
//	}
//		
//	@AfterMethod (groups= {"Sanity","Regression","Master"})
//	public void tearDownMethod(ITestResult result) {
//		if (ITestResult.FAILURE == result.getStatus()) {
//			logger.error("❌ Test FAILED: " + result.getName());
//			logger.error("Reason: " + result.getThrowable());
//		}
//	}
//	@AfterClass (groups= {"Sanity","Regression","Master"})
//	public void tearDownClass() {
////		driver.close();
//		if(driver != null) {
//	        driver.quit();
//	    }
//	}
//	
//	// Cach nay da cu, nen khong dung nua.
////	public String randomString(int num) {
////		String generatedString = RandomStringUtils.randomAlphabetic(num);
////		return generatedString;
////	}
//	
//	// Dung cach moi ben duoi
//	public String shortUUID(int length) {
//        if (length <= 0 || length > 32) {
//            throw new IllegalArgumentException("Length must be between 1 and 32");
//        }
//        return UUID.randomUUID()
//                .toString()
//                .replace("-", "")
//                .substring(0, length);
//    }
//	
//	public String randomString(int num) {
//		return shortUUID(num);
//	}
//	
//	public String randomMail(int num) {
//		return "user_" + shortUUID(num) + "@gmail.com";
//	}
//	
//	// Cach nay da cu
////	public String randomeNumber(int num)
////	{
////		String generatedString=RandomStringUtils.randomNumeric(num);
////		return generatedString;
////	}
//	
//	
//	
//}

// Chat GPT Refactor lai theo chuan Selenium 4
package testBase;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.ITestResult;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    // WebDriver dùng chung cho toàn bộ Test Class
    protected WebDriver driver;

    // Logger log4j2
    protected Logger logger;

    // Properties đọc từ config.properties
    protected Properties properties;

    /**
     * Getter để các class khác (Listener, Page, Util) lấy driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Setup WebDriver trước khi chạy Test Class
     * @param os      hệ điều hành (Windows / Mac)
     * @param browser trình duyệt (chrome / edge / firefox)
     */
    @Parameters({"os", "browser"})
    @BeforeClass(groups = {"Sanity", "Regression", "Master"})
    public void setupClass(
            @Optional("Windows") String os,
            @Optional("chrome") String browser) throws IOException {

        /* =========================================================
         * 1. LOAD CONFIG.PROPERTIES
         * ========================================================= */
        try (FileReader reader =
                     new FileReader("./src/test/resources/config.properties")) {
            properties = new Properties();
            properties.load(reader);
        }

        /* =========================================================
         * 2. INIT LOGGER
         * ========================================================= */
        logger = LogManager.getLogger(this.getClass());
        logger.info("=== START TEST CLASS ===");

        String executionEnv = properties.getProperty("execution_env");

        /* =========================================================
         * 3. REMOTE EXECUTION (Selenium Grid / Docker)
         * ========================================================= */
        if ("remote".equalsIgnoreCase(executionEnv)) {

            // Selenium 4 dùng Options thay cho DesiredCapabilities
            if ("chrome".equalsIgnoreCase(browser)) {

                ChromeOptions options = new ChromeOptions();
                options.setPlatformName(
                        os.equalsIgnoreCase("mac") ? "MAC" : "WINDOWS");

                driver = new RemoteWebDriver(
                        new URL("http://localhost:4444"), options);

            } else if ("edge".equalsIgnoreCase(browser)) {

                EdgeOptions options = new EdgeOptions();
                options.setPlatformName(
                        os.equalsIgnoreCase("mac") ? "MAC" : "WINDOWS");

                driver = new RemoteWebDriver(
                        new URL("http://localhost:4444"), options);

            } else if ("firefox".equalsIgnoreCase(browser)) {

                FirefoxOptions options = new FirefoxOptions();
                options.setPlatformName(
                        os.equalsIgnoreCase("mac") ? "MAC" : "WINDOWS");

                driver = new RemoteWebDriver(
                        new URL("http://localhost:4444"), options);

            } else {
                throw new RuntimeException(
                        "Remote browser not supported: " + browser);
            }
        }

        /* =========================================================
         * 4. LOCAL EXECUTION
         * ========================================================= */
        else if ("local".equalsIgnoreCase(executionEnv)) {

            switch (browser.toLowerCase()) {

                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;

                default:
                    throw new RuntimeException(
                            "Local browser not supported: " + browser);
            }
        }

        /* =========================================================
         * 5. SAFETY CHECK
         * ========================================================= */
        if (driver == null) {
            throw new RuntimeException("WebDriver initialization FAILED.");
        }

        /* =========================================================
         * 6. COMMON DRIVER SETUP
         * ========================================================= */
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        // Khuyến nghị: KHÔNG dùng implicitWait lâu dài
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(5));

        driver.get(properties.getProperty("appURL"));

        logger.info("Browser launched successfully");
    }

    /**
     * After mỗi test method
     * Log khi test FAIL
     */
    @AfterMethod(groups = {"Sanity", "Regression", "Master"})
    public void tearDownMethod(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {
            logger.error("❌ TEST FAILED: " + result.getName());
            logger.error("Reason: ", result.getThrowable());
        }
    }

    /**
     * Close browser sau khi chạy xong Test Class
     */
    @AfterClass(groups = {"Sanity", "Regression", "Master"})
    public void tearDownClass() {

        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }

    /* =========================================================
     * 7. RANDOM DATA UTILITIES
     * ========================================================= */

    /**
     * Sinh chuỗi random từ UUID (1–32 ký tự)
     */
    public String shortUUID(int length) {

        if (length <= 0 || length > 32) {
            throw new IllegalArgumentException(
                    "Length must be between 1 and 32");
        }

        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, length);
    }

    public String randomString(int length) {
        return shortUUID(length);
    }

    public String randomMail(int length) {
        return "user_" + shortUUID(length) + "@gmail.com";
    }
}

