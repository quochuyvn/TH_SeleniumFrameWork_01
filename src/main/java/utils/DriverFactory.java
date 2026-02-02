package utils;

import config.ConfigKeys;
import config.ConfigReader;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.exceptions.DriverCreationException;
import utils.exceptions.GridConnectionException;
import utils.exceptions.InvalidBrowserException;
import utils.exceptions.InvalidConfigException;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * =====================================================
 * DriverFactory - ENTERPRISE EDITION
 * =====================================================
 * ✅ Input validation (prevents silent failures)
 * ✅ Custom exceptions (clear error messages)
 * ✅ Selenium 4 compliant
 * ✅ Local & Grid support
 * =====================================================
 */
public class DriverFactory {

    private static final List<String> SUPPORTED_BROWSERS = Arrays.asList("chrome", "firefox", "edge");
    private static final List<String> SUPPORTED_RUN_MODES = Arrays.asList("local", "grid");

    private DriverFactory() {
    }

    /**
     * Creates WebDriver with input validation
     * 
     * @param browser Browser name (chrome/firefox/edge) - case insensitive, trimmed
     * @param os Operating system (windows/mac/linux) - can be null for local mode
     * @param runMode Execution mode (local/grid) - case insensitive, trimmed
     * @return WebDriver instance
     * @throws InvalidBrowserException if browser is null, empty, or unsupported
     * @throws InvalidConfigException if runMode is null, empty, or unsupported
     * @throws GridConnectionException if Grid connection fails
     * @throws DriverCreationException if driver creation fails
     */
    public static WebDriver createDriver(String browser, String os, String runMode) {
        
        // ✅ ISSUE #1: INPUT VALIDATION
        validateInputs(browser, runMode);
        
        // Normalize inputs
        String normalizedBrowser = browser.trim().toLowerCase();
        String normalizedRunMode = runMode.trim().toLowerCase();

        if ("grid".equals(normalizedRunMode)) {
            return createRemoteDriver(normalizedBrowser, os);
        }
        
        // Default to local
        return createLocalDriver(normalizedBrowser);
    }

    /**
     * Validates input parameters
     */
    private static void validateInputs(String browser, String runMode) {
        // Validate browser
        if (browser == null || browser.trim().isEmpty()) {
            throw new InvalidBrowserException(
                "Browser cannot be null or empty. Supported browsers: " + SUPPORTED_BROWSERS);
        }
        
        String normalizedBrowser = browser.trim().toLowerCase();
        if (!SUPPORTED_BROWSERS.contains(normalizedBrowser)) {
            throw new InvalidBrowserException(
                String.format("Unsupported browser: '%s'. Supported browsers: %s", 
                    browser, SUPPORTED_BROWSERS));
        }

        // Validate runMode
        if (runMode == null || runMode.trim().isEmpty()) {
            throw new InvalidConfigException(
                "Run mode cannot be null or empty. Supported modes: " + SUPPORTED_RUN_MODES);
        }
        
        String normalizedRunMode = runMode.trim().toLowerCase();
        if (!SUPPORTED_RUN_MODES.contains(normalizedRunMode)) {
            throw new InvalidConfigException(
                String.format("Unsupported run mode: '%s'. Supported modes: %s", 
                    runMode, SUPPORTED_RUN_MODES));
        }
    }

    /*
     * ======================
     * LOCAL DRIVER
     * ======================
     */

    private static WebDriver createLocalDriver(String browser) {
        try {
            switch (browser) {
                case "chrome":
                    return new ChromeDriver();

                case "edge":
                    return new EdgeDriver();

                case "firefox":
                    return new FirefoxDriver();

                default:
                    // Should never reach here due to validation
                    throw new InvalidBrowserException("Unsupported browser: " + browser);
            }
        } catch (Exception e) {
            if (e instanceof InvalidBrowserException) {
                throw e; // Re-throw custom exception
            }
            throw new DriverCreationException(
                String.format("Failed to create local %s driver. Ensure driver is in PATH or configured correctly.", browser), e);
        }
    }

    /*
     * ======================
     * REMOTE DRIVER (GRID)
     * ======================
     */

    private static WebDriver createRemoteDriver(String browser, String os) {
        String gridUrl = ConfigReader.getRequired(ConfigKeys.GRID_URL);
        
        if (gridUrl == null || gridUrl.trim().isEmpty()) {
            throw new InvalidConfigException("Grid URL is not configured in config.properties");
        }

        try {
            switch (browser) {
                case "firefox":
                    return new RemoteWebDriver(
                            URI.create(gridUrl).toURL(),
                            getFirefoxOptions(os));
                case "edge":
                    return new RemoteWebDriver(
                            URI.create(gridUrl).toURL(),
                            getEdgeOptions(os));
                case "chrome":
                default:
                    return new RemoteWebDriver(
                            URI.create(gridUrl).toURL(),
                            getChromeOptions(os));
            }
        } catch (java.net.MalformedURLException e) {
            throw new InvalidConfigException(
                String.format("Invalid Grid URL: '%s'. Expected format: http://host:port", gridUrl), e);
        } catch (Exception e) {
            throw new GridConnectionException(
                String.format("Failed to connect to Selenium Grid at '%s'. Ensure Grid is running and accessible.", gridUrl), e);
        }
    }

    /*
     * ======================
     * OPTIONS
     * ======================
     */

    private static ChromeOptions getChromeOptions(String os) {
        ChromeOptions options = new ChromeOptions();

        // 1. Xác định Platform dựa trên tham số os truyền vào
        Platform platform;
        if (os != null && os.toLowerCase().contains("mac")) {
            platform = Platform.MAC;
        } else if (os != null && os.toLowerCase().contains("lin")) {
            platform = Platform.LINUX; // Bonus thêm Linux nếu cần
        } else {
            platform = Platform.WINDOWS; // Mặc định là Windows
        }

        // 2. Set capability platformName
        options.setCapability("platformName", platform);

        // 3. Cấu hình Arguments (Lưu ý sự khác biệt giữa Mac và Win)
        if (platform == Platform.MAC) {
            // Trên Mac, --start-maximized thường không hoạt động ổn định
            // Nên dùng --kiosk (fullscreen) hoặc set cứng kích thước
            options.addArguments("--kiosk");
            // Hoặc: options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        return options;
    }

    private static FirefoxOptions getFirefoxOptions(String os) {
        FirefoxOptions options = new FirefoxOptions();

        // 1. Xác định Platform dựa trên tham số os truyền vào
        Platform platform;
        if (os != null && os.toLowerCase().contains("mac")) {
            platform = Platform.MAC;
        } else if (os != null && os.toLowerCase().contains("lin")) {
            platform = Platform.LINUX; // Bonus thêm Linux nếu cần
        } else {
            platform = Platform.WINDOWS; // Mặc định là Windows
        }

        // 2. Set capability platformName
        options.setCapability("platformName", platform);

        // 3. Cấu hình Arguments (Lưu ý sự khác biệt giữa Mac và Win)
        if (platform == Platform.MAC) {
            // Trên Mac, --start-maximized thường không hoạt động ổn định
            // Nên dùng --kiosk (fullscreen) hoặc set cứng kích thước
            options.addArguments("--kiosk");
            // Hoặc: options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        return options;
    }

    private static EdgeOptions getEdgeOptions(String os) {
        EdgeOptions options = new EdgeOptions();

        // 1. Xác định Platform dựa trên tham số os truyền vào
        Platform platform;
        if (os != null && os.toLowerCase().contains("mac")) {
            platform = Platform.MAC;
        } else if (os != null && os.toLowerCase().contains("lin")) {
            platform = Platform.LINUX; // Bonus thêm Linux nếu cần
        } else {
            platform = Platform.WINDOWS; // Mặc định là Windows
        }

        // 2. Set capability platformName
        options.setCapability("platformName", platform);

        // 3. Cấu hình Arguments (Lưu ý sự khác biệt giữa Mac và Win)
        if (platform == Platform.MAC) {
            // Trên Mac, --start-maximized thường không hoạt động ổn định
            // Nên dùng --kiosk (fullscreen) hoặc set cứng kích thước
            options.addArguments("--kiosk");
            // Hoặc: options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        return options;
    }

}
