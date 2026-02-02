package utils;

import base.BaseClass;
import config.ConfigKeys;
import config.ConfigReader;
import org.openqa.selenium.WebDriver; // [NEW] Import thêm WebDriver để xử lý logic lấy driver an toàn
import org.testng.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays; // [NEW] Import để log mảng dữ liệu (parameters)
import java.util.Date;
import java.util.List;

/**
 * ====================================================
 * ExtentReportManager - TestNG Listener với Video Recording
 * ====================================================
 * 
 * Tính năng mới:
 * - 🎥 Tự động record video khi test chạy
 * - ❌ Test FAIL → Giữ video + attach vào report
 * - ✅ Test PASS → Xoá video để tiết kiệm disk
 * - 📊 Report HTML với screenshot + video embed
 * 
 * @version 3.0 - Thêm Video Recording
 */
public class ExtentReportManager implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();
    private String reportName;

    @Override
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportName = "Test-Report-" + timeStamp + ".html";
        
        // [UPDATE] Thêm logic kiểm tra và tạo thư mục report
        // Mục đích: Tránh lỗi "FileNotFoundException" nếu thư mục 'reports' chưa tồn tại (khi clone code mới hoặc chạy CI)
        String reportFolder = System.getProperty("user.dir") + "/reports/";
        File dir = new File(reportFolder);
        if (!dir.exists()) {
            dir.mkdirs(); // Tự động tạo folder nếu chưa có
        }

        String reportPath = reportFolder + reportName;

        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Result");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        // [NEW] Thêm thông tin Java version
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        String env = System.getProperty(ConfigKeys.ENV, context.getCurrentXmlTest().getParameter("env"));
        if (env == null || env.isBlank()) {
            env = ConfigReader.get(ConfigKeys.ENV);
        }
        extent.setSystemInfo("Environment", env);
        extent.setSystemInfo("Base URL", ConfigReader.getBaseUrl());

        String browser = context.getCurrentXmlTest().getParameter("browser");
        if (browser != null) extent.setSystemInfo("Browser", browser);

        List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
        if (!groups.isEmpty()) extent.setSystemInfo("Groups", groups.toString());
    }

    @Override
    public void onTestStart(ITestResult result) {
        // [VIDEO RECORDING] Start recording khi test bắt đầu
        VideoManager.startRecording(result.getMethod().getMethodName());
        
        // [UPDATE] 1. Lấy thông tin Browser từ file testng.xml
        // result.getTestContext() giúp lấy ngữ cảnh của thẻ <test> đang chạy
        String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
        
        // Nếu không tìm thấy param (trường hợp chạy đơn lẻ không qua xml), set mặc định
        if (browser == null || browser.isEmpty()) {
            browser = "Unknown";
        }

        // [UPDATE] 2. Format lại tên Test để hiển thị rõ trình duyệt
        // Ví dụ output: "[EDGE] : Scenario_002... :: TC001..."
        String testName = "[" + browser.toUpperCase() + "] : " +
                          result.getTestClass().getRealClass().getSimpleName() + " :: " +
                          result.getMethod().getMethodName();

        // Tạo test trong report với tên mới đã có prefix [BROWSER]
        ExtentTest test = extent.createTest(testName);

        // [UPDATE] 3. Gán thêm Device và Category để report hiển thị icon và filter được
        test.assignCategory(result.getMethod().getGroups()); // Giữ nguyên group cũ
        test.assignCategory(browser.toUpperCase());          // Thêm group là tên browser (để lọc xem riêng Chrome/Edge)
        test.assignDevice(browser.toUpperCase());            // Hiển thị tên browser ở mục Device

        EXTENT_TEST.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // [VIDEO RECORDING] Stop và XOÁ video vì test PASS
        VideoManager.stopRecording(false);
        
        // [NEW] Gọi hàm log tham số đầu vào (ví dụ: login với user nào)
        logTestParams(result); 
        
        EXTENT_TEST.get().log(Status.PASS, result.getMethod().getMethodName() + " PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = EXTENT_TEST.get();
        logTestParams(result); // Log tham số (nếu có)

        test.log(Status.FAIL, result.getMethod().getMethodName() + " FAILED");
        test.log(Status.FAIL, result.getThrowable());

        try {
            // 1. Lấy Driver an toàn (tránh lỗi NullPointer hoặc ClassCast)
            Object currentClass = result.getInstance();
            WebDriver driver = null;
            if (currentClass instanceof BaseClass) {
                driver = ((BaseClass) currentClass).getDriver();
            }

            if (driver != null) {
                // --- SCREENSHOT ---
                // Bước 1: Chụp và lưu file ảnh vào folder (để backup)
                ScreenshotUtil.captureViewport(driver, result.getMethod().getMethodName()); // Backup to disk

                // Bước 2: Chụp và lấy chuỗi Base64 (để hiển thị đẹp trên report)
                String base64Screenshot = ScreenshotUtil.captureBase64(driver);
                
                if (base64Screenshot != null) {
                    test.addScreenCaptureFromBase64String(base64Screenshot, "Failure Screenshot");
                }
                
            } else {
                test.log(Status.WARNING, "Cannot capture screenshot: Driver is null.");
            }

        } catch (Exception e) {
            test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
        }

        // --- VIDEO RECORDING ---
        // Stop và GIỮ video vì test FAIL
        VideoManager.stopRecording(true);
        
        // Attach video vào report
        String videoPath = VideoManager.getVideoPath();
        if (videoPath != null) {
            File videoFile = new File(videoPath);
            if (videoFile.exists()) {
                try {
                    // Tạo relative path từ reports/ folder đến target/videos/
                    // Report: reports/Test-Report-xxx.html
                    // Video:  target/videos/TC001_xxx.mp4
                    // Path:   ../target/videos/TC001_xxx.mp4
                    String relativePath = "../target/videos/" + videoFile.getName();
                    
                    // Attach video link và player vào report
                    test.info("<b>🎥 Video Recorded:</b>");
                    
                    // Check file extension để hiển thị appropriate message
                    String fileExt = videoFile.getName().substring(videoFile.getName().lastIndexOf("."));
                    boolean isMp4 = fileExt.equalsIgnoreCase(".mp4");
                    
                    // Download link (luôn luôn có)
                    test.info("📹 <a href='" + relativePath + "' download='" + videoFile.getName() + "' " +
                             "style='color:#4CAF50; font-weight:bold;'>" +
                             "Download Video (" + String.format("%.2f", videoFile.length() / 1024.0 / 1024.0) + " MB)" +
                             "</a>");
                    
                    // Video player (chỉ hiển thị nếu là MP4)
                    if (isMp4) {
                        test.info("<video width='800' height='450' controls preload='metadata' " +
                                 "style='margin-top:10px; border:1px solid #333; border-radius:5px;'>" +
                                 "<source src='" + relativePath + "' type='video/mp4'>" +
                                 "Your browser does not support HTML5 video. Please download the video." +
                                 "</video>");
                        // Removed: Video is ready to play message
                    } else {
                        test.info("⚠️ AVI format - Please download to view with VLC/Media Player");
                    }
                    
                    // Removed: File info and path display
                    
                } catch (Exception e) {
                    test.log(Status.WARNING, "Failed to attach video: " + e.getMessage());
                }
            } else {
                test.log(Status.WARNING, "Video file not found: " + videoPath);
            }
        } else {
            test.log(Status.WARNING, "Video path is null - recording may have failed");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // [VIDEO RECORDING] Stop và XOÁ video vì test SKIPPED
        VideoManager.stopRecording(false);
        
        EXTENT_TEST.get().log(Status.SKIP, result.getMethod().getMethodName() + " SKIPPED");
        if (result.getThrowable() != null) {
            EXTENT_TEST.get().log(Status.SKIP, result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        EXTENT_TEST.remove();

        // [UPDATE] Thêm điều kiện !isRunningOnCI()
        // Mục đích: Không cố mở trình duyệt khi chạy trên server Jenkins/Linux không màn hình
        if (Desktop.isDesktopSupported() && !isRunningOnCI()) {
            try {
                File reportFile = new File(System.getProperty("user.dir") + "/reports/" + reportName);
                Desktop.getDesktop().browse(reportFile.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // [NEW] Helper Method: Log các tham số từ DataProvider vào Report
    private void logTestParams(ITestResult result) {
        Object[] params = result.getParameters();
        if (params.length > 0) {
            EXTENT_TEST.get().info("Test Data: " + Arrays.toString(params));
        }
    }

    // [NEW] Helper Method: Kiểm tra xem code có đang chạy trên môi trường CI (Jenkins, GitLab...) không
    private boolean isRunningOnCI() {
        // Hầu hết các hệ thống CI đều có biến môi trường "CI" hoặc "JENKINS_URL"
        return System.getenv("CI") != null || System.getenv("JENKINS_URL") != null;
    }
}